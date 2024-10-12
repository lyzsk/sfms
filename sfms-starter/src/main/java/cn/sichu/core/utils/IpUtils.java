package cn.sichu.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HtmlUtil;
import cn.sichu.core.constant.StringConstants;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import net.dreamlu.mica.ip2region.core.IpInfo;

import java.util.Set;

/**
 * IP 工具类
 *
 * @author sichu huang
 * @date 2024/10/11
 **/
public class IpUtils {

    private IpUtils() {
    }

    /**
     * 查询 IP 归属地（本地库解析）
     *
     * @param ip IP 地址
     * @return IP 归属地
     */
    public static String getIpv4Address(String ip) {
        if (isInnerIpv4(ip)) {
            return "内网IP";
        }
        Ip2regionSearcher ip2regionSearcher = SpringUtil.getBean(Ip2regionSearcher.class);
        IpInfo ipInfo = ip2regionSearcher.memorySearch(ip);
        if (null != ipInfo) {
            Set<String> regionSet = CollUtil.newLinkedHashSet(ipInfo.getAddress(), ipInfo.getIsp());
            regionSet.removeIf(CharSequenceUtil::isBlank);
            return String.join(StringConstants.SPACE, regionSet);
        }
        return null;
    }

    /**
     * 是否为内网 IPv4
     *
     * @param ip IP 地址
     * @return 是否为内网 IP
     */
    public static boolean isInnerIpv4(String ip) {
        return NetUtil.isInnerIP(
            "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : HtmlUtil.cleanHtmlTag(ip));
    }
}
