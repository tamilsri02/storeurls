package com.newswhip.storeurls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.newswhip.storeurls.model.ModelUrl;
import com.newswhip.storeurls.validator.InputValidator;
import com.newswhip.storeurls.service.ManageUrls;

public class NewsWhipScanner {
	
	private static Map<String, List<ModelUrl>> userMap = new HashMap<>();
	
	static InputValidator inputValidator=new InputValidator();
	static ManageUrls manageUrls=new ManageUrls();

	public static void main(String[] args) {

		String userInput;
		Scanner input = new Scanner(System.in);

		try {
			loop: while (true) {

				System.out.println("Please Enter Your Input in below format to Manage URLs\n");
				System.out.println("\t--> ADD URL SOCIAL_SCORE Ex:- ADD https://test.com 1");
				System.out.println("\t--> REMOVE URL Ex:- REMOVE https://test.com");
				System.out.println("\t--> EXPORT");
				System.out.println("\t--> EXIT\n");
				userInput = input.nextLine();

				String[] inputArr = userInput.split(" ");

				switch (inputArr[0]) {
				case "ADD":
					if (inputValidator.validateAddUrlInput(inputArr)) {
						manageUrls.addUrl(inputArr,userMap);
						continue;
					} else {
						System.out.println("Please provide valid Input\n\n");
						continue;
					}

				case "REMOVE":
					if (inputValidator.validateRemoveUrlInput(inputArr)) {
						manageUrls.removeUrl(inputArr,userMap);
						continue;
					} else {
						System.out.println("Please provide valid Input\n\n");
						continue;
					}

				case "EXPORT":
					manageUrls.exportUrl(userMap);
					continue;

				case "EXIT":
					System.out.println("BYE !! Have a Wonderful day!!");
					break loop;

				default:
					System.out.println("Please provide valid Input\n\n");
					continue;

				}
			}
		} finally {
			input.close();
		}
	}

}
