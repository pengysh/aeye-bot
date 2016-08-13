package com.ai.bot.web;

import com.ai.bot.web.httpclient.HttpClientUtil;

public class HttpClientTest {

	public static void main(String[] args) {
		String responseContent = HttpClientUtil.getInstance().sendHttpGet("http://localhost:9999/wordTree?question=我要订明天上午8点到后天下午9点的8个人的投影仪会议室");
		System.out.println("reponse content:" + responseContent);
	}

}
