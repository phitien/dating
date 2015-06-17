package dating.service;

import java.util.ArrayList;

import dating.model.*;

public class Customer {
	public static ArrayList<dating.model.Customer> customers;

	public static ArrayList<dating.model.Customer> getAllCustomers() {
		return customers;
	}

	public static ArrayList<dating.model.Advertiser> getAllAdvertisers() {
		ArrayList<dating.model.Advertiser> result = new ArrayList<dating.model.Advertiser>();
		return result;
	}

	public static ArrayList<dating.model.Responder> getAllResponders() {
		ArrayList<dating.model.Responder> result = new ArrayList<dating.model.Responder>();
		return result;
	}

	public static ArrayList<dating.model.Advertiser> getMatchingAdvertisersOfResponder(
			Responder responder) {
		ArrayList<dating.model.Advertiser> result = new ArrayList<dating.model.Advertiser>();
		ArrayList<Advertiser> allAdvertisers = Customer.getAllAdvertisers();
		for (Advertiser advertiser : allAdvertisers) {
			if (advertiser.match(responder)) {
				result.add(advertiser);
			}
		}
		return result;
	}

	public static dating.model.Customer createAdvertiser() {
		Advertiser customer = new Advertiser();
		Customer.customers.add(customer);
		return customer;
	}

	public static dating.model.Responder createResponder() {
		Responder customer = new Responder();
		Customer.customers.add(customer);
		return customer;
	}

	public static boolean remove(dating.model.Customer customer) {
		return Customer.customers.remove(customer);
	}

	public static boolean sendMessage(Advertiser advertiser, Message message) {
		if (advertiser != null) {
			return advertiser.addMessage(message);
		}
		return false;
	}

	public static boolean removeMessage(Advertiser advertiser, Message message) {
		if (advertiser != null) {
			return advertiser.removeMessage(message);
		}
		return false;
	}

}
