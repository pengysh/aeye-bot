package com.ai.bot.web.service;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonObject;

public class Test {

	public static void main(String[] args) {
		JsonObject ob = new JsonObject();
		ob.addProperty("test", "test");
		String str = ob.toString();
		System.out.println(str);
		String encode = new String(Base64.encodeBase64(str.getBytes()));
		System.out.println(encode);
		String decode = new String(Base64.decodeBase64(encode));
		System.out.println(decode);
	}
}
