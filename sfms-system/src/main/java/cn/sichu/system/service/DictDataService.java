package cn.sichu.system.service;

import cn.sichu.model.Option;
import cn.sichu.system.model.entity.DictData;
import cn.sichu.system.model.form.DictDataForm;
import cn.sichu.system.model.query.DictDataPageQuery;
import cn.sichu.system.model.vo.DictDataPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 字典数据接口
 *
 * @author sichu huang
 * @since 2024/10/16 23:51:56
 */
public interface DictDataService extends IService<DictData> {

    /**
     * 字典数据分页列表
     *
     * @param queryParams queryParams
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.sichu.model.vo.DictDataPageVO>
     * @author sichu huang
     * @since 2024/10/16 23:53:03
     */
    Page<DictDataPageVO> getDictDataPage(DictDataPageQuery queryParams);

    /**
     * 获取字典数据表单
     *
     * @param id 字典数据ID
     * @return cn.sichu.model.form.DictDataForm
     * @author sichu huang
     * @since 2024/10/16 23:52:54
     */
    DictDataForm getDictDataForm(Long id);

    /**
     * 保存字典数据
     *
     * @param formData formData
     * @return boolean
     * @author sichu huang
     * @since 2024/10/16 23:52:44
     */
    boolean saveDictData(DictDataForm formData);

    /**
     * 更新字典数据
     *
     * @param formData 字典数据表单
     * @return boolean
     * @author sichu huang
     * @since 2024/10/16 23:52:35
     */
    boolean updateDictData(DictDataForm formData);

    /**
     * 删除字典数据
     *
     * @param ids 字典数据ID,多个逗号分隔
     * @author sichu huang
     * @since 2024/10/16 23:52:24
     */
    void deleteDictDataByIds(String ids);

    /**
     * 获取字典数据列表
     *
     * @param dictCode 字典编码
     * @return java.util.List<cn.sichu.model.Option < java.lang.String>>
     * @author sichu huang
     * @since 2024/10/16 23:52:15
     */
    List<Option<String>> getDictDataList(String dictCode);
}
