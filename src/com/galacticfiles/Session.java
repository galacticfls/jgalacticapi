package com.galacticfiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

public class Session {
	
	private final Logger log = Logger.getLogger(Session.class.getName());
	
	private static String URLBASE = "https://galacticfiles.com";
	private static String URLLOGIN = "/Login";
	private static String URLCREATE = "/CreateUser";
	private static String URLUPLOAD = "/Upload";
	private static String URLCHECK = "/Check";
	
	private static String USERNAME = "username";
	private static String PASSWORD = "password";
	private static String PASSCONFIRM = "confirm";
	private static String TERMS = "terms";
	private static String FILEMODE = "mode";
	private static String FILEFEE = "fee";
	private static String FILEDLPASS = "dlpass";
	private static String FILE = "file";
	private static String APIMODE = "api";
	private static String STATUS = "status";
	private static String IP = "ip";
	private static String BALANCE = "balance";
	
	private static String MODEPUBLIC = "public";
	private static String MODEPRIVATE = "private";
	private static String MODEPASS = "pass";
	

	private String username;
	private String password;
	private long uploadFileId;
	
	private String loginToken;
	
	private String ip;
	private String balance;
	
	private boolean terms = false;
	
	public int CREATEUSERFAILED = 0x8000;
	public int LOGINFAILED      = 0x4000;
	public int UPLOADFAILED     = 0x2000;
	public int OK               = 0x0;
	private int error = OK;
	
	/**
	 * You must set this to true indicating that you agree to the
	 * terms outlined at: https://galacticfiles.com/terms.html
	 * @param t
	 */
	public void agreeToTerms(boolean t) {
		terms = t;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAuthenticated() {
		return loginToken != null;
	}
	
	public int getErrors() {
		return error;
	}
	
	public boolean isReady() {
		return getErrors() == OK && loginToken != null;
	}
	
	public long getUploadFileId() {
		return uploadFileId;
	}
	
	public String getIp() {
		return ip;
	}
	
	public String getBalance() {
		return balance;
	}
	
	public void resetErrors() {
		error = OK;
	}
	
	private HttpsURLConnection getConnection(String nu) throws IOException {
		HttpsURLConnection.setFollowRedirects(false);
		StringBuilder sb = new StringBuilder();
		sb.append(URLBASE);
		sb.append(URLCHECK);
		URL url = new URL(sb.toString());
		HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
		con.setUseCaches(false);
		con.setDoOutput(true); // indicates POST method
		con.setDoInput(true);
		con.setRequestProperty("Cookie", loginToken);
		return con;
	}
	
	public void check() {
		ip = null;
		balance = null;
		HttpsURLConnection con = null;
		try {
			con = getConnection(URLCHECK);
			con.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String l = br.readLine();
			while (l != null) {
				String sl[] = l.split(":");
				if (sl != null && sl.length == 2) {
					String k = sl[0];
					String v = sl[1];
					if (STATUS.equals(k)) {
						if (!"ok".equals(v)) {
							error = error | LOGINFAILED;
						}
					}
					if (IP.equals(k)) {
						ip = v;
					}
					if (BALANCE.equals(k)) {
						balance = v;
					}
				}
				l = br.readLine();
			}
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Login failed.", e);
			error = error | LOGINFAILED;
		}
		finally {
			if (con != null) {
				try {
					con.disconnect();
				}
				catch (Exception e) {
				}
			}
		}
	}
	
	public void createUser() {
		loginToken = null;
		HttpsURLConnection.setFollowRedirects(false);
		HttpsURLConnection con = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(URLBASE);
			sb.append(URLCREATE);
			sb.append("?");
			sb.append(USERNAME);
			sb.append("=");
			sb.append(URLEncoder.encode(username, "UTF-8"));
			sb.append("&");
			sb.append(PASSWORD);
			sb.append("=");
			sb.append(URLEncoder.encode(password, "UTF-8"));
			sb.append("&");
			sb.append(PASSCONFIRM);
			sb.append("=");
			sb.append(URLEncoder.encode(password, "UTF-8"));
			sb.append("&");
			sb.append(TERMS);
			sb.append("=");
			sb.append(terms ? "agree" : "nope");
			URL url = new URL(sb.toString());
			con = (HttpsURLConnection)url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			loginToken = con.getHeaderField("Set-Cookie");
			log.info("Create cookie: " + loginToken);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Create user failed.", e);
			error = error | CREATEUSERFAILED;
		}
		finally {
			if (con != null) {
				try {
					con.disconnect();
				}
				catch (Exception e) {
				}
			}
		}
	}
	
	public void login() {
		loginToken = null;
		HttpsURLConnection.setFollowRedirects(false);
		HttpsURLConnection con = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(URLBASE);
			sb.append(URLLOGIN);
			sb.append("?");
			sb.append(USERNAME);
			sb.append("=");
			sb.append(URLEncoder.encode(username, "UTF-8"));
			sb.append("&");
			sb.append(PASSWORD);
			sb.append("=");
			sb.append(URLEncoder.encode(password, "UTF-8"));
			URL url = new URL(sb.toString());
			con = (HttpsURLConnection)url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			loginToken = con.getHeaderField("Set-Cookie");
			log.info("Login cookie: " + loginToken);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Login failed.", e);
			error = error | LOGINFAILED;
		}
		finally {
			if (con != null) {
				try {
					con.disconnect();
				}
				catch (Exception e) {
				}
			}
		}
	}
	
	public void uploadFile(File f, boolean pub, String fee) {
		uploadFile(f, pub ? MODEPUBLIC : MODEPRIVATE, null, fee);
	}
	
	public void uploadFile(File f, String dlpass, String fee) {
		uploadFile(f, MODEPASS, dlpass, fee);
	}

	private void uploadFile(File f, String mode, String password, String fee) {
		check();
		if (!isReady()) {
			error = error | UPLOADFAILED;
			return;
		}
		uploadFileId = 0;
		HttpsURLConnection.setFollowRedirects(false);
		try {
			MultipartUtility mu = new MultipartUtility();
			mu.addFormField(FILEMODE, mode);
			if (password != null) {
				mu.addFormField(FILEDLPASS, password);
			}
			mu.addFormField(FILEFEE, fee);
			mu.addFormField(APIMODE, "1");
			mu.init(URLBASE + URLUPLOAD, loginToken, "UTF-8");
			List<String> l = mu.sendData(FILE, f);
			for (String s : l) {
				if (s.matches("\\d+")) {
					uploadFileId = Long.valueOf(s);
				}
				if (s.matches("ERROR")) {
					error = error | UPLOADFAILED;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Upload failed.", e);
			error = error | UPLOADFAILED;
		}
	}
	
	//TODO: List files.
	//         - File name, earned, links made, etc.
	//         - List download links
	//TODO: Delete file
	//TODO: Update file - private, password, etc.
	
	//TODO: Get Account info
	//         - List balance.
	//         - List payment addresses
	//         - List deposits
	//         - List withdraws
	//TODO: Generate payment address
	//TODO: Withdraw
	
	//TODO: Generate download link

}
