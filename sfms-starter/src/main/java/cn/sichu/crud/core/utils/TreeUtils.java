package cn.sichu.crud.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.util.ReflectUtil;
import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.core.annotation.TreeField;

import java.util.ArrayList;
import java.util.List;

/**
 * 树工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class TreeUtils {

    /**
     * 默认字段配置对象（根据前端树结构灵活调整名称）
     */
    public static final TreeNodeConfig DEFAULT_CONFIG =
        TreeNodeConfig.DEFAULT_CONFIG.setNameKey("title").setIdKey("key").setWeightKey("sort");

    private TreeUtils() {
    }

    /**
     * 树构建
     *
     * @param <T>        转换的实体 为数据源里的对象类型
     * @param <E>        ID类型
     * @param list       源数据集合
     * @param nodeParser 转换器
     * @return List 树列表
     */
    public static <T, E> List<Tree<E>> build(List<T> list, NodeParser<T, E> nodeParser) {
        return build(list, DEFAULT_CONFIG, nodeParser);
    }

    /**
     * 树构建
     *
     * @param <T>            转换的实体 为数据源里的对象类型
     * @param <E>            ID类型
     * @param list           源数据集合
     * @param treeNodeConfig 配置
     * @param nodeParser     转换器
     * @return List 树列表
     */
    public static <T, E> List<Tree<E>> build(List<T> list, TreeNodeConfig treeNodeConfig,
        NodeParser<T, E> nodeParser) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>(0);
        }
        E parentId = (E)ReflectUtil.getFieldValue(list.get(0), treeNodeConfig.getParentIdKey());
        return TreeUtil.build(list, parentId, treeNodeConfig, nodeParser);
    }

    /**
     * 根据 @TreeField 配置生成树结构配置
     *
     * @param treeField 树结构字段注解
     * @return 树结构配置
     */
    public static TreeNodeConfig genTreeNodeConfig(TreeField treeField) {
        CheckUtils.throwIfNull(treeField, "请添加并配置 @TreeField 树结构信息");
        return new TreeNodeConfig().setIdKey(treeField.value())
            .setParentIdKey(treeField.parentIdKey()).setNameKey(treeField.nameKey())
            .setWeightKey(treeField.weightKey()).setChildrenKey(treeField.childrenKey())
            .setDeep(treeField.deep() < 0 ? null : treeField.deep());
    }
}
