package com.a.eye.bot.common.message;

import org.deeplearning4j.ui.UiServer;

/**
 * @author: pengysh
 * @date 2016/8/23 0023 下午 4:31
 * @Description
 */
public class UIStart {
    public static void main(String[] args) throws Exception {
        UiServer server = UiServer.getInstance();
        System.out.println("Started on port " + server.getPort());
    }
}
