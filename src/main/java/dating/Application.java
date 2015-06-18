package dating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import dating.model.Customer.Gender;
import dating.model.Message;
import dating.service.Customer;
import dating.util.AgeRange;
import dating.util.IncomeRange;
import dating.util.PartnerCriteria;

public class Application {

	public enum APP_STATE {
		DASHBOARD, SHOW_CUSTOMERS, //
		ADDING_ADVERTISER, ADDING_RESPONDER, REMOVING_CUSTOMER, SHOWING_CUSTOMER_DETAIL, //
		SHOW_ADVERTISER_MESSAGES, READING_ADVERTISER_MESSAGE, REMOVING_ADVERTISER_MESSAGE, //
		SHOW_MATCHING_ADVERTISERS_FOR_RESPONDER, SENDING_RESPONDER_MESSAGE_TO_ADVERTISER
	}

	public static APP_STATE state = APP_STATE.DASHBOARD;
	public static int selectedCustomerIndex = -1;
	public static HashMap<String, APP_STATE> actionsMap = new HashMap<String, APP_STATE>();

	public static void main(String[] args) throws IOException {
		printInstruction();
		setupMaps();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));
		while (true) {
			process(bufferedReader);
		}
	}

	public static void setupMaps() {
		actionsMap.put("0", APP_STATE.DASHBOARD);
		actionsMap.put("1", APP_STATE.SHOW_CUSTOMERS);
		actionsMap.put("2", APP_STATE.ADDING_ADVERTISER);
		actionsMap.put("3", APP_STATE.ADDING_RESPONDER);
		actionsMap.put("4", APP_STATE.REMOVING_CUSTOMER);
		actionsMap.put("5", APP_STATE.SHOWING_CUSTOMER_DETAIL);
		actionsMap.put("6", APP_STATE.SHOW_ADVERTISER_MESSAGES);
		actionsMap.put("7", APP_STATE.READING_ADVERTISER_MESSAGE);
		actionsMap.put("8", APP_STATE.REMOVING_ADVERTISER_MESSAGE);
		actionsMap.put("9", APP_STATE.SHOW_MATCHING_ADVERTISERS_FOR_RESPONDER);
		actionsMap.put("10", APP_STATE.SENDING_RESPONDER_MESSAGE_TO_ADVERTISER);
	}

	public static void printInstruction() throws IOException {
		// Runtime.getRuntime().exec("clear");
		System.out.println("Please choose one option below:");
		switch (state) {
		case SHOW_CUSTOMERS:
		case DASHBOARD:
		default:
			if (Customer.customers.size() > 0) {
				System.out.println("1. Print customers");
			}
			System.out.println("2. Add an advertiser");
			System.out.println("3. Add a responder");
			if (Customer.customers.size() > 0) {
				System.out.println("4. Remove a customer");
				System.out.println("5. Show customer's detail");
			}
			break;
		}
		System.out.println("0. Dashboard");
	}

	public static void process(BufferedReader bufferedReader)
			throws IOException {
		String input = bufferedReader.readLine().trim();
		setState(input);
		printInstruction();
		switch (state) {
		case SHOW_CUSTOMERS:
			printCustomers(Customer.getAllCustomers());
			break;
		case ADDING_ADVERTISER:
			createAdvertiser(bufferedReader);
			break;
		case ADDING_RESPONDER:
			createResponder(bufferedReader);
			break;
		case REMOVING_CUSTOMER:
			break;
		case SHOWING_CUSTOMER_DETAIL:
			showCustomerDetail(bufferedReader);
			break;
		case SHOW_ADVERTISER_MESSAGES:
			break;
		case READING_ADVERTISER_MESSAGE:
			break;
		case REMOVING_ADVERTISER_MESSAGE:
			break;
		case SHOW_MATCHING_ADVERTISERS_FOR_RESPONDER:
			break;
		case SENDING_RESPONDER_MESSAGE_TO_ADVERTISER:
			break;
		case DASHBOARD:
		default:
			break;
		}
	}

	private static void showCustomerDetail(BufferedReader bufferedReader) {
		if (Customer.customers.size() > 0) {
			while (true) {
				System.out.println("Please enter customer index:");
				try {
					int idx = Integer
							.parseInt(bufferedReader.readLine().trim());
					if (idx >= 0 && idx < Customer.customers.size()) {
						selectedCustomerIndex = idx;
						dating.model.Customer customer = Customer.customers
								.get(selectedCustomerIndex);
						if (customer instanceof dating.model.Advertiser) {
							printAdvertiser(selectedCustomerIndex,
									(dating.model.Advertiser) customer);
							printAdvertiserMessages((dating.model.Advertiser) customer);
						} else if (customer instanceof dating.model.Responder) {
							printResponder(selectedCustomerIndex,
									(dating.model.Responder) customer);
						}
						break;
					}
				} catch (Exception e) {
				}
			}
		}
	}

	private static void createResponder(BufferedReader bufferedReader)
			throws IOException {
		dating.model.Responder responder = Customer.createResponder();
		while (true) {
			System.out.println("Please enter username:");
			String username = bufferedReader.readLine().trim();
			if (username != "") {
				responder.username = username;
				break;
			}
		}
		while (true) {
			System.out.println("Please enter password:");
			String password = bufferedReader.readLine().trim();
			if (password != "") {
				responder.password = password;
				break;
			}
		}
		while (true) {
			System.out
					.println("Please enter gender:0 for female, not 0 for male:");
			String gender = bufferedReader.readLine().trim();
			if (gender.equalsIgnoreCase("0")) {
				responder.gender = Gender.FEMALE;
			} else {
				responder.gender = Gender.MALE;
			}
			break;
		}
		while (true) {
			System.out.println("Please enter age:");
			String age = bufferedReader.readLine().trim();
			try {
				responder.age = Float.parseFloat(age);
				if (responder.age > 0 && responder.age < 200) {
					break;
				}
			} catch (Exception e) {
			}
		}
		while (true) {
			System.out.println("Please enter income range: from-to");
			responder.incomeRange = new IncomeRange();
			String incomeRange = bufferedReader.readLine().trim();
			String[] range = incomeRange.split("-");
			if (range.length == 1) {
				try {
					responder.incomeRange.from = (int) Float
							.parseFloat(range[0]);
				} catch (Exception e) {
				}
			} else if (range.length > 1) {
				try {
					responder.incomeRange.from = (int) Float
							.parseFloat(range[0]);
					responder.incomeRange.to = (int) Float.parseFloat(range[1]);
				} catch (Exception e) {
				}
			}
			break;
		}
	}

	private static void createAdvertiser(BufferedReader bufferedReader)
			throws IOException {
		dating.model.Advertiser advertiser = Customer.createAdvertiser();
		while (true) {
			System.out.println("Please enter username:");
			String username = bufferedReader.readLine().trim();
			if (username != "") {
				advertiser.username = username;
				break;
			}
		}
		while (true) {
			System.out.println("Please enter password:");
			String password = bufferedReader.readLine().trim();
			if (password != "") {
				advertiser.password = password;
				break;
			}
		}
		while (true) {
			System.out
					.println("Please enter gender:0 for female, not 0 for male:");
			String gender = bufferedReader.readLine().trim();
			if (gender.equalsIgnoreCase("0")) {
				advertiser.gender = Gender.FEMALE;
			} else {
				advertiser.gender = Gender.MALE;
			}
			break;
		}
		while (true) {
			System.out.println("Please enter age:");
			String age = bufferedReader.readLine().trim();
			try {
				advertiser.age = Float.parseFloat(age);
				if (advertiser.age > 0 && advertiser.age < 200) {
					break;
				}
			} catch (Exception e) {
			}
		}
		while (true) {
			System.out.println("Please enter income range: from-to");
			advertiser.incomeRange = new IncomeRange();
			String incomeRange = bufferedReader.readLine().trim();
			String[] range = incomeRange.split("-");
			if (range.length == 1) {
				try {
					advertiser.incomeRange.from = (int) Float
							.parseFloat(range[0]);
				} catch (Exception e) {
				}
			} else if (range.length > 1) {
				try {
					advertiser.incomeRange.from = (int) Float
							.parseFloat(range[0]);
					advertiser.incomeRange.to = (int) Float
							.parseFloat(range[1]);
				} catch (Exception e) {
				}
			}
			break;
		}
		while (true) {
			System.out.println("Please enter some advert text:");
			String advertText = bufferedReader.readLine().trim();
			advertiser.advertText = advertText;
			break;
		}
		advertiser.partnerCriteria = new PartnerCriteria();
		while (true) {
			System.out
					.println("Please enter partner gender:0 for female, not 0 for male:");
			String gender = bufferedReader.readLine().trim();
			if (gender.equalsIgnoreCase("0")) {
				advertiser.partnerCriteria.gender = Gender.FEMALE;
			} else {
				advertiser.partnerCriteria.gender = Gender.MALE;
			}
			break;
		}
		while (true) {
			System.out.println("Please enter partner age range: from-to");
			advertiser.partnerCriteria.ageRange = new AgeRange();
			String ageRange = bufferedReader.readLine().trim();
			String[] range = ageRange.split("-");
			if (range.length == 1) {
				try {
					advertiser.partnerCriteria.ageRange.from = (int) Float
							.parseFloat(range[0]);
				} catch (Exception e) {
				}
			} else if (range.length > 1) {
				try {
					advertiser.partnerCriteria.ageRange.from = (int) Float
							.parseFloat(range[0]);
					advertiser.partnerCriteria.ageRange.to = (int) Float
							.parseFloat(range[1]);
				} catch (Exception e) {
				}
			}
			break;
		}
		while (true) {
			System.out.println("Please enter partner income range: from-to");
			advertiser.partnerCriteria.incomeRange = new IncomeRange();
			String incomeRange = bufferedReader.readLine().trim();
			String[] range = incomeRange.split("-");
			if (range.length == 1) {
				try {
					advertiser.partnerCriteria.incomeRange.from = (int) Float
							.parseFloat(range[0]);
				} catch (Exception e) {
				}
			} else if (range.length > 1) {
				try {
					advertiser.partnerCriteria.incomeRange.from = (int) Float
							.parseFloat(range[0]);
					advertiser.partnerCriteria.incomeRange.to = (int) Float
							.parseFloat(range[1]);
				} catch (Exception e) {
				}
			}
			break;
		}
	}

	public static void setState(String newState) {
		if (actionsMap.containsKey(newState)) {
			state = actionsMap.get(newState);
		} else {
			state = APP_STATE.DASHBOARD;
		}
	}

	public static void printCustomers(ArrayList<?> customers) {
		if (customers.size() <= 0) {
			System.out.println("There is no customer.");
			return;
		}
		int i = 0;
		for (Object object : customers) {
			if (object instanceof dating.model.Advertiser) {
				printAdvertiser(i, (dating.model.Advertiser) object);
			} else if (object instanceof dating.model.Responder) {
				printResponder(i, (dating.model.Responder) object);
			}
			i++;
		}
	}

	public static void printAdvertiser(int idx,
			dating.model.Advertiser advertiser) {
		System.out.println("---------------Advertiser---------------");
		System.out.println("Index		: " + idx);
		System.out.println("Username	: " + advertiser.username);
		// System.out.println("Password	: " + advertiser.password);
		System.out.println("Gender		: "
				+ (advertiser.gender == Gender.MALE ? "Male" : "Female"));
		System.out.println("Age		: " + advertiser.age);
		System.out.println("Income		: " + advertiser.incomeRange.from + " -> "
				+ advertiser.incomeRange.to);
		System.out.println("Text advert	: " + advertiser.advertText);
		System.out.println("Partner criteria");
		System.out.println("Gender		: "
				+ (advertiser.partnerCriteria.gender == Gender.MALE ? "Male"
						: "Female"));
		System.out.println("Age		: " + advertiser.partnerCriteria.ageRange.from
				+ " -> " + advertiser.partnerCriteria.ageRange.to);
		System.out.println("Income		: "
				+ advertiser.partnerCriteria.incomeRange.from + " -> "
				+ advertiser.partnerCriteria.incomeRange.to);
		System.out.println("----------------------------------------");
	}

	public static void printAdvertiserMessages(
			dating.model.Advertiser advertiser) {
		System.out.println("Messages");
		int idx = 0;
		for (Message message : advertiser.messages) {
			printMessage(idx, message);
			idx++;
		}
	}

	public static void printMessage(int idx, Message message) {
		System.out.println("" + idx + ") From: " + message.owner.username);
		System.out.println(message.text);
	}

	public static void printResponder(int idx, dating.model.Responder responder) {
		System.out.println("---------------Responder----------------");
		System.out.println("Index		: " + idx);
		System.out.println("Username	: " + responder.username);
		// System.out.println("Password	: " + responder.password);
		System.out.println("Gender		: "
				+ (responder.gender == Gender.MALE ? "Male" : "Female"));
		System.out.println("Age		: " + responder.age);
		System.out.println("Income		: " + responder.incomeRange.from + " -> "
				+ responder.incomeRange.to);
		System.out.println("----------------------------------------");
	}

}
