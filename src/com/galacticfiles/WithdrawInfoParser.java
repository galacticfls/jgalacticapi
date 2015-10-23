package com.galacticfiles;

public class WithdrawInfoParser implements ListPartParser<WithdrawInfo> {

	@Override
	public WithdrawInfo parse(String ln) {
		String p[] = ln.split(":");
		if (p != null && p.length == 5) {
			WithdrawInfo d = new WithdrawInfo();
			d.setAddress(p[0]);
			d.setAmount(p[1]);
			d.setFee(p[2]);
			d.setCreatedon(Long.valueOf(p[3]));
			d.setConfirmations(Long.valueOf(p[4]));
			return d;
		}
		return null;
	}
	

}
