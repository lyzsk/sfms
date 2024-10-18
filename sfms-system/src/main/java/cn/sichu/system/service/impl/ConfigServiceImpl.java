package cn.sichu.system.service.impl;

import cn.sichu.constant.RedisConstants;
import cn.sichu.system.converter.ConfigConverter;
import cn.sichu.system.core.utils.SecurityUtils;
import cn.sichu.system.mapper.ConfigMapper;
import cn.sichu.system.model.entity.Config;
import cn.sichu.system.model.form.ConfigForm;
import cn.sichu.system.model.query.ConfigPageQuery;
import cn.sichu.system.model.vo.ConfigVO;
import cn.sichu.system.service.ConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统配置Service接口实现
 *
 * @author sichu huang
 * @since 2024/10/16 23:12
 */
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    private final ConfigMapper configMapper;
    private final ConfigConverter configConverter;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 系统启动完成后，加载系统配置到缓存
     */
    @PostConstruct
    public void init() {
        refreshCache();
    }

    @Override
    public IPage<ConfigVO> page(ConfigPageQuery configPageQuery) {
        Page<Config> page = new Page<>(configPageQuery.getPageNum(), configPageQuery.getPageSize());
        String keywords = configPageQuery.getKeywords();
        LambdaQueryWrapper<Config> query =
            new LambdaQueryWrapper<Config>().and(StringUtils.isNotBlank(keywords),
                q -> q.like(Config::getConfigKey, keywords).or()
                    .like(Config::getConfigName, keywords));
        Page<Config> pageList = this.page(page, query);
        return configConverter.toPageVo(pageList);
    }

    @Override
    public boolean save(ConfigForm configForm) {
        Assert.isTrue(super.count(
            new LambdaQueryWrapper<Config>().eq(Config::getConfigKey, configForm.getConfigKey()))
            == 0, "配置键已存在");
        Config config = configConverter.toEntity(configForm);
        config.setCreateBy(SecurityUtils.getUserId());
        return this.save(config);
    }

    @Override
    public ConfigForm getConfigFormData(Long id) {
        Config entity = this.getById(id);
        return configConverter.toForm(entity);
    }

    @Override
    public boolean edit(Long id, ConfigForm configForm) {
        Assert.isTrue(super.count(
            new LambdaQueryWrapper<Config>().eq(Config::getConfigKey, configForm.getConfigKey())
                .ne(Config::getId, id)) == 0, "配置键已存在");
        Config config = configConverter.toEntity(configForm);
        config.setUpdateBy(SecurityUtils.getUserId());
        return this.updateById(config);
    }

    @Override
    public boolean delete(Long id) {
        if (id != null) {
            return super.update(
                new LambdaUpdateWrapper<Config>().eq(Config::getId, id).set(Config::getIsDeleted, 1)
                    .set(Config::getUpdateBy, SecurityUtils.getUserId()));
        }
        return false;
    }

    @Override
    public boolean refreshCache() {
        redisTemplate.delete(RedisConstants.SYSTEM_CONFIG_KEY);
        List<Config> list = this.list();
        if (list != null) {
            Map<String, String> map = list.stream()
                .collect(Collectors.toMap(Config::getConfigKey, Config::getConfigValue));
            redisTemplate.opsForHash().putAll(RedisConstants.SYSTEM_CONFIG_KEY, map);
            return true;
        }
        return false;
    }

    @Override
    public Object getSystemConfig(String key) {
        if (StringUtils.isNotBlank(key)) {
            return redisTemplate.opsForHash().get(RedisConstants.SYSTEM_CONFIG_KEY, key);
        }
        return null;
    }
}
