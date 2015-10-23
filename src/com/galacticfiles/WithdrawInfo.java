package com.galacticfiles;

public class WithdrawInfo {

	private String address;
	private String amount;
	private String fee;
	private long createdon;
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
	
	public String getFee() {
		return fee;
	}
	
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	public long getCreatedon() {
		return createdon;
	}
	
	public void setCreatedon(long createdon) {
		this.createdon = createdon;
	}
	
	public long getConfirmations() {
		return confirmations;
	}
	
	public void setConfirmations(long confirmations) {
		this.confirmations = confirmations;
	}
	
}
