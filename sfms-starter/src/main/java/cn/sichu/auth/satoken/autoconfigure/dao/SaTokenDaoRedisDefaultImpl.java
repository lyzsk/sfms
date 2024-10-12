package cn.sichu.auth.satoken.autoconfigure.dao;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.sichu.cache.redisson.utils.RedisUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 默认 Sa-Token 持久层 Redis 实现（参考：Sa-Token/sa-token-plugin/sa-token-dao-redisx/SaTokenDaoOfRedis.java）
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class SaTokenDaoRedisDefaultImpl implements SaTokenDao {

    @Override
    public String get(String key) {
        return RedisUtils.get(key);
    }

    @Override
    public void set(String key, String value, long timeout) {
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            RedisUtils.set(key, value);
        } else {
            RedisUtils.set(key, value, Duration.ofSeconds(timeout));
        }
    }

    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        // -2：无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    @Override
    public void delete(String key) {
        RedisUtils.delete(key);
    }

    @Override
    public long getTimeout(String key) {
        long timeout = RedisUtils.getTimeToLive(key);
        return timeout < 0 ? timeout : timeout / 1000;
    }

    @Override
    public void updateTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getTimeout(key);
            // 如果其已经被设置为永久，则不作任何处理。如果尚未被设置为永久，那么再次 set 一次
            if (expire != SaTokenDao.NEVER_EXPIRE) {
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        RedisUtils.expire(key, Duration.ofSeconds(timeout));
    }

    @Override
    public Object getObject(String key) {
        return RedisUtils.get(key);
    }

    @Override
    public void setObject(String key, Object object, long timeout) {
        if (0 == timeout || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            RedisUtils.set(key, object);
        } else {
            RedisUtils.set(key, object, Duration.ofSeconds(timeout));
        }
    }

    @Override
    public void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        // -2：无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.setObject(key, object, expire);
    }

    @Override
    public void deleteObject(String key) {
        RedisUtils.delete(key);
    }

    @Override
    public long getObjectTimeout(String key) {
        return this.getTimeout(key);
    }

    @Override
    public void updateObjectTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getObjectTimeout(key);
            // 如果其已经被设置为永久，则不作任何处理。如果尚未被设置为永久，那么再次 set 一次
            if (expire != SaTokenDao.NEVER_EXPIRE) {
                this.setObject(key, this.getObject(key), timeout);
            }
            return;
        }
        RedisUtils.expire(key, Duration.ofSeconds(timeout));
    }

    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size,
        boolean sortType) {
        Collection<String> keys = RedisUtils.keys("%s*%s*".formatted(prefix, keyword));
        List<String> list = new ArrayList<>(keys);
        return SaFoxUtil.searchList(list, start, size, sortType);
    }
}
