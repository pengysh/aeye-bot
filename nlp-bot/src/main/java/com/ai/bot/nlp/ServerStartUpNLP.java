package com.ai.bot.nlp;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.bot.nlp.server.JettyServer;
import com.ai.bot.nlp.util.DataUtil;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class ServerStartUpNLP {

	private static Logger logger = LogManager.getLogger(ServerStartUpNLP.class.getName());

	private static JettyServer server;

	private static final String serverproperty = "properties/server.properties";
	
	private static final String stanfordproperty = "properties/StanfordCoreNLP-chinese.properties";
	
	private static StanfordCoreNLP pipeline;

	public static void main(String[] args) {
		Properties serverproperties = DataUtil.loadProperty(serverproperty);
		Properties stanfordproperties = DataUtil.loadProperty(stanfordproperty);
		pipeline = new StanfordCoreNLP(stanfordproperties);
		
		ServerStartUpNLP startUp = new ServerStartUpNLP();
		startUp.initServer(serverproperties);

		server.start(pipeline);

		logger.info("服务器启动完毕");
	}

	private void initServer(Properties properties) {
		logger.info("服务器启动");
		String port = properties.getProperty("port");
		server = new JettyServer(port);
	}
}
