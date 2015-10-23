package com.galacticfiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ParseListResponse<T> implements ResponseParser<List<T>> {

	private ListPartParser<T> parser;
	
	public ParseListResponse(ListPartParser<T> p) {
		parser = p;
	}
	
	@Override
	public List<T> parse(BufferedReader br) throws IOException {
		List<T> r = new LinkedList<T>();
		String ln = br.readLine();
		while (ln != null) {
			T t = parser.parse(ln);
			if (t != null) {
				r.add(t);
			}
			ln = br.readLine();
		}
		return r;
	}

}
