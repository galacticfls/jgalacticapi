package com.galacticfiles;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * @author www.codejava.net
 *
 */
public class MultipartUtility {
	private final String boundary;
	private static final String LINE_FEED = "\r\n";
	private HttpsURLConnection httpConn;
	private String charset;
	private OutputStream outputStream;
	private ByteArrayOutputStream bos;
	private PrintWriter writer;
	private byte [] linefeedbytes;
	private byte [] boundbytes;

	public MultipartUtility() {
		boundary = "===" + System.currentTimeMillis() + "===";
		bos = new ByteArrayOutputStream();
		writer = new PrintWriter(bos);
		
		ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bos2);
		pw.append(LINE_FEED);
		pw.flush();
		linefeedbytes = bos2.toByteArray();

		bos2 = new ByteArrayOutputStream();
		pw = new PrintWriter(bos2);
		pw.append("--" + boundary + "--").append(LINE_FEED);
		pw.flush();
		boundbytes = bos2.toByteArray();
	}
	
	/**
	 * This constructor initializes a new HTTP POST request with content type
	 * is set to multipart/form-data
	 * @param requestURL
	 * @param charset
	 * @throws IOException
	 */
	public void init(String requestURL, String cookies, String charset)
			throws IOException {
		this.charset = charset;

		URL url = new URL(requestURL);
		httpConn = (HttpsURLConnection) url.openConnection();
		httpConn.setUseCaches(false);
		httpConn.setDoOutput(true); // indicates POST method
		httpConn.setDoInput(true);
		httpConn.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);
		httpConn.setRequestProperty("Cookie", cookies);
	}

	/**
	 * Adds a form field to the request
	 * @param name field name
	 * @param value field value
	 */
	public void addFormField(String name, String value) {
		writer.append("--" + boundary).append(LINE_FEED);
		writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
		.append(LINE_FEED);
		writer.append("Content-Type: text/plain; charset=" + charset).append(
				LINE_FEED);
		writer.append(LINE_FEED);
		writer.append(value).append(LINE_FEED);
		writer.flush();
	}

	/**
	 * Adds a upload file section to the request
	 * @param fieldName name attribute in <input type="file" name="..." />
	 * @param uploadFile a File to be uploaded
	 * @throws IOException
	 */
	public List<String> sendData(String fieldName, File uploadFile)
			throws IOException {
		String fileName = uploadFile.getName();
		writer.append("--" + boundary).append(LINE_FEED);
		writer.append(
				"Content-Disposition: form-data; name=\"" + fieldName
				+ "\"; filename=\"" + fileName + "\"")
		.append(LINE_FEED);
		writer.append(
				"Content-Type: "
						+ URLConnection.guessContentTypeFromName(fileName))
		.append(LINE_FEED);
		writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
		writer.append(LINE_FEED);
		writer.flush();

		byte [] headers = bos.toByteArray();
		long len = uploadFile.length();
		len += headers.length;
		len += linefeedbytes.length;
		len += boundbytes.length;
		httpConn.setFixedLengthStreamingMode(len);
		//httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
		//httpConn.setRequestProperty("Test", "Bonjour");
		outputStream = httpConn.getOutputStream();
		outputStream.write(headers);
		
		FileInputStream inputStream = new FileInputStream(uploadFile);
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		outputStream.flush();
		inputStream.close();

		List<String> response = new ArrayList<String>();

		outputStream.write(linefeedbytes);
		outputStream.write(boundbytes);
		outputStream.close();

		// checks server's status code first
		int status = httpConn.getResponseCode();
		if (status == HttpsURLConnection.HTTP_OK || status == HttpsURLConnection.HTTP_MOVED_TEMP ||
				status == HttpsURLConnection.HTTP_MOVED_PERM) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				response.add(line);
			}
			reader.close();
			httpConn.disconnect();
		} else {
			throw new IOException("Server returned non-OK status: " + status);
		}

		return response;
	
	}

	/**
	 * Completes the request and receives response from the server.
	 * @return a list of Strings as response in case the server returned
	 * status OK, otherwise an exception is thrown.
	 * @throws IOException
	 */
	public List<String> finish() throws IOException {
		List<String> response = new ArrayList<String>();

		writer.append(LINE_FEED).flush();
		writer.append("--" + boundary + "--").append(LINE_FEED);
		writer.close();

		// checks server's status code first
		int status = httpConn.getResponseCode();
		if (status == HttpsURLConnection.HTTP_OK || status == HttpsURLConnection.HTTP_MOVED_TEMP ||
				status == HttpsURLConnection.HTTP_MOVED_PERM) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				response.add(line);
			}
			reader.close();
			httpConn.disconnect();
		} else {
			throw new IOException("Server returned non-OK status: " + status);
		}

		return response;
	}
}
