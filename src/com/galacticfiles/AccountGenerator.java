package com.galacticfiles;

import java.security.SecureRandom;

/**
 * Used to generate new random usernames and passwords
 *
 */
public class AccountGenerator {
	
	private static String symbolbucket[] = {
			"!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",",
			"-", ".", "/", ":", ";", "<", "=", ">",	"?", "@",
			"[", "\\", "]", "^", "_", "`", "{", "|", "}", "`"
		};
	
	private static String numbucket[] = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
	};
	
	private static String letterbucket[] = {
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
			"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
	};
	
	private static String upbucket[] = {
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};
	
	private static SecureRandom random = new SecureRandom();
	
	private static int numFract(int f, int t, int sz) {
		float tf = (float)f * (float)sz;
		tf = tf / (float)t;
		return Math.max(1, (int)tf);
	}
	
	private static void roll(String v[]) {
		for (int i = 0; i < v.length; i++) {
			for (int c = 0; c < v.length; c++) {
				int d = random.nextInt(v.length);
				String t = v[c];
				v[c] = v[d];
				v[d] = t;
			}
		}
	}
	
	/**
	 * Generate a random username.  Not absolutely guaranteed to be available, but
	 * very likely.
	 * @return new username
	 */
	public static String getUsername() {
		StringBuilder sb = new StringBuilder();
		sb.append("GFAPI");
		sb.append(Math.abs(random.nextLong()));
		return sb.toString();
	}
	
	/**
	 * Generate a random password that will be accepted by the site's password
	 * rules.
	 * @return valid password
	 */
	public static String getPassword() {
		//Between 15 and 22 characters
		int size = random.nextInt(8) + 15;
		int symf = random.nextInt(symbolbucket.length);
		int numf = random.nextInt(numbucket.length);
		int letf = random.nextInt(letterbucket.length);
		int upf = random.nextInt(upbucket.length);
		int tot = symf + numf + letf + upf;
		int syms = numFract(symf, tot, size);
		int nums = numFract(numf, tot, size);
		int lets = numFract(letf, tot, size);
		int ups = numFract(upf, tot, size);
		String p[] = new String[syms+nums+lets+ups];
		int i = 0;
		for (int c = 0; c < syms; c++) {
			p[i] = symbolbucket[random.nextInt(symbolbucket.length)];
			i++;
		}
		for (int c = 0; c < nums; c++) {
			p[i] = numbucket[random.nextInt(numbucket.length)];
			i++;
		}
		for (int c = 0; c < lets; c++) {
			p[i] = letterbucket[random.nextInt(letterbucket.length)];
			i++;
		}
		for (int c = 0; c < ups; c++) {
			p[i] = upbucket[random.nextInt(upbucket.length)];
			i++;
		}
		roll(p);
		StringBuilder sb = new StringBuilder();
		for (int c = 0; c < p.length; c++) {
			sb.append(p[c]);
		}
		return sb.toString();
	}

}
