package com.newswhip.storeurls.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.newswhip.storeurls.model.ModelUrl;
import com.newswhip.storeurls.validator.InputValidator;

public class ManageUrls {

	InputValidator inputValidator = new InputValidator();

	public void exportUrl(Map<String, List<ModelUrl>> userMap) {

		int top = 0;
		StringBuilder line = new StringBuilder();
		line.append('+');
		while (top < 28) {
			line.append("-");
			top++;
		}
		line.append("+");
		System.out.println(line);

		int totalScore = 0;
		System.out.println("| domain;urls;social_score   |");
		for (Entry<String, List<ModelUrl>> entry : userMap.entrySet()) {
			for (ModelUrl modelUrl : entry.getValue()) {
				totalScore = totalScore + modelUrl.getScore();
			}
			String innerLineStr = entry.getKey() + "," + (entry.getValue()).size() + "," + totalScore;
			StringBuilder innerline = new StringBuilder(innerLineStr);
			innerline.insert(0, "| ");

			int i = 26 - innerLineStr.length();
			for (int j = 0; j <= i; j++) {
				innerline.append(" ");
			}
			innerline.append("|");
			System.out.println(innerline);
			totalScore = 0;
		}
		System.out.println(line + "\n\n");
	}

	public void removeUrl(String[] inputArr, Map<String, List<ModelUrl>> userMap) {
		String domain = inputValidator.getDomainName(inputArr[1]);

		if (userMap.containsKey(domain)) {
			List<ModelUrl> existingModelUrlList = userMap.get(domain);
			boolean removed = existingModelUrlList.removeIf(ModelUrl -> ModelUrl.getUrl().equals(inputArr[1].trim()));
			if (removed) {
				System.out.println("The Url is sucessfully removed");
			} else {
				System.out.println("The specified Url found");
			}
			if (existingModelUrlList.isEmpty()) {
				userMap.remove(domain);
			} else {
				userMap.put(domain, existingModelUrlList);
			}
		}
	}

	public void addUrl(String[] inputArr, Map<String, List<ModelUrl>> userMap) {

		String domain = inputValidator.getDomainName(inputArr[1]);
		List<ModelUrl> newUrlList = new ArrayList<>();

		ModelUrl modelUrl = new ModelUrl();
		modelUrl.setScore(Integer.parseInt(inputArr[2]));
		modelUrl.setUrl(inputArr[1].trim());

		newUrlList.add(modelUrl);

		if (userMap.containsKey(domain)) {
			List<ModelUrl> existingModelUrlList = userMap.get(domain);
			boolean isUrlExists=existingModelUrlList.stream().anyMatch(ModelUrl -> ModelUrl.getUrl().equals(inputArr[1].trim()));
			if(isUrlExists)
			{
				//if Url already exists increase only the score 
				ModelUrl existingModelUrl = existingModelUrlList.stream().filter(ModelUrl -> ModelUrl.getUrl().equals(inputArr[1].trim())).findAny().orElse(null);
				modelUrl.setScore(existingModelUrl.getScore() + Integer.parseInt(inputArr[2]));
				existingModelUrlList.remove(existingModelUrl);
			}
			existingModelUrlList.add(modelUrl);
			userMap.put(domain, existingModelUrlList);
			System.out.println("The Url is sucessfully added");
		} else {
			userMap.put(domain, newUrlList);
			System.out.println("The Url is sucessfully added");
		}
	}

}
