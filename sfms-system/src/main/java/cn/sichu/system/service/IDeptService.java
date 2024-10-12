package cn.sichu.system.service;

import cn.sichu.crud.mp.service.BaseService;
import cn.sichu.data.mp.service.IService;
import cn.sichu.system.model.entity.DeptDO;
import cn.sichu.system.model.query.DeptQuery;
import cn.sichu.system.model.req.DeptReq;
import cn.sichu.system.model.resp.DeptResp;

import java.util.List;

/**
 * 部门业务接口
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
public interface IDeptService
    extends BaseService<DeptResp, DeptResp, DeptQuery, DeptReq>, IService<DeptDO> {
    /**
     * 查询子部门列表
     *
     * @param id id
     * @return java.util.List<cn.sichu.system.model.entity.DeptDO> 子部门列表
     * @author sichu huang
     * @date 2024/10/10
     **/
    List<DeptDO> listChildren(Long id);

    /**
     * 通过名称查询部门
     *
     * @param list 名称列表
     * @return java.util.List<cn.sichu.system.model.entity.DeptDO> 部门列表
     * @author sichu huang
     * @date 2024/10/10
     **/
    List<DeptDO> listByNames(List<String> list);

    /**
     * 通过名称查询部门数量
     *
     * @param deptNames 名称列表
     * @return int 部门数量
     * @author sichu huang
     * @date 2024/10/10
     **/
    int countByNames(List<String> deptNames);
}
