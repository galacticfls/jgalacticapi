package com.galacticfiles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

public class ProcessRequest<T> {
	
	private final Logger log = Logger.getLogger(ProcessRequest.class.getName());

	private StringBuilder url;
	private ResponseParser<T> parser;
	private String token;
	private String setCookie;
	
	public ProcessRequest(ResponseParser<T> p, String t) {
		parser = p;
		token = t;
		url = new StringBuilder();
	}
	
	public void append(String a) {
		url.append(a);
	}
	
	public void appendUrl(String a) {
		try {
			url.append(URLEncoder.encode(a, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "failed to encode string", e);
		}
	}
	
	public String getCookie() {
		return setCookie;
	}
	
	public T process() {
		HttpsURLConnection.setFollowRedirects(false);
		HttpsURLConnection con = null;
		T t = null;
		try {
			URL u = new URL(url.toString());
			con = (HttpsURLConnection)u.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			if (token != null) {
				con.setRequestProperty("Cookie", token);				
			}
			con.connect();
			setCookie = con.getHeaderField("Set-Cookie");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			if (parser != null) {
				t = parser.parse(br);
			}
			else {
				String ln = br.readLine();
				while (ln != null) {
					System.out.println(ln);
					ln = br.readLine();
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "failed to process", e);
		}
		finally {
			if (con != null) {
				try {
					con.disconnect();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return t;
	}

}
