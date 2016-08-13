package com.ai.bot.web.websocket.cmd;

public class CmdBase {
	private String cmd;
	
	private String myAccountId;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getMyAccountId() {
		return myAccountId;
	}

	public void setMyAccountId(String myAccountId) {
		this.myAccountId = myAccountId;
	}
}
