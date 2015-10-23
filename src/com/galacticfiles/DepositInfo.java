package com.galacticfiles;

public class DepositInfo {

	private String address;
	private String amount;
	private long createdon;
	private boolean accepted;
	private long confirmations;

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public long getCreatedon() {
		return createdon;
	}
	
	public void setCreatedon(long createdon) {
		this.createdon = createdon;
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	public long getConfirmations() {
		return confirmations;
	}
	
	public void setConfirmations(long confirmations) {
		this.confirmations = confirmations;
	}
	
	
}
