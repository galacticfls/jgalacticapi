package com.galacticfiles;

public class FileInfo {

	/*
	       	    pw.print(sf.getId());
    	    	pw.print(":");
    	    	pw.print(sf.getCreatedon());
    	    	pw.print(":");
    	    	pw.print(sf.getSize());
    	    	pw.print(":");
    	    	pw.print(sf.getLastdownload());
    	    	pw.print(":");
    	    	pw.print(sf.getLinksgenerated());
    	    	pw.print(":");
    	    	pw.print(sf.getMode());
    	    	pw.print(":");
    	    	pw.println(sf.getFilename());
	 */
	
	private long id;
	private long createdon;
	private long size;
	private long lastdownload;
	private long linksgenerated;
	private int mode;
	private String filename;
	private String totalEarnings;
	private String fee;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCreatedon() {
		return createdon;
	}
	
	public void setCreatedon(long createdon) {
		this.createdon = createdon;
	}
	
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getLastdownload() {
		return lastdownload;
	}

	public void setLastdownload(long lastdownload) {
		this.lastdownload = lastdownload;
	}

	public long getLinksgenerated() {
		return linksgenerated;
	}

	public void setLinksgenerated(long linksgenerated) {
		this.linksgenerated = linksgenerated;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(String totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
	
}
