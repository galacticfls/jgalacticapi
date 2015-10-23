package com.galacticfiles;

public class DownloadLinkObjParser implements ObjPartParser<DownloadLink> {

	private DownloadLink d;
	
	public DownloadLinkObjParser() {
		d = new DownloadLink();
	}
	
	@Override
	public void update(String k, String v) {
		if (k != null && v != null) {
			if ("url".equals(k)) {
				d.setUrl(v);
			}
			if ("fee".equals(k)) {
				d.setFee(v);
			}
		}
	}

	@Override
	public DownloadLink getObject() {
		return d;
	}

}
