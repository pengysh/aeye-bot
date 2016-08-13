package com.ai.bot.meetingscene.creator;

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
import com.ai.message.actor.Props;

public class MeetingSceneBotApi extends BotApiBase {

	private LuceneUtil luceneUtil = new LuceneUtil();

	public MeetingSceneBotApi(ActorContext context) {
		super(context);
	}

	private Logger logger = LogManager.getLogger(this.getClass());

	public ActorRef getCache(String accountId) {
		String[] stringQuery = { RegistedBot.MeetingSceneBot.toString(), accountId };
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
		String meetingSceneActorId = RegistedBot.MeetingSceneBot + accountId;
		MeetingSceneCreater creator = new MeetingSceneCreater(meetingSceneActorId, accountId, accountId);
		ActorRef meetingRoomSceneBot = getContext().actorOf(Props.create(creator, RegistedBot.MeetingSceneBot.toString()));
		logger.debug("创建会议室场景机器人：" + creator.getId() + "," + meetingRoomSceneBot.path());
		this.cache(accountId, meetingRoomSceneBot.path().getPath());
		return meetingRoomSceneBot;
	}

	private void cache(String accountId, String actorRefPath) {
		Document doc = new Document();
		doc.add(new TextField("botType", RegistedBot.MeetingSceneBot.toString(), Field.Store.YES));
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
