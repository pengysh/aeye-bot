package com.a.eye.bot.nlp.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.eye.bot.nlp.service.CoreNLP;
import com.a.eye.bot.nlp.service.Pipeline;
import com.google.gson.JsonArray;

@Component
public class PartOfSpeechService {

	@Autowired
	private Pipeline pipeline;

	public String parseMessage(String sentence) {
		JsonArray treeJson = CoreNLP.parseMessage(pipeline.getPipeline(), sentence);
		return treeJson.toString();
	}
}
