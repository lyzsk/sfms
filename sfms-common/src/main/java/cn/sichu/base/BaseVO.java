package cn.sichu.base;

import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 视图对象基类
 *
 * @author sichu huang
 * @since 2024/10/16 22:05
 */
@Data
@ToString
public class BaseVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
