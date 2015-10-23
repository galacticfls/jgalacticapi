package com.galacticfiles;

public class DepositInfoParser implements ListPartParser<DepositInfo> {

	@Override
	public DepositInfo parse(String ln) {
		String p[] = ln.split(":");
		if (p != null && p.length == 5) {
			DepositInfo d = new DepositInfo();
			d.setAddress(p[0]);
			d.setAmount(p[1]);
			d.setCreatedon(Long.valueOf(p[2]));
			d.setAccepted(p[3] == "1");
			d.setConfirmations(Long.valueOf(p[4]));
			return d;
		}
		return null;
	}

}
