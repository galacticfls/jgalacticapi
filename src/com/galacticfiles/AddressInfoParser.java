package com.galacticfiles;

public class AddressInfoParser implements ListPartParser<AddressInfo> {

	@Override
	public AddressInfo parse(String ln) {
		String p[] = ln.split(":");
		if (p != null && p.length == 3) {
			AddressInfo a = new AddressInfo();
			a.setAddress(p[0]);
			a.setCreatedon(Long.valueOf(p[1]));
			a.setTotalReceived(p[2]);
			return a;
		}
		return null;
	}

}
