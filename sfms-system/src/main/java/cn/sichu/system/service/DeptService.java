package cn.sichu.system.service;

import cn.sichu.model.Option;
import cn.sichu.system.model.entity.Dept;
import cn.sichu.system.model.form.DeptForm;
import cn.sichu.system.model.query.DeptQuery;
import cn.sichu.system.model.vo.DeptVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author sichu huang
 * @since 2024/10/16 23:46
 */
public interface DeptService extends IService<Dept> {

    /**
     * 部门列表
     *
     * @param queryParams queryParams
     * @return java.util.List<cn.sichu.model.vo.DeptVO> 部门列表
     * @author sichu huang
     * @since 2024/10/16 23:47:03
     */
    List<DeptVO> getDeptList(DeptQuery queryParams);

    /**
     * 部门树形下拉选项
     *
     * @return java.util.List<cn.sichu.model.Option < java.lang.Long>> 部门树形下拉选项
     * @author sichu huang
     * @since 2024/10/16 23:47:17
     */
    List<Option<Long>> listDeptOptions();

    /**
     * 新增部门
     *
     * @param formData 部门表单
     * @return java.lang.Long 部门ID
     * @author sichu huang
     * @since 2024/10/16 23:47:35
     */
    Long saveDept(DeptForm formData);

    /**
     * 修改部门
     *
     * @param deptId   部门ID
     * @param formData 部门表单
     * @return java.lang.Long 部门ID
     * @author sichu huang
     * @since 2024/10/16 23:47:47
     */
    Long updateDept(Long deptId, DeptForm formData);

    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号,拼接字符串
     * @return boolean 是否成功
     * @author sichu huang
     * @since 2024/10/16 23:47:59
     */
    boolean deleteByIds(String ids);

    /**
     * 获取部门详情
     *
     * @param deptId 部门ID
     * @return cn.sichu.model.form.DeptForm 部门详情
     * @author sichu huang
     * @since 2024/10/16 23:48:11
     */
    DeptForm getDeptForm(Long deptId);
}
