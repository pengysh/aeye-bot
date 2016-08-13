package com.ai.bot.web.bridgebot.creator;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.BooleanClause.Occur;

import com.ai.bot.common.base.BotApiBase;
import com.ai.bot.common.lucene.LuceneUtil;
import com.ai.bot.common.util.RegistedBot;
import com.ai.message.actor.ActorContext;
import com.ai.message.actor.ActorRef;
import com.ai.message.actor.ActorSystem;
import com.ai.message.actor.Props;

public class BridgeBotApi extends BotApiBase {

	private Logger logger = LogManager.getLogger(this.getClass());

	private LuceneUtil luceneUtil = new LuceneUtil();

	public BridgeBotApi() {
		super(null);
	}

	public ActorRef getCache(String accountId) {
		String[] stringQuery = { RegistedBot.Bridge.toString(), accountId };
		String[] fields = { "botType", "accountId" };
		Occur[] occ = { Occur.MUST, Occur.MUST };
		List<String> actorRefPathList = luceneUtil.searcher(stringQuery, fields, occ);

		if (actorRefPathList != null && actorRefPathList.size() == 1) {
			ActorRef actorRef = ActorContext.getInstance().getActorRef(actorRefPathList.get(0));
			return actorRef;
		}
		return null;
	}

	public ActorRef createBot(String accountId) {
		BridgeBotCreater bridgeBotCreater = new BridgeBotCreater(accountId);
		String bridgeActorId = RegistedBot.Bridge + bridgeBotCreater.getId();

		ActorRef bridgeBot = ActorSystem.getContext().actorOf(Props.create(bridgeBotCreater, RegistedBot.Bridge.toString()));
		logger.debug("创建桥接机器人：" + bridgeActorId);
		this.cache(accountId, bridgeBot.path().getPath());
		return bridgeBot;
	}

	private void cache(String accountId, String actorRefPath) {
		Document doc = new Document();
		doc.add(new TextField("botType", RegistedBot.Bridge.toString(), Field.Store.YES));
		doc.add(new TextField("accountId", accountId, Field.Store.YES));
		doc.add(new TextField("actorRefPath", actorRefPath, Field.Store.YES));
		IndexWriter writer;
		try {
			writer = luceneUtil.getIndexWriter();
			writer.addDocument(doc);
			writer.commit();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
