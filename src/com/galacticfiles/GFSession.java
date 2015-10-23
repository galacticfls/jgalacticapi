package com.galacticfiles;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

public class GFSession {
	
	private final Logger log = Logger.getLogger(GFSession.class.getName());

	private static String BASEURL = "https://galacticfiles.com";
	private static int MODE_PRIVATE = 0;
	private static int MODE_PUBLIC = 1;
	private static int MODE_DLPASS = 2;
	
	private String token;
	private boolean terms;
	
	public void acceptTerms(boolean t) {
		terms = t;
	}
	
	public String getToken() { 
		return token;
	}
	
	public void createUser(String userid, String password) {
		token = null;
		ProcessRequest<String> p = new ProcessRequest<String>(null,	null);
		p.append(BASEURL);
		p.append("/CreateUser?username=");
		p.appendUrl(userid);
		p.append("&password=");
		p.appendUrl(password);
		p.append("&confirm=");
		p.appendUrl(password);
		p.append("&terms=");
		p.append(terms ? "agree" : "nope");		
		p.process();
		token = p.getCookie();
	}
	
	public void login(String userid, String password) {
		token = null;
		ProcessRequest<String> p = new ProcessRequest<String>(null,	null);
		p.append(BASEURL);
		p.append("/Login?username=");
		p.appendUrl(userid);
		p.append("&password=");
		p.appendUrl(password);
		p.process();
		token = p.getCookie();
	}
	
	public CheckData check() {
		ProcessRequest<CheckData> p = 
				new ProcessRequest<CheckData>(
						new ParseObjResponse<CheckData>(
								new CheckDataParser()), token);
		p.append(BASEURL);
		p.append("/Check");
		CheckData d = p.process();
		return d;
	}

	public List<FileInfo> listFiles() {
		ProcessRequest<List<FileInfo>> p = 
				new ProcessRequest<List<FileInfo>>(
						new ParseListResponse<FileInfo>(
								new FileInfoParser()), token);
		p.append(BASEURL);
		p.append("/ListFiles");
		List<FileInfo> l = p.process();
		return l;
	}
	
	public boolean deleteFile(String fid) {
		ProcessRequest<Map<String,String>> p = 
				new ProcessRequest<Map<String,String>>(
						new ParseObjResponse<Map<String,String>>(
								new HashPartParser()), token);
		p.append(BASEURL);
		p.append("/FileSetDelete");
		p.append("?fileid=");
		p.append(fid);
		p.append("&api=1");
		Map<String,String> m = p.process();
		return "ok".equals(m.get("status"));
	}
	
	public boolean setPrivateFile(String id) {
		return updateFile(id, MODE_PRIVATE, null);
	}
	
	public boolean setPublicFile(String id) {
		return updateFile(id, MODE_PUBLIC, null);
	}
	
	public boolean setDownloadpass(String id, String pass) {
		return updateFile(id, MODE_DLPASS, pass);
	}

	private boolean updateFile(String id, int mode, String dlpass) {
		ProcessRequest<Map<String,String>> p = 
				new ProcessRequest<Map<String,String>>(
						new ParseObjResponse<Map<String,String>>(
								new HashPartParser()), token);
		p.append(BASEURL);
		p.append("/FileSetPublic");
		p.append("?fileid=");
		p.append(id);
		p.append("&pub=");
		p.append(Integer.toString(mode));
		if (dlpass != null) {
			p.append("&dlpass=");
			p.appendUrl(dlpass);
		}
		p.append("&api=1");
		Map<String,String> m = p.process();
		return "ok".equals(m.get("status"));
	}
	
	public boolean setFileFee(String id, String fee) {
		ProcessRequest<Map<String,String>> p = 
				new ProcessRequest<Map<String,String>>(
						new ParseObjResponse<Map<String,String>>(
								new HashPartParser()), token);
		p.append(BASEURL);
		p.append("/FileSetFee");
		p.append("?fileid=");
		p.append(id);
		p.append("&fee=");
		p.append(fee);
		p.append("&api=1");
		Map<String,String> m = p.process();
		return "ok".equals(m.get("status"));
	}
	
	public boolean addFileGrant(String id, String userid) {
		ProcessRequest<Map<String,String>> p = 
				new ProcessRequest<Map<String,String>>(
						new ParseObjResponse<Map<String,String>>(
								new HashPartParser()), token);
		p.append(BASEURL);
		p.append("/GrantFile");
		p.append("?fileid=");
		p.append(id);
		p.append("&userid=");
		p.append(userid);
		p.append("&api=1");
		Map<String,String> m = p.process();
		return "ok".equals(m.get("status"));
	}
	
