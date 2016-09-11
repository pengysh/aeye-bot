package com.a.eye.bot.common.message.robot;

import com.a.eye.bot.common.message.robot.tuling.TuringHttpClient;
import com.a.eye.bot.common.message.robot.tuling.TuringRequestBean;
import com.a.eye.bot.common.message.robot.tuling.TuringResponseBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

public class TuringRobot implements Robot {

    private Gson gson = new Gson();

    HashMap<String, String> contentMapReply;
    private static TuringRobot tulingRobot;

    private TuringRobot() {
        contentMapReply = new HashMap<String, String>();
        init();
    }

    private void init() {
        contentMapReply.put("您好", "您好！我是万国置地在线客服，请问有什么可以帮到您?");
    }

    public static TuringRobot getInstance() {
        if (tulingRobot == null) {
            synchronized (TuringRobot.class) {
                if (tulingRobot == null) {
                    tulingRobot = new TuringRobot();
                }
            }
        }
        return tulingRobot;
    }

    @Override
    public String getReply(Long userId, String content) throws IOException {
        String reply = contentMapReply.get(content);
        if (reply != null) {
            return reply;
        }
        TuringRequestBean turingBean = new TuringRequestBean("ae35e0925aa475708cd0f1892ac8c244", content, String.valueOf(userId));
        String jsonString = gson.toJson(turingBean);

        TuringHttpClient turingClient = new TuringHttpClient();
        TuringResponseBean turingResponseBean = gson.fromJson(turingClient.getResult(jsonString), TuringResponseBean.class);
        return turingResponseBean.getText();
    }


}
