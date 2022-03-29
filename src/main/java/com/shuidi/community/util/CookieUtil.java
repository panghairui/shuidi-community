package com.shuidi.community.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description: cookie工具类
 * @Author: panghairui
 * @Date: 2022/3/29 8:02 下午
 */
public class CookieUtil {

    public static String getValue(HttpServletRequest request, String name) {
        if (Objects.isNull(request) || StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("参数为空！");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

}
