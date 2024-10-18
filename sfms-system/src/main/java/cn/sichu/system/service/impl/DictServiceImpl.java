package cn.sichu.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.sichu.exception.BusinessException;
import cn.sichu.model.Option;
import cn.sichu.system.converter.DictConverter;
import cn.sichu.system.converter.DictDataConverter;
import cn.sichu.system.mapper.DictMapper;
import cn.sichu.system.model.entity.Dict;
import cn.sichu.system.model.entity.DictData;
import cn.sichu.system.model.form.DictForm;
import cn.sichu.system.model.query.DictPageQuery;
import cn.sichu.system.model.vo.DictPageVO;
import cn.sichu.system.service.DictDataService;
import cn.sichu.system.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author sichu huang
 * @since 2024/10/17 15:47
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private final DictDataService dictDataService;
    private final DictConverter dictConverter;
    private final DictDataConverter dictDataConverter;

    @Override
    public Page<DictPageVO> getDictPage(DictPageQuery queryParams) {
        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();

        // 查询数据
        return this.baseMapper.getDictPage(new Page<>(pageNum, pageSize), queryParams);
    }

    @Override
    public boolean saveDict(DictForm dictForm) {
        // 保存字典
        Dict entity = dictConverter.toEntity(dictForm);

        // 校验 code 是否唯一
        String dictCode = entity.getDictCode();

        long count = this.count(new LambdaQueryWrapper<Dict>().eq(Dict::getDictCode, dictCode));
        Assert.isTrue(count == 0, "字典编码已存在");

        return this.save(entity);
    }

    @Override
    public DictForm getDictForm(Long id) {
        // 获取字典
        Dict entity = this.getById(id);
        if (entity == null) {
            throw new BusinessException("字典不存在");
        }
        return dictConverter.toForm(entity);
    }

    @Override
    public boolean updateDict(Long id, DictForm dictForm) {
        // 更新字典
        Dict entity = dictConverter.toEntity(dictForm);

        // 校验 code 是否唯一
        String dictCode = entity.getDictCode();
        long count = this.count(
            new LambdaQueryWrapper<Dict>().eq(Dict::getDictCode, dictCode).ne(Dict::getId, id));
        if (count > 0) {
            throw new BusinessException("字典编码已存在");
        }

        return this.updateById(entity);
    }

    @Override
    @Transactional
    public void deleteDictByIds(String ids) {

        Assert.isTrue(StrUtil.isNotBlank(ids), "请选择需要删除的字典");

        List<String> idList = Arrays.stream(ids.split(",")).toList();

        for (String id : idList) {
            Dict dict = this.getById(id);
            if (dict != null) {
                boolean removeResult = this.removeById(id);
                // 删除字典下的字典项
                if (removeResult) {
                    dictDataService.remove(
                        new LambdaQueryWrapper<DictData>().eq(DictData::getDictCode,
                            dict.getDictCode()));
                }

            }
        }
    }

    @Override
    public List<Option<Long>> listDictItemsByCode(String code) {
        // 根据字典编码获取字典ID
        Dict dict = this.getOne(
            new LambdaQueryWrapper<Dict>().eq(Dict::getDictCode, code).select(Dict::getId)
                .last("limit 1"));
        // 如果字典不存在，则返回空集合
        if (dict == null) {
            return CollectionUtil.newArrayList();
        }

        // 获取字典项
        List<DictData> dictData = dictDataService.list(
            new LambdaQueryWrapper<DictData>().eq(DictData::getDictCode, dict.getDictCode()));

        // 转换为 Option
        return dictDataConverter.toOption(dictData);
    }

    @Override
    public List<Option<String>> getDictList() {
        return this.list(new LambdaQueryWrapper<Dict>().eq(Dict::getStatus, 1)
                .select(Dict::getName, Dict::getDictCode)).stream()
            .map(dict -> new Option<>(dict.getDictCode(), dict.getName())).toList();
    }
}
