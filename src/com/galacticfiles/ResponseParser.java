package com.galacticfiles;

import java.io.BufferedReader;
import java.io.IOException;

public interface ResponseParser<T> {

	public T parse(BufferedReader br) throws IOException;
	
}
