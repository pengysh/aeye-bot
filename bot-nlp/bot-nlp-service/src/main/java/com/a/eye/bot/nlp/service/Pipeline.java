package com.a.eye.bot.nlp.service;

import java.util.Properties;

import org.springframework.stereotype.Component;

import com.a.eye.bot.common.util.PropertiesReadUtil;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

@Component
public class Pipeline {
	private static final String stanfordproperty = "properties/StanfordCoreNLP-chinese.properties";
	private static StanfordCoreNLP pipeline;

	{
		Properties stanfordproperties = PropertiesReadUtil.loadProperty(stanfordproperty);
		pipeline = new StanfordCoreNLP(stanfordproperties);
	}

	public StanfordCoreNLP getPipeline() {
		return pipeline;
	}
}
