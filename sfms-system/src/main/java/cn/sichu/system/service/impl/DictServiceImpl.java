package cn.sichu.system.service.impl;

import cn.sichu.core.utils.validate.CheckUtils;
import cn.sichu.crud.core.model.resp.LabelValueResp;
import cn.sichu.crud.mp.service.impl.BaseServiceImpl;
import cn.sichu.system.mapper.DictMapper;
import cn.sichu.system.model.entity.DictDO;
import cn.sichu.system.model.query.DictQuery;
import cn.sichu.system.model.req.DictReq;
import cn.sichu.system.model.resp.DictResp;
import cn.sichu.system.service.IDictItemService;
import cn.sichu.system.service.IDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 字典业务实现
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Service
@RequiredArgsConstructor
public class DictServiceImpl
    extends BaseServiceImpl<DictMapper, DictDO, DictResp, DictResp, DictQuery, DictReq>
    implements IDictService {

    private final IDictItemService dictItemService;

    @Override
    protected void beforeAdd(DictReq req) {
        String name = req.getName();
        CheckUtils.throwIf(this.isNameExists(name, null), "新增失败，[{}] 已存在", name);
        String code = req.getCode();
        CheckUtils.throwIf(this.isCodeExists(code, null), "新增失败，[{}] 已存在", code);
    }

    @Override
    protected void beforeUpdate(DictReq req, Long id) {
        String name = req.getName();
        CheckUtils.throwIf(this.isNameExists(name, id), "修改失败，[{}] 已存在", name);
        DictDO oldDict = super.getById(id);
        CheckUtils.throwIfNotEqual(req.getCode(), oldDict.getCode(), "不允许修改字典编码");
    }

    @Override
    protected void beforeDelete(List<Long> ids) {
        List<DictDO> list = baseMapper.lambdaQuery().select(DictDO::getName, DictDO::getIsSystem)
            .in(DictDO::getId, ids).list();
        Optional<DictDO> isSystemData = list.stream().filter(DictDO::getIsSystem).findFirst();
        CheckUtils.throwIf(isSystemData::isPresent, "所选字典 [{}] 是系统内置字典，不允许删除",
            isSystemData.orElseGet(DictDO::new).getName());
        dictItemService.deleteByDictIds(ids);
    }

    @Override
    public List<LabelValueResp> listEnumDict() {
        List<String> enumDictNameList = dictItemService.listEnumDictNames();
        return enumDictNameList.stream().map(name -> new LabelValueResp(name, name)).toList();
    }

    /**
     * 名称是否存在
     *
     * @param name 名称
     * @param id   ID
     * @return boolean 是否存在
     * @author sichu huang
     * @date 2024/10/12
     **/
    private boolean isNameExists(String name, Long id) {
        return baseMapper.lambdaQuery().eq(DictDO::getName, name).ne(null != id, DictDO::getId, id)
            .exists();
    }

    /**
     * 编码是否存在
     *
     * @param code 编码
     * @param id   ID
     * @return boolean 是否存在
     * @author sichu huang
     * @date 2024/10/12
     **/
    private boolean isCodeExists(String code, Long id) {
        return baseMapper.lambdaQuery().eq(DictDO::getCode, code).ne(null != id, DictDO::getId, id)
            .exists();
    }
}