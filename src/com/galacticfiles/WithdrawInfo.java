package com.galacticfiles;

/**
 * Information about transactions sent from the current GFSession account. 
 *
 */
public class WithdrawInfo {

	private String address;
	private String amount;
	private String fee;
	private long createdon;
	private long confirmations;

	/**
	 * The address funds were sent to from this session's account.
	 * @return the bitcoin address
	 */
	public String getAddress() {
		return address;
	}
	
	protected void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * The amount sent to the address.
	 * @return the amount sent
	 */
	public String getAmount() {
		return amount;
	}
	
	protected void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
	 * The fee the site charged for the withdraw.
	 * @return The site withdraw fee charged
	 */
	public String getFee() {
		return fee;
	}
	
	protected void setFee(String fee) {
		this.fee = fee;
	}
	
	/**
	 * The time in milliseconds since January 1, 1970, 00:00:00 GMT that
	 * the withdraw was created. 
	 * @return the time created
	 */
	public long getCreatedon() {
		return createdon;
	}
	
	protected void setCreatedon(long createdon) {
		this.createdon = createdon;
	}
	
	/**
	 * The number of confirmations for this transfer.
	 * @return number of confirmations
	 */
	public long getConfirmations() {
		return confirmations;
	}
	
	protected void setConfirmations(long confirmations) {
		this.confirmations = confirmations;
	}
	
}
