package com.galacticfiles;

public interface ObjPartParser<T> {
	
	public void update(String k, String v);
	
	public T getObject();

}
