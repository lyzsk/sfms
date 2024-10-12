package cn.sichu.crud.mp.service;

import cn.hutool.core.lang.tree.Tree;
import cn.sichu.crud.core.model.query.PageQuery;
import cn.sichu.crud.core.model.query.SortQuery;
import cn.sichu.crud.core.model.resp.LabelValueResp;
import cn.sichu.crud.mp.model.resp.PageResp;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * 业务接口基类
 *
 * @param <L> 列表类型
 * @param <D> 详情类型
 * @param <Q> 查询条件
 * @param <C> 创建或修改类型
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface BaseService<L, D, Q, C> {

    /**
     * 分页查询列表
     *
     * @param query     查询条件
     * @param pageQuery 分页查询条件
     * @return 分页列表信息
     */
    PageResp<L> page(Q query, PageQuery pageQuery);

    /**
     * 查询树列表
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @param isSimple  是否为简单树结构（不包含基本树结构之外的扩展字段）
     * @return 树列表信息
     */
    List<Tree<Long>> tree(Q query, SortQuery sortQuery, boolean isSimple);

    /**
     * 查询列表
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @return 列表信息
     */
    List<L> list(Q query, SortQuery sortQuery);

    /**
     * 查询详情
     *
     * @param id ID
     * @return 详情信息
     */
    D get(Long id);

    /**
     * 查询字典列表
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @return 字典列表信息
     * @since 2.1.0
     */
    List<LabelValueResp> listDict(Q query, SortQuery sortQuery);

    /**
     * 新增
     *
     * @param req 创建信息
     * @return 自增 ID
     */
    Long add(C req);

    /**
     * 修改
     *
     * @param req 修改信息
     * @param id  ID
     */
    void update(C req, Long id);

    /**
     * 删除
     *
     * @param ids ID 列表
     */
    void delete(List<Long> ids);

    /**
     * 导出
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @param response  响应对象
     */
    void export(Q query, SortQuery sortQuery, HttpServletResponse response);
}
