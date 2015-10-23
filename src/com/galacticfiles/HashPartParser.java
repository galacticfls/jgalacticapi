package com.galacticfiles;

import java.util.HashMap;
import java.util.Map;

public class HashPartParser implements ObjPartParser<Map<String,String>> {

	private Map<String,String> m;
	
	public HashPartParser() {
		m = new HashMap<String,String>();
	}
	
	@Override
	public void update(String k, String v) {
		if (k != null && v != null) {
			m.put(k, v);
		}
	}

	@Override
	public Map<String, String> getObject() {
		return m;
	}

}
