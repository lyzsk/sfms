package cn.sichu.system.service.impl;

import cn.sichu.model.Option;
import cn.sichu.system.converter.DictDataConverter;
import cn.sichu.system.mapper.DictDataMapper;
import cn.sichu.system.model.entity.DictData;
import cn.sichu.system.model.form.DictDataForm;
import cn.sichu.system.model.query.DictDataPageQuery;
import cn.sichu.system.model.vo.DictDataPageVO;
import cn.sichu.system.service.DictDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author sichu huang
 * @since 2024/10/17 15:48
 */
@Service
@RequiredArgsConstructor
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData>
    implements DictDataService {

    private final DictDataConverter dictDataConverter;

    @Override
    public Page<DictDataPageVO> getDictDataPage(DictDataPageQuery queryParams) {
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<DictDataPageVO> page = new Page<>(pageNum, pageSize);

        return this.baseMapper.getDictDataPage(page, queryParams);
    }

    @Override
    public DictDataForm getDictDataForm(Long id) {
        DictData entity = this.getById(id);
        return dictDataConverter.toForm(entity);
    }

    @Override
    public boolean saveDictData(DictDataForm formData) {
        DictData entity = dictDataConverter.toEntity(formData);
        return this.save(entity);
    }

    @Override
    public boolean updateDictData(DictDataForm formData) {
        DictData entity = dictDataConverter.toEntity(formData);
        return this.updateById(entity);
    }

    @Override
    public void deleteDictDataByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
        this.removeByIds(idList);
    }

    @Override
    public List<Option<String>> getDictDataList(String dictCode) {
        return this.list(new LambdaQueryWrapper<DictData>().eq(DictData::getDictCode, dictCode)
                .eq(DictData::getStatus, 1)).stream()
            .map(item -> new Option<>(item.getValue(), item.getLabel(), item.getTagType()))
            .toList();
    }
}
