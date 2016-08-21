package com.a.eye.bot.nlp.machine.match.result;

import java.util.List;

public class NNResult extends SpeechResult {
	private List<SpeechResult> resultList;

	public List<SpeechResult> getResultList() {
		return resultList;
	}

	public void setResultList(List<SpeechResult> resultList) {
		this.resultList = resultList;
	}
}
