package com.galacticfiles;

public class DownloadLinkListParser implements ListPartParser<DownloadLink> {

	@Override
	public DownloadLink parse(String ln) {
		if (ln != null) {
			DownloadLink d = new DownloadLink();
			d.setUrl(ln);
			return d;
		}
		return null;
	}

}
