package cn.sichu.system.config.file;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.sichu.enums.DisEnableStatusEnum;
import cn.sichu.system.model.query.StorageQuery;
import cn.sichu.system.model.req.StorageReq;
import cn.sichu.system.model.resp.StorageResp;
import cn.sichu.system.service.IStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文件存储配置加载器
 *
 * @author sichu huang
 * @date 2024/10/10
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class FileStorageConfigLoader implements ApplicationRunner {

    private final IStorageService storageService;

    @Override
    public void run(ApplicationArguments args) {
        StorageQuery query = new StorageQuery();
        query.setStatus(DisEnableStatusEnum.ENABLE);
        List<StorageResp> storageList = storageService.list(query, null);
        if (CollUtil.isEmpty(storageList)) {
            return;
        }
        storageList.forEach(s -> storageService.load(BeanUtil.copyProperties(s, StorageReq.class)));
    }
}
