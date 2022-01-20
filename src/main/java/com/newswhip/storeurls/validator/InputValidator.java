package com.newswhip.storeurls.validator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class InputValidator {
	
	public  boolean validateAddUrlInput(String[] inputArr) {
		if (inputArr.length != 3) {
			return false;
		}
		try {
			URL url = new URL(inputArr[1]);
			Integer.parseInt(inputArr[2]);
		} catch (NumberFormatException | MalformedURLException e) {
			return false;
		}
		return true;
	}

	public  boolean validateRemoveUrlInput(String[] inputArr) {
		if (inputArr.length != 2) {
			return false;
		}
		try {
			URL url = new URL(inputArr[1]);
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}

	public  String getDomainName(String url) {
		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String domain = uri.getHost();
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

}
