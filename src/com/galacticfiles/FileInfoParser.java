package com.galacticfiles;

public class FileInfoParser implements ListPartParser<FileInfo> {

	@Override
	public FileInfo parse(String ln) {
		String p[] = ln.split(":");
		if (p != null && p.length == 9) {
			FileInfo f = new FileInfo();
			f.setId(Long.valueOf(p[0]));
			f.setCreatedon(Long.valueOf(p[1]));
			f.setSize(Long.valueOf(p[2]));
			f.setLastdownload(Long.valueOf(p[3]));
			f.setLinksgenerated(Long.valueOf(p[4]));
			f.setMode(Integer.valueOf(p[5]));
			f.setFee(p[6]);
			f.setTotalEarnings(p[7]);
			f.setFilename(p[8]);
			return f;
		}
		return null;
	}

}
