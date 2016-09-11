package com.a.eye.bot.common.message.robot.tuling;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public class TuringHttpClient {

    private Logger logger = LogManager.getLogger(TuringHttpClient.class.getName());

    public String getResult(String turingJson) throws ClientProtocolException, IOException {
        String url = "http://www.tuling123.com/openapi/api";
        StringEntity s = new StringEntity(turingJson, "UTF-8");   // 中文乱码在此解决
        s.setContentType("application/json");

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(s);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();

        StringBuilder sb = new StringBuilder();
        if (httpEntity != null) {
            try {
                Scanner sc = new Scanner(httpEntity.getContent());
                String line = null;
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    sb.append(line + "/n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.debug("图灵返回数据：" + sb.toString());
        return sb.toString();
    }
}
