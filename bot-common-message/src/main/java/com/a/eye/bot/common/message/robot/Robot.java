package com.a.eye.bot.common.message.robot;


import java.io.IOException;

public interface Robot {
    String getReply(Long userId, String content) throws IOException;
}
