package com.rhinode.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class FileReader {

	public static class FileReadException extends RuntimeException {

		public FileReadException() {
			// TODO Auto-generated constructor stub
		}

		public FileReadException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public FileReadException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}

		public FileReadException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public FileReadException(String message, Throwable cause,
				boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
			// TODO Auto-generated constructor stub
		}

	}

	private final File file;
	public FileReader(String fileName) {
		file = new File(fileName);
	}
	
	public File getFile() {
		return file;
	}

	public String read() {
		ByteArrayOutputStream scriptTextStream = new ByteArrayOutputStream();
    	try {
			IOUtils.copy(new FileInputStream(file), scriptTextStream);
			return new String(scriptTextStream.toByteArray());
		} catch (IOException e) {
			throw new FileReadException(e);
		}
	}
}
