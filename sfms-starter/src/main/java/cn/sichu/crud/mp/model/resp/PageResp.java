package cn.sichu.crud.mp.model.resp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.sichu.crud.core.model.resp.BasePageResp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分页信息
 *
 * @param <L> 列表数据类型
 * @author sichu huang
 * @date 2024/10/11
 **/
@Schema(description = "分页信息")
public class PageResp<L> extends BasePageResp<L> {

    @Serial
    private static final long serialVersionUID = 1L;

    public PageResp() {
    }

    public PageResp(final List<L> list, final long total) {
        super(list, total);
    }

    /**
     * 基于 MyBatis Plus 分页数据构建分页信息，并将源数据转换为指定类型数据
     *
     * @param page        MyBatis Plus 分页数据
     * @param targetClass 目标类型 Class 对象
     * @param <T>         源列表数据类型
     * @param <L>         目标列表数据类型
     * @return 分页信息
     */
    public static <T, L> PageResp<L> build(IPage<T> page, Class<L> targetClass) {
        if (null == page) {
            return empty();
        }
        return new PageResp<>(BeanUtil.copyToList(page.getRecords(), targetClass), page.getTotal());
    }

    /**
     * 基于 MyBatis Plus 分页数据构建分页信息
     *
     * @param page MyBatis Plus 分页数据
     * @param <L>  列表数据类型
     * @return 分页信息
     */
    public static <L> PageResp<L> build(IPage<L> page) {
        if (null == page) {
            return empty();
        }
        return new PageResp<>(page.getRecords(), page.getTotal());
    }

    /**
     * 基于列表数据构建分页信息
     *
     * @param page 页码
     * @param size 每页条数
     * @param list 列表数据
     * @param <L>  列表数据类型
     * @return 分页信息
     */
    public static <L> PageResp<L> build(int page, int size, List<L> list) {
        if (CollUtil.isEmpty(list)) {
            return empty();
        }
        PageResp<L> pageResp = new PageResp<>();
        pageResp.setTotal(list.size());
        // 对列表数据进行分页
        int fromIndex = (page - 1) * size;
        int toIndex = page * size + fromIndex;
        if (fromIndex > list.size()) {
            pageResp.setList(new ArrayList<>(0));
        } else if (toIndex >= list.size()) {
            pageResp.setList(list.subList(fromIndex, list.size()));
        } else {
            pageResp.setList(list.subList(fromIndex, toIndex));
        }
        return pageResp;
    }

    /**
     * 空分页信息
     *
     * @param <L> 列表数据类型
     * @return 分页信息
     */
    private static <L> PageResp<L> empty() {
        return new PageResp<>(Collections.emptyList(), 0L);
    }
}
