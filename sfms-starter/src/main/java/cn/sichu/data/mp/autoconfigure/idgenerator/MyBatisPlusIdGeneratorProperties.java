package cn.sichu.data.mp.autoconfigure.idgenerator;

import cn.sichu.data.mp.enums.MyBatisPlusIdGeneratorType;
import lombok.Getter;
import lombok.Setter;

/**
 * MyBatis ID 生成器配置属性
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
@Setter
@Getter
public class MyBatisPlusIdGeneratorProperties {

    /**
     * ID 生成器类型
     */
    private MyBatisPlusIdGeneratorType type = MyBatisPlusIdGeneratorType.DEFAULT;

}
