package com.galacticfiles;

/**
 * Information about a file uploaded by the account used for the GFSession
 *
 */
public class FileInfo {
	
	private long id;
	private long createdon;
	private long size;
	private long lastdownload;
	private long linksgenerated;
	private int mode;
	private String filename;
	private String totalEarnings;
	private String fee;
	
	/**
	 * The ID of the file.  This is the ID required to update, delete, or
	 * generate download links for the file.
	 * @return the id of the file
	 */
	public long getId() {
		return id;
	}
	
	protected void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Time time in milliseconds since January 1, 1970, 00:00:00 GMT that
	 * the file was uploaded.
	 * @return time file was uploaded
	 */
	public long getCreatedon() {
		return createdon;
	}
	
	protected void setCreatedon(long createdon) {
		this.createdon = createdon;
	}
	
	/**
	 * The size in bytes of the file
	 * @return size in bytes
	 */
	public long getSize() {
		return size;
	}

	protected void setSize(long size) {
		this.size = size;
	}

	/**
	 * The last time in milliseconds since January 1, 1970, 00:00:00 GMT that
	 * a download link was generated for this file.
	 * @return last time a download link was generated
	 */
	public long getLastdownload() {
		return lastdownload;
	}

	protected void setLastdownload(long lastdownload) {
		this.lastdownload = lastdownload;
	}

	/**
	 * The number of download links that have been generated for this file.
	 * @return number of download links
	 */
	public long getLinksgenerated() {
		return linksgenerated;
	}

	protected void setLinksgenerated(long linksgenerated) {
		this.linksgenerated = linksgenerated;
	}

	/**
	 * The mode of the file.
	 * <p>
	 * 0: Private file - private files are only accessible by the file owner
	 * and accounts that have been granted access to the file
	 * <p>
	 * 1: Public file - public files are accessible by anyone
	 * <p>
	 * 2: Download password - download links may only be generated if someone knows
	 * the download password. 
	 * @return 0: private, 1: public, 2: download password
	 */
	public int getMode() {
		return mode;
	}

	protected void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * The name of the file.
	 * @return file name
	 */
	public String getFilename() {
		return filename;
	}

	protected void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * The amount earned by this file from download links generated.
	 * @return amount earned
	 */
	public String getTotalEarnings() {
		return totalEarnings;
	}

	protected void setTotalEarnings(String totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	/**
	 * The download link generation fee.
	 * @return the link generation fee
	 */
	public String getFee() {
		return fee;
	}

	protected void setFee(String fee) {
		this.fee = fee;
	}
	
}
