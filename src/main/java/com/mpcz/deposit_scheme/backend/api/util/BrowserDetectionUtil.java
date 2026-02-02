package com.mpcz.deposit_scheme.backend.api.util;

import javax.servlet.http.HttpServletRequest;

public class BrowserDetectionUtil {

	private static String browser = "";

	public synchronized static String getBrowserName(HttpServletRequest request) {

		String userAgent = request.getHeader("User-Agent");
		
		if(userAgent != null) {
			
			
			if (userAgent.contains("msie")) {
				String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
				browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
			} else if (userAgent.contains("safari") && userAgent.contains("version")) {
				browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			} else if (userAgent.contains("opr") || userAgent.contains("opera")) {
				if (userAgent.contains("opera"))
					browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
							+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
				else if (userAgent.contains("opr"))
					browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
							.replace("OPR", "Opera");
			} else if (userAgent.contains("chrome")) {
				browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
			} else if ((userAgent.indexOf("mozilla/5.0") > -1) || (userAgent.indexOf("netscape6") != -1)
					|| (userAgent.indexOf("mozilla/4.7") != -1) || (userAgent.indexOf("mozilla/4.78") != -1)
					|| (userAgent.indexOf("mozilla/4.08") != -1) || (userAgent.indexOf("mozilla/3") != -1)) {
				// browser=(userAgent.substring(userAgent.indexOf("MSIE")).split("
				// ")[0]).replace("/", "-");
				browser = "Netscape-?";

			} else if (userAgent.contains("firefox")) {
				browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
			} else if (userAgent.contains("rv")) {
				browser = "IE-" + userAgent.substring(userAgent.indexOf("rv") + 3, userAgent.indexOf(")"));
			} else {
				browser = "UnKnown, More-Info: " + userAgent;
			}

		} else {
			browser = "UnKnown, source ";

		}

		return browser;
	}
}
