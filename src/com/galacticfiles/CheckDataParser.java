package com.galacticfiles;

public class CheckDataParser implements ObjPartParser<CheckData> {

	private CheckData d;
	
	public CheckDataParser() {
		d = new CheckData();
	}
	
	@Override
	public void update(String k, String v) {
		if (k != null && v != null) {
			if ("status".equals(k)) {
				if ("ok".equals(v)) {
					d.setOk(true);
				}
			}
			if ("ip".equals(k)) {
				d.setIp(v);
			}
			if ("balance".equals(k)) {
				d.setBalance(v);
			}
		}
	}

	@Override
	public CheckData getObject() {
		return d;
	}

}
