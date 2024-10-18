package cn.sichu.system.service;

import cn.sichu.model.Option;
import cn.sichu.system.model.entity.Dict;
import cn.sichu.system.model.form.DictForm;
import cn.sichu.system.model.query.DictPageQuery;
import cn.sichu.system.model.vo.DictPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 数据字典业务接口
 *
 * @author sichu huang
 * @since 2024/10/16 23:53:17
 */
public interface DictService extends IService<Dict> {

    /**
     * 字典分页列表
     *
     * @param queryParams 分页查询对象
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.sichu.model.vo.DictPageVO>
     * @author sichu huang
     * @since 2024/10/16 23:53:48
     */
    Page<DictPageVO> getDictPage(DictPageQuery queryParams);

    /**
     * 获取字典表单详情
     *
     * @param id 字典ID
     * @return cn.sichu.model.form.DictForm
     * @author sichu huang
     * @since 2024/10/16 23:53:57
     */
    DictForm getDictForm(Long id);

    /**
     * 新增字典
     *
     * @param dictForm 字典表单
     * @return boolean
     * @author sichu huang
     * @since 2024/10/16 23:54:21
     */
    boolean saveDict(DictForm dictForm);

    /**
     * 修改字典
     *
     * @param id       id
     * @param dictForm 字典表单
     * @return boolean
     * @author sichu huang
     * @since 2024/10/16 23:54:30
     */
    boolean updateDict(Long id, DictForm dictForm);

    /**
     * 删除字典
     *
     * @param idsStr 字典ID，多个以英文逗号(,)分割
     * @author sichu huang
     * @since 2024/10/16 23:54:51
     */
    void deleteDictByIds(String idsStr);

    /**
     * 获取字典的数据项
     *
     * @param code 字典编码
     * @return java.util.List<cn.sichu.model.Option < java.lang.Long>>
     * @author sichu huang
     * @since 2024/10/16 23:55:08
     */
    List<Option<Long>> listDictItemsByCode(String code);

    /**
     * 获取字典列表
     *
     * @return java.util.List<cn.sichu.model.Option < java.lang.String>>
     * @author sichu huang
     * @since 2024/10/16 23:55:18
     */
    List<Option<String>> getDictList();
}
