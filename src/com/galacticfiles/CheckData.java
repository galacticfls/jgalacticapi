package com.galacticfiles;

/**
 * Provides information about the account used for a GFSession
 *
 */
public class CheckData {
	
	private String ip;
	private String balance;
	private boolean ok;

	/**
	 * Returns the externally visible IP address for the session.
	 * Or, null if the account could not be created or logged into.
	 * @return the IP address or null
	 */
	public String getIp() {
		return ip;
	}
	
	protected void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Returns the bitcoin balance for this account
	 * Or, null if the account could not be created or logged into.
	 * @return bitcoin balance or null
	 */
	public String getBalance() {
		return balance;
	}
	
	protected void setBalance(String balance) {
		this.balance = balance;
	}
	
	/**
	 * Returns true if the account was succesfully created for logged into
	 * for this GFSession
	 * @return true if the GFSession can be used
	 */
	public boolean isOk() {
		return ok;
	}
	
	protected void setOk(boolean ok) {
		this.ok = ok;
	}

}
