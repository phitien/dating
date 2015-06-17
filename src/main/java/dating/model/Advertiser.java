package dating.model;

import java.util.ArrayList;

import dating.util.PartnerCriteria;

public class Advertiser extends Customer {
	public String advertText = "";
	public PartnerCriteria partnerCriteria = new PartnerCriteria();
	public ArrayList<Message> messages = new ArrayList<Message>();

	public Advertiser() {
		super();
		this.type = Type.ADVERTISER;
	}

	public boolean match(Responder responder) {
		return this.partnerCriteria.match(responder);
	}

	public boolean addMessage(Message message) {
		this.messages.add(message);
		return true;
	}

	public boolean removeMessage(Message message) {
		this.messages.remove(message);
		return true;
	}
}
