package cn.sichu.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.cache.redisson.utils.RedisUtils;
import cn.sichu.constant.CacheConstants;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.core.utils.validate.ValidationUtils;
import cn.sichu.data.mp.utils.QueryWrapperHelper;
import cn.sichu.system.enums.PasswordPolicyEnum;
import cn.sichu.system.mapper.OptionMapper;
import cn.sichu.system.model.entity.OptionDO;
import cn.sichu.system.model.query.OptionQuery;
import cn.sichu.system.model.req.OptionReq;
import cn.sichu.system.model.req.OptionResetValueReq;
import cn.sichu.system.model.resp.OptionResp;
import cn.sichu.system.service.IOptionService;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 参数业务实现
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements IOptionService {
    private final OptionMapper optionMapper;

    @Override
    public List<OptionResp> list(OptionQuery query) {
        return BeanUtil.copyToList(optionMapper.selectList(QueryWrapperHelper.build(query)),
            OptionResp.class);
    }

    @Override
    @Cached(key = "#category", name = CacheConstants.OPTION_KEY_PREFIX + "MAP:")
    public Map<String, String> getByCategory(String category) {
        return optionMapper.selectByCategory(category).stream().collect(
            Collectors.toMap(OptionDO::getCode, o -> StrUtil.emptyIfNull(
                    ObjectUtil.defaultIfNull(o.getValue(), o.getDefaultValue())),
                (oldVal, newVal) -> oldVal));
    }

    @Override
    public void update(List<OptionReq> options) {
        Map<String, String> passwordPolicyOptionMap = options.stream().filter(
            option -> StrUtil.startWith(option.getCode(),
                PasswordPolicyEnum.CATEGORY + StringConstants.UNDERLINE)).collect(
            Collectors.toMap(OptionReq::getCode, OptionReq::getValue, (oldVal, newVal) -> oldVal));
        // 校验密码策略参数取值范围
        for (Map.Entry<String, String> passwordPolicyOptionEntry : passwordPolicyOptionMap.entrySet()) {
            String code = passwordPolicyOptionEntry.getKey();
            String value = passwordPolicyOptionEntry.getValue();
            ValidationUtils.throwIf(!NumberUtil.isNumber(value), "参数 [%s] 的值必须为数字", code);
            PasswordPolicyEnum passwordPolicy = PasswordPolicyEnum.valueOf(code);
            passwordPolicy.validateRange(Integer.parseInt(value), passwordPolicyOptionMap);
        }
        RedisUtils.deleteByPattern(CacheConstants.OPTION_KEY_PREFIX + StringConstants.ASTERISK);
        optionMapper.updateBatchById(BeanUtil.copyToList(options, OptionDO.class));
    }

    @Override
    public void resetValue(OptionResetValueReq req) {
        RedisUtils.deleteByPattern(CacheConstants.OPTION_KEY_PREFIX + StringConstants.ASTERISK);
        String category = req.getCategory();
        List<String> codeList = req.getCode();
        ValidationUtils.throwIf(StrUtil.isBlank(category) && CollUtil.isEmpty(codeList),
            "键列表不能为空");
        LambdaUpdateChainWrapper<OptionDO> chainWrapper =
            optionMapper.lambdaUpdate().set(OptionDO::getValue, null);
        if (StrUtil.isNotBlank(category)) {
            chainWrapper.eq(OptionDO::getCategory, category);
        } else {
            chainWrapper.in(OptionDO::getCode, req.getCode());
        }
        chainWrapper.update();
    }

    @Override
    public int getValueByCode2Int(String code) {
        return this.getValueByCode(code, Integer::parseInt);
    }

    @Override
    public <T> T getValueByCode(String code, Function<String, T> mapper) {
        String value = RedisUtils.get(CacheConstants.OPTION_KEY_PREFIX + code);
        if (StrUtil.isNotBlank(value)) {
            return mapper.apply(value);
        }
        OptionDO option = optionMapper.lambdaQuery().eq(OptionDO::getCode, code)
            .select(OptionDO::getValue, OptionDO::getDefaultValue).one();
        CheckUtils.throwIfNull(option, "参数 [{}] 不存在", code);
        value = StrUtil.nullToDefault(option.getValue(), option.getDefaultValue());
        CheckUtils.throwIfBlank(value, "参数 [{}] 数据错误", code);
        RedisUtils.set(CacheConstants.OPTION_KEY_PREFIX + code, value);
        return mapper.apply(value);
    }
}
