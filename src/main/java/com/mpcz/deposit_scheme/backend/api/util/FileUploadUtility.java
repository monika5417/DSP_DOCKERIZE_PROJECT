package com.mpcz.deposit_scheme.backend.api.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class FileUploadUtility {
	public static Logger LOG = LoggerFactory.getLogger(FileUploadUtility.class);

	@Value("${maxFileCount}")
	private static String maxFileCount;

	public static String uploadFile(byte[] fileContent, String billPath, String uploadFileName, String uploadPath) {

		OutputStream outputStream = null;
		String newPath = "";
		try {
			if (fileContent != null && fileContent.length > 0) {

				if (uploadPath == null || uploadPath.trim().equals("") || !new File(uploadPath).exists()) {
					throw new Exception("Invalid Virtual Directory");
				} else {
					Calendar c = Calendar.getInstance();
					newPath += ("" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) > 8 ? (c.get(Calendar.MONTH) + 1)
							: "0" + (c.get(Calendar.MONTH) + 1)));
					File file = new File(uploadPath + File.separator + billPath + File.separator + newPath);

					if (file.exists() && maxFileCount != null) {
						String tempFilePath = newPath;
						for (int i = 2; file.list().length >= Integer.parseInt(maxFileCount); i++) {
							tempFilePath = newPath + "_" + i;
							file = new File(uploadPath + File.separator + billPath + File.separator + tempFilePath);

							if (!file.exists()) {
								file.mkdir();
								break;
							}
						}
						newPath = tempFilePath;
					} else {
						file.mkdirs();
					}
				}
				newPath = billPath + File.separator + newPath + File.separator + uploadFileName;

				outputStream = new FileOutputStream(uploadPath + File.separator + newPath);
				outputStream.write(fileContent);

			} else {
				LOG.info("Invalid Multipart Object.... " + uploadFileName);

			}
			return newPath;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("File Upload Utility : " + e);
			LOG.error("File Upload Utility : " + uploadFileName);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					LOG.error("File Upload Utility Finally : " + e);
					LOG.error("File Upload Utility Finally : " + uploadFileName);
				}
			}
		}
		return newPath;
	}

}
