package com.mpcz.deposit_scheme.backend.api.util;

import javax.servlet.http.HttpServletRequest;

public class OpreatingSystemDetectionUtil {
	private static String os = "";

	public synchronized static String getOSName(HttpServletRequest request) {

		String userAgent = request.getHeader("User-Agent");

		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		return os;
	}
}
