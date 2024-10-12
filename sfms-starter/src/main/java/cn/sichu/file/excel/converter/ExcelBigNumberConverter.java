package cn.sichu.file.excel.converter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Easy Excel 大数值转换器
 * <p>
 * Excel 中对长度超过 15 位的数值输入是有限制的，从 16 位开始无论录入什么数字均会变为 0，因此输入时只能以文本的形式进行录入
 * </p>
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class ExcelBigNumberConverter implements Converter<Long> {

    /**
     * Excel 输入数值长度限制
     */
    private static final int MAX_LENGTH = 15;

    @Override
    public Class<Long> supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 转换为 Java 数据（读取 Excel）
     */
    @Override
    public Long convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return Convert.toLong(cellData.getData());
    }

    /**
     * 转换为 Excel 数据（写入 Excel）
     */
    @Override
    public WriteCellData<Object> convertToExcelData(Long value,
        ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (null != value) {
            String str = Long.toString(value);
            if (str.length() > MAX_LENGTH) {
                return new WriteCellData<>(str);
            }
        }
        WriteCellData<Object> writeCellData = new WriteCellData<>(NumberUtil.toBigDecimal(value));
        writeCellData.setType(CellDataTypeEnum.NUMBER);
        return writeCellData;
    }
}