	public boolean deleteFileGrant(String id, String userid) {
		ProcessRequest<Map<String,String>> p = 
				new ProcessRequest<Map<String,String>>(
						new ParseObjResponse<Map<String,String>>(
								new HashPartParser()), token);
		p.append(BASEURL);
		p.append("/DeleteGrant");
		p.append("?fileid=");
		p.append(id);
		p.append("&userid=");
		p.append(userid);
		p.append("&api=1");
		Map<String,String> m = p.process();
		return "ok".equals(m.get("status"));
	}
	
	public String genAddress() {
		ProcessRequest<Map<String,String>> p = 
				new ProcessRequest<Map<String,String>>(
						new ParseObjResponse<Map<String,String>>(
								new HashPartParser()), token);
		p.append(BASEURL);
		p.append("/CreateAddress");
		p.append("?api=1");
		Map<String,String> m = p.process();
		return m.get("addr");
	}
	
	public List<AddressInfo> listAddresses() {
		ProcessRequest<List<AddressInfo>> p = 
				new ProcessRequest<List<AddressInfo>>(
						new ParseListResponse<AddressInfo>(
								new AddressInfoParser()), token);
		p.append(BASEURL);
		p.append("/ListAddresses");
		List<AddressInfo> l = p.process();
		return l;		
	}
	
	public boolean withdraw(String addr, String amnt) {
		ProcessRequest<Map<String,String>> p = 
				new ProcessRequest<Map<String,String>>(
						new ParseObjResponse<Map<String,String>>(
								new HashPartParser()), token);
		p.append(BASEURL);
		p.append("/Withdraw");
		p.append("?addr=");
		p.append(addr);
		p.append("&amnt=");
		p.append(amnt);
		p.append("&api=1");
		Map<String,String> m = p.process();
		return "ok".equals(m.get("status"));
	}
	
	public List<DepositInfo> listDeposits() {
		ProcessRequest<List<DepositInfo>> p = 
				new ProcessRequest<List<DepositInfo>>(
						new ParseListResponse<DepositInfo>(
								new DepositInfoParser()), token);
		p.append(BASEURL);
		p.append("/ListDeposits");
		List<DepositInfo> l = p.process();
		return l;
	}
	
	public List<WithdrawInfo> listWithdraws() {
		ProcessRequest<List<WithdrawInfo>> p = 
				new ProcessRequest<List<WithdrawInfo>>(
						new ParseListResponse<WithdrawInfo>(
								new WithdrawInfoParser()), token);
		p.append(BASEURL);
		p.append("/ListWithdraws");
		List<WithdrawInfo> l = p.process();
		return l;
	}
	
	public DownloadLink genDownloadLink(String fileid, String pass, String ip) {
		ProcessRequest<DownloadLink> p = 
				new ProcessRequest<DownloadLink>(
						new ParseObjResponse<DownloadLink>(
								new DownloadLinkObjParser()), token);
		p.append(BASEURL);
		p.append("/GenLink");
		p.append("?id=");
		p.append(fileid);
		if (pass != null) {
			p.append("&dlpass=");
			p.appendUrl(pass);
		}
		if (ip != null) {
			p.append("&ip=");
			p.appendUrl(ip);
		}
		p.append("&force=1&api=1");
		DownloadLink d = p.process();
		return d;
	}
	
	public List<DownloadLink> listDownloadLinks() {
		ProcessRequest<List<DownloadLink>> p = 
				new ProcessRequest<List<DownloadLink>>(
						new ParseListResponse<DownloadLink>(
								new DownloadLinkListParser()), token);
		p.append(BASEURL);
		p.append("/ListLinks");
		List<DownloadLink> l = p.process();
		return l;
	}
	
	public Long uploadFile(File f, boolean pub, String fee) {
		return uploadFile(f, pub ? "public" : "private", null, fee);
	}
	
	public Long uploadFile(File f, String dlpass, String fee) {
		return uploadFile(f, "pass", dlpass, fee);
	}

	private Long uploadFile(File f, String mode, String password, String fee) {
		Long fid = null;
		HttpsURLConnection.setFollowRedirects(false);
		CheckData cd = check();
		if (cd.isOk()) {
			try {
				MultipartUtility mu = new MultipartUtility();
				mu.addFormField("mode", mode);
				if (password != null) {
					mu.addFormField("dlpass", password);
				}
				mu.addFormField("fee", fee);
				mu.addFormField("api", "1");
				mu.init(BASEURL + "/Upload", token, "UTF-8");
				List<String> l = mu.sendData("file", f);
				for (String s : l) {
					if (s.matches("\\d+")) {
						fid = Long.valueOf(s);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.log(Level.SEVERE, "Upload failed.", e);
			}
		}
		return fid;
	}
	
}
