package com.ai.bot.web;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.bot.common.lucene.LuceneStore;
import com.ai.bot.common.util.DataUtil;
import com.ai.bot.web.server.JettyServer;

public class ServerStartUp {

	private static Logger logger = LogManager.getLogger(ServerStartUp.class.getName());

	private static JettyServer server;

	private static String serverproperty = "properties/server.properties";

	public static void main(String[] args) throws Exception {
		Properties serverproperties = DataUtil.loadProperty(serverproperty);

		ServerStartUp startUp = new ServerStartUp();
		startUp.initServer(serverproperties);
		LuceneStore.Initialization();

		server.start();
		
		logger.info("服务器启动完毕");
	}

	private void initServer(Properties properties) {
		logger.info("服务器启动");
		String port = properties.getProperty("port");
		server = new JettyServer(port);
	}
}
