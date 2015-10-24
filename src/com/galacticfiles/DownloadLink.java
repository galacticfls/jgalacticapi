package com.galacticfiles;

/**
 * A download link generated for a file, or the fee necessary to
 * generate a download link for a file. 
 *
 */
public class DownloadLink {
	
	private String url;
	private String fee;

	/**
	 * The download link, or null if one could not be created.
	 * @return download link or null
	 */
	public String getUrl() {
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}

	/**
	 * The amount required to generate a download link.  The account
	 * must have at least this much before a link can be generated.
	 * Or, null if the link was successfully generated.
	 * @return required fee or null
	 */
	public String getFee() {
		return fee;
	}

	protected void setFee(String fee) {
		this.fee = fee;
	}

}
