package com.mpcz.deposit_scheme.backend.api.util;

import javax.servlet.http.HttpServletRequest;
/*
 * This utility class is used to get IP address of system.
 * @Author Aditya Vyas
 * @since 1.0
 */
public class InternetProtocolAddressUtil {

	private static String ipAddress = null;

	public static synchronized String getIpAddress(final HttpServletRequest request) {

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		return ipAddress;
	}
	
	
	public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
 }
	
	public static String getUserAgent(HttpServletRequest request) {
	    String ua = "";
	    if (request != null) {
	        ua = request.getHeader("User-Agent");
	    }
	    return ua;
	}
}
