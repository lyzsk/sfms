package cn.sichu.security.mask.strategy;

/**
 * 脱敏策略
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public interface IMaskStrategy {

    /**
     * 数据脱敏
     *
     * @param str       原始字符串
     * @param character 脱敏符号
     * @param left      左侧保留位数
     * @param right     右侧保留位数
     * @return 脱敏后的数据
     */
    String mask(String str, char character, int left, int right);
}
