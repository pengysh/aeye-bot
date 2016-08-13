package com.ai.bot.web.service.view;

abstract class ViewBase {
	
	public ViewBase(String contentId){
		this.contentId = contentId;
	}
	
	private String contentId;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
}
