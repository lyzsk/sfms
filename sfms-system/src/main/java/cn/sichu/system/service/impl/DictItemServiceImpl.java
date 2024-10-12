package cn.sichu.system.service.impl;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.sichu.cache.redisson.utils.RedisUtils;
import cn.sichu.constant.CacheConstants;
import cn.sichu.core.autoconfigure.project.ProjectProperties;
import cn.sichu.core.constant.StringConstants;
import cn.sichu.core.enums.BaseEnum;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.core.model.resp.LabelValueResp;
import cn.sichu.crud.mp.service.impl.BaseServiceImpl;
import cn.sichu.system.mapper.DictItemMapper;
import cn.sichu.system.model.entity.DictItemDO;
import cn.sichu.system.model.query.DictItemQuery;
import cn.sichu.system.model.req.DictItemReq;
import cn.sichu.system.model.resp.DictItemResp;
import cn.sichu.system.service.IDictItemService;
import com.alicp.jetcache.anno.Cached;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 字典项业务实现
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Service
@RequiredArgsConstructor
public class DictItemServiceImpl extends
    BaseServiceImpl<DictItemMapper, DictItemDO, DictItemResp, DictItemResp, DictItemQuery, DictItemReq>
    implements IDictItemService {
    private static final Map<String, List<LabelValueResp>> ENUM_DICT_CACHE =
        new ConcurrentHashMap<>();
    private final ProjectProperties projectProperties;

    @Override
    protected void beforeAdd(DictItemReq req) {
        String value = req.getValue();
        CheckUtils.throwIf(this.isValueExists(value, null, req.getDictId()),
            "新增失败，字典值 [{}] 已存在", value);
        RedisUtils.deleteByPattern(CacheConstants.DICT_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    protected void beforeUpdate(DictItemReq req, Long id) {
        String value = req.getValue();
        CheckUtils.throwIf(this.isValueExists(value, id, req.getDictId()),
            "修改失败，字典值 [{}] 已存在", value);
        RedisUtils.deleteByPattern(CacheConstants.DICT_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    @Cached(key = "#dictCode", name = CacheConstants.DICT_KEY_PREFIX)
    public List<LabelValueResp> listByDictCode(String dictCode) {
        return Optional.ofNullable(ENUM_DICT_CACHE.get(dictCode.toLowerCase()))
            .orElseGet(() -> baseMapper.listByDictCode(dictCode));
    }

    @Override
    public void deleteByDictIds(List<Long> dictIds) {
        baseMapper.lambdaUpdate().in(DictItemDO::getDictId, dictIds).remove();
        RedisUtils.deleteByPattern(CacheConstants.DICT_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    public List<String> listEnumDictNames() {
        return ENUM_DICT_CACHE.keySet().stream().toList();
    }

    /**
     * 字典值是否存在
     *
     * @param value  字典值
     * @param id     ID
     * @param dictId 字典 ID
     * @return boolean
     * @author sichu huang
     * @date 2024/10/11
     **/
    private boolean isValueExists(String value, Long id, Long dictId) {
        return baseMapper.lambdaQuery().eq(DictItemDO::getValue, value)
            .eq(DictItemDO::getDictId, dictId).ne(null != id, DictItemDO::getId, id).exists();
    }

    /**
     * 将枚举转换为枚举字典
     *
     * @param enumClass 枚举类型
     * @return java.util.List<cn.sichu.base.model.resp.LabelValueResp> 枚举字典
     * @author sichu huang
     * @date 2024/10/11
     **/
    private List<LabelValueResp> toEnumDict(Class<?> enumClass) {
        Object[] enumConstants = enumClass.getEnumConstants();
        return Arrays.stream(enumConstants).map(e -> {
            BaseEnum baseEnum = (BaseEnum)e;
            return new LabelValueResp(baseEnum.getDescription(), baseEnum.getValue(),
                baseEnum.getColor());
        }).toList();
    }

    /**
     * 缓存枚举字典
     *
     * @author sichu huang
     * @date 2024/10/11
     **/
    @PostConstruct
    public void init() {
        Set<Class<?>> classSet =
            ClassUtil.scanPackageBySuper(projectProperties.getBasePackage(), BaseEnum.class);
        ENUM_DICT_CACHE.putAll(classSet.stream().collect(
            Collectors.toMap(cls -> StrUtil.toUnderlineCase(cls.getSimpleName()).toLowerCase(),
                this::toEnumDict)));
    }

}
