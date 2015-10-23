package com.galacticfiles;

import java.io.BufferedReader;
import java.io.IOException;

public class ParseObjResponse<T> implements ResponseParser<T> {

	private ObjPartParser<T> parser;
	
	public ParseObjResponse(ObjPartParser<T> p) {
		parser = p;
	}
	
	@Override
	public T parse(BufferedReader br) throws IOException {
		String ln = br.readLine();
		while (ln != null) {
			int splt = ln.indexOf(":");
			String k = ln.substring(0, splt);
			String v = ln.substring(splt+1);
			parser.update(k, v);
			ln = br.readLine();
		}
		return parser.getObject();
	}

}
