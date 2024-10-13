package cn.sichu.cache.redisson.autoconfigure;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.sichu.core.constant.PropertiesConstants;
import cn.sichu.core.constant.StringConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redisson 自动配置
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@AutoConfiguration
@ConditionalOnProperty(prefix = "spring.data.redisson", name = PropertiesConstants.ENABLED,
    havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(RedissonProperties.class)
@Component
public class RedissonAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RedissonAutoConfiguration.class);
    private static final String REDIS_PROTOCOL_PREFIX = "redis://";
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";
    private final RedissonProperties properties;
    private final RedisProperties redisProperties;
    private final ObjectMapper objectMapper;

    public RedissonAutoConfiguration(RedissonProperties properties, RedisProperties redisProperties,
        ObjectMapper objectMapper) {
        this.properties = properties;
        this.redisProperties = redisProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public RedissonAutoConfigurationCustomizer redissonAutoConfigurationCustomizer() {
        return config -> {
            RedissonProperties.Mode mode = properties.getMode();
            String protocolPrefix = redisProperties.getSsl().isEnabled() ? REDISS_PROTOCOL_PREFIX :
                REDIS_PROTOCOL_PREFIX;
            switch (mode) {
                case CLUSTER -> this.buildClusterModeConfig(config, protocolPrefix);
                case SENTINEL -> this.buildSentinelModeConfig(config, protocolPrefix);
                default -> this.buildSingleModeConfig(config, protocolPrefix);
            }
            // Jackson 处理
            config.setCodec(new JsonJacksonCodec(objectMapper));
            log.debug("[SFMS] - Auto Configuration 'Redisson' completed initialization.");
        };
    }

    /**
     * 构建集群模式配置
     *
     * @param config         配置
     * @param protocolPrefix 协议前缀
     */
    private void buildClusterModeConfig(Config config, String protocolPrefix) {
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        ClusterServersConfig customClusterServersConfig = properties.getClusterServersConfig();
        if (null != customClusterServersConfig) {
            BeanUtil.copyProperties(customClusterServersConfig, clusterServersConfig);
            clusterServersConfig.setNodeAddresses(customClusterServersConfig.getNodeAddresses());
        }
        // 下方配置如果为空，则使用 Redis 的配置
        if (CollUtil.isEmpty(clusterServersConfig.getNodeAddresses())) {
            List<String> nodeList = redisProperties.getCluster().getNodes();
            nodeList.stream().map(node -> protocolPrefix + node)
                .forEach(clusterServersConfig::addNodeAddress);
        }
        if (CharSequenceUtil.isBlank(clusterServersConfig.getPassword())) {
            clusterServersConfig.setPassword(redisProperties.getPassword());
        }
    }

    /**
     * 构建哨兵模式配置
     *
     * @param config         配置
     * @param protocolPrefix 协议前缀
     */
    private void buildSentinelModeConfig(Config config, String protocolPrefix) {
        SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
        SentinelServersConfig customSentinelServersConfig = properties.getSentinelServersConfig();
        if (null != customSentinelServersConfig) {
            BeanUtil.copyProperties(customSentinelServersConfig, sentinelServersConfig);
            sentinelServersConfig.setSentinelAddresses(
                customSentinelServersConfig.getSentinelAddresses());
        }
        // 下方配置如果为空，则使用 Redis 的配置
        if (CollUtil.isEmpty(sentinelServersConfig.getSentinelAddresses())) {
            List<String> nodeList = redisProperties.getSentinel().getNodes();
            nodeList.stream().map(node -> protocolPrefix + node)
                .forEach(sentinelServersConfig::addSentinelAddress);
        }
        if (CharSequenceUtil.isBlank(sentinelServersConfig.getPassword())) {
            sentinelServersConfig.setPassword(redisProperties.getPassword());
        }
        if (CharSequenceUtil.isBlank(sentinelServersConfig.getMasterName())) {
            sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
        }
    }

    /**
     * 构建单机模式配置
     *
     * @param config         配置
     * @param protocolPrefix 协议前缀
     */
    private void buildSingleModeConfig(Config config, String protocolPrefix) {
        SingleServerConfig singleServerConfig = config.useSingleServer();
        SingleServerConfig customSingleServerConfig = properties.getSingleServerConfig();
        if (null != customSingleServerConfig) {
            BeanUtil.copyProperties(properties.getSingleServerConfig(), singleServerConfig);
        }
        // 下方配置如果为空，则使用 Redis 的配置
        singleServerConfig.setDatabase(redisProperties.getDatabase());
        if (CharSequenceUtil.isBlank(singleServerConfig.getPassword())) {
            singleServerConfig.setPassword(redisProperties.getPassword());
        }
        if (CharSequenceUtil.isBlank(singleServerConfig.getAddress())) {
            singleServerConfig.setAddress(
                protocolPrefix + redisProperties.getHost() + StringConstants.COLON
                    + redisProperties.getPort());
        }
    }
}
