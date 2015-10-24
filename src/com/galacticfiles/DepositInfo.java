package com.galacticfiles;

/**
 * Information about payments made to addresses created for the account
 * used for this GFSession.
 *  
 */
public class DepositInfo {

	private String address;
	private String amount;
	private long createdon;
	private boolean accepted;
	private long confirmations;

	/**
	 * The address that the payment was sent to.
	 * @return bitcoin address sent to
	 */
	public String getAddress() {
		return address;
	}
	
	protected void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * The amount that was sent to the address.
	 * @return the amount sent
	 */
	public String getAmount() {
		return amount;
	}
	
	protected void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
	 * The time in milliseconds since January 1, 1970, 00:00:00 GMT that
	 * the payment was seen.
	 * @return time payment was seen
	 */
	public long getCreatedon() {
		return createdon;
	}
	
	protected void setCreatedon(long createdon) {
		this.createdon = createdon;
	}
	
	/**
	 * Returns true if the payment was accepted and added to the account balance.
	 * @return true if the payment was accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}
	
	protected void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	/**
	 * The number of confirmations for the payment.  A minimum of 6 is needed before
	 * a payment is accepted.
	 * @return number of confirmations
	 */
	public long getConfirmations() {
		return confirmations;
	}
	
	protected void setConfirmations(long confirmations) {
		this.confirmations = confirmations;
	}
	
	
}
