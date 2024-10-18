package cn.sichu.system.service;

import cn.sichu.system.model.entity.Config;
import cn.sichu.system.model.form.ConfigForm;
import cn.sichu.system.model.query.ConfigPageQuery;
import cn.sichu.system.model.vo.ConfigVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统配置Service接口
 *
 * @author sichu huang
 * @since 2024/10/16 23:10
 */
public interface ConfigService extends IService<Config> {

    /**
     * 分页查询系统配置
     *
     * @param sysConfigPageQuery 查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<cn.sichu.model.vo.ConfigVO> 系统配置分页列表
     * @author sichu huang
     * @since 2024/10/16 23:10:49
     */
    IPage<ConfigVO> page(ConfigPageQuery sysConfigPageQuery);

    /**
     * 保存系统配置
     *
     * @param sysConfigForm 系统配置表单
     * @return boolean 是否保存成功
     * @author sichu huang
     * @since 2024/10/16 23:11:08
     */
    boolean save(ConfigForm sysConfigForm);

    /**
     * 获取系统配置表单数据
     *
     * @param id 系统配置ID
     * @return cn.sichu.model.form.ConfigForm 系统配置表单数据
     * @author sichu huang
     * @since 2024/10/16 23:11:18
     */
    ConfigForm getConfigFormData(Long id);

    /**
     * 编辑系统配置
     *
     * @param id            系统配置ID
     * @param sysConfigForm 系统配置表单
     * @return boolean 是否编辑成功
     * @author sichu huang
     * @since 2024/10/16 23:11:30
     */
    boolean edit(Long id, ConfigForm sysConfigForm);

    /**
     * 删除系统配置
     *
     * @param ids 系统配置ID
     * @return boolean 是否删除成功
     * @author sichu huang
     * @since 2024/10/16 23:11:42
     */
    boolean delete(Long ids);

    /**
     * 刷新系统配置缓存
     *
     * @return boolean 是否刷新成功
     * @author sichu huang
     * @since 2024/10/16 23:11:53
     */
    boolean refreshCache();

    /**
     * 获取系统配置
     *
     * @param key 配置键
     * @return java.lang.Object 配置值
     * @author sichu huang
     * @since 2024/10/16 23:12:05
     */
    Object getSystemConfig(String key);
}
