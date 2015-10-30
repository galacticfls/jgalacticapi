package com.galacticfiles;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

/**
 * A login session for an account.  All access to galacticfiles must
 * be done within the context of a session.  A new valid session is created
 * either when a new account is created, or after logging in with an
 * existing account.
 * 
 */
public class GFSession {
	
	private final Logger log = Logger.getLogger(GFSession.class.getName());

	private static String BASEURL = "https://galacticfiles.com";
	private static int MODE_PRIVATE = 0;
	private static int MODE_PUBLIC = 1;
	private static int MODE_DLPASS = 2;
	
	private String token;
	private boolean terms;
	
	/**
	 * To create a new account you need to set this true.
	 * Any access to galacticfiles.com indicates you have accepted the terms regardless
	 * of setting this to true or not.
	 * <p>
	 * Please see: galacticfiles.com/terms.html
	 * 
	 * @param t set to true to create new accounts
	 */
	public void acceptTerms(boolean t) {
		terms = t;
	}
	
	/**
	 * The login cookie for this session, or null if not logged in.
	 * @return the login cookie or null
	 */
	public String getToken() { 
		return token;
	}
	
	/**
	 * Create a new account.  Note, acceptTerms must have been called with "true"
	 * before this will work.
	 * <br>
	 * WARNING: For a given IP address new accounts may only be created
	 * about every 5 minutes.  Be sure to call check() after this to make
	 * sure your user id was successfully created.
	 * <br>
	 * @param userid A new user id.  It must not already exist.
	 * @param password A valid password.  Passwords must contain at least one symbol,
	 * an upper case letter, and a number.
	 * @see AccountGenerator
	 */
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
	
	/**
	 * Login with an existing account
	 * @param userid The username of the account
	 * @param password The password for the account
	 */
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
	
	/**
	 * Check the status of the current session.
	 * @return information about the account and session
	 */
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

	/**
	 * List all the files this account has uploaded.
	 * @return list of files
	 */
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
	
	/**
	 * Delete a file this account has uploaded.  Files may still be availble 
	 * from already generated download links for a while.
	 * @param fid The id of the file to delete
	 * @return true if successful
	 */
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
	
	/**
	 * Set a file to private mode.  Only the owner and accounts granted access
	 * may generate download links.
	 * @param id The id of the file to set to private mode
	 * @return true if successfully updated
	 */
	public boolean setPrivateFile(String id) {
		return updateFile(id, MODE_PRIVATE, null);
	}
	
	/**
	 * Set a file to public mode.  Any user will the file id will be able to
	 * generate download links and download the file.
	 * @param id The id of the file to set to public mode
	 * @return true if successfully updated
	 */
	public boolean setPublicFile(String id) {
		return updateFile(id, MODE_PUBLIC, null);
	}

	/**
	 * Set the file download password.  This puts the file in download password mode.
	 * Only users that know the download password will be able to generate download
	 * links and download the file.
	 * @param id The id of the file to set the download password for
	 * @param pass The donwload password
	 * @return true if successfully udpated
	 */
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
	
	/**
	 * Set the download link generation fee you would like to collect for a file.
	 * Once set this amount will be transfered from users' accounts that generate
	 * a download link to your account.
	 * @param id The id of the file to update the fee
	 * @param fee The new fee amount
	 * @return true if successfully updated
	 */
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
	
	/**
	 * Grant an account access to a private file.  This has no effect on files
	 * that are not private.
	 * @param id The id of the file to grant the user access to
	 * @param userid The user to grant access to the file
	 * @return true if successfully granted access
	 */
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
	
	/**
	 * Remove a user's access to a private file.  This has no effect on files
	 * that are not private.
	 * @param id The id of the file to remove the user's access
	 * @param userid The user to remove access from the file
	 * @return true if successfully removed the user's access
	 */
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
	
	/**
	 * Generate a new bitcoin address the user can send funds to add to 
	 * the account balance.  New addresses can only be generated every 
	 * 5 minutes.
	 * @return the bitcoin address or null if failed.
	 */
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
	
	/**
	 * List addresses generated for this account.  Bitcoin sent to
	 * any of these addresses will add funds to the account.
	 * @return list of addresses
	 */
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
	
	/**
	 * Send bitcoin from the account to a bitcoin address.  Funds are immediately 
	 * removed from the account balance.
	 * <p>
	 * NOTE: The site's withdraw fee is subtracted from the amount transfered.
	 * If you specify an amount of 0.0004.  The transfered amount will be
	 * 0.0004 - withdrawfee (0.0002), and 0.0004 will be deducted from the
	 * account balance.
	 * 
	 * @param addr The address to send funds to
	 * @param amnt The amount to send from the account
	 * @return true if successful
	 */
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
	
	/**
	 * List all transactions sent to payment addresses generated for
	 * this account.  Not all may be confirmed and added to the balance. 
	 * @return list of deposits 
	 */
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
	
	/**
	 * List all transactions sent from this account.
	 * @return list of withdraws
	 */
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
	
	/**
	 * Generate a download link for a file.  This file does not need to be owned 
	 * by this account, but if it is private this account must have been granted access.
	 * If the file has a download password it must be specified correctly.  The 
	 * download link can be generated for any IP address.  Invalid ip addresses
	 * or attempts to mask will cause the IP to be replaced by this connection's ip
	 * address.
	 * @param fileid The id of the file to generate a download link for
	 * @param pass The download password or null if there is not one
	 * @param ip The IP address that can download the file or null to use this connection's.
	 * @return the download link created or the fee if failed due to insufficient funds
	 */
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
	
	/**
	 * List all still valid download links generated by this account.
	 * @return list of download links that have not expired
	 */
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
	
	/**
	 * Upload a new public or private file with the fee specified.
	 * This will block until the whole file has been uploaded or there is a failure.
	 * @param f The file to upload
	 * @param pub True if the file should be public
	 * @param fee The fee you want to charge for download links
	 * @return The id of the file or null if upload failed
	 */
	public Long uploadFile(File f, boolean pub, String fee) {
		return uploadFile(f, pub ? "public" : "private", null, fee);
	}

	/**
	 * Upload a new file with a download password.
	 * This will block until the whole file has been uploaded or there is a failure.
	 * @param f The file to upload
	 * @param dlpass The password needed to generate new download links
	 * @param fee The fee you want to charge for download links
	 * @return The id of the file or null if upload failed
	 */
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
