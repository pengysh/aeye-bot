package com.ai.bot.meetingroom.creator;

import java.io.IOException;
import java.util.ArrayList;
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
import com.ai.bot.common.util.BooleanUtil;
import com.ai.bot.common.util.RegistedBot;
import com.ai.bot.common.util.SpringContextUtil;
import com.ai.message.actor.ActorContext;
import com.ai.message.actor.ActorRef;
import com.ai.message.actor.ActorSystem;
import com.ai.message.actor.Props;
import com.ai.system.interfaces.entity.MeetingRoom;
import com.ai.system.interfaces.logic.MeetingRoomLogic;

public class MeetingRoomBotApi extends BotApiBase {

	private Logger logger = LogManager.getLogger(this.getClass());

	private MeetingRoomLogic logic = SpringContextUtil.getBean(MeetingRoomLogic.class);

	private LuceneUtil luceneUtil = new LuceneUtil();

	public MeetingRoomBotApi(ActorContext context) {
		super(context);
	}

	public List<ActorRef> getCache(String region) {
		String[] stringQuery = { RegistedBot.MeetingRoomBot.toString().toLowerCase(), region };
		String[] fields = { "botType", "region" };
		Occur[] occ = { Occur.MUST, Occur.MUST };
		List<String> actorRefPathList = luceneUtil.searcher(stringQuery, fields, occ);

		List<ActorRef> refList = new ArrayList<ActorRef>();
		for (String path : actorRefPathList) {
			refList.add(ActorContext.getInstance().getActorRef(path));
		}
		return refList;
	}

	public void initBots() {
		List<MeetingRoom> roomList = logic.getMeetingRoomTime();
		for (MeetingRoom meetingRoom : roomList) {
			MeetingRoomCreater creator = new MeetingRoomCreater(String.valueOf(meetingRoom.getId()), meetingRoom.getName(), Integer.valueOf(meetingRoom.getCapacity()),
					BooleanUtil.getBoolean(meetingRoom.getHasprojector()));
			ActorRef meetingRoomBot = ActorSystem.getContext().actorOf(Props.create(creator, RegistedBot.MeetingRoomBot.toString()));
			logger.debug("创建会议室机器人：" + meetingRoom.getName() + "\t" + meetingRoomBot.path() + "\t" + creator.getCapacity() + "\t" + creator.isHasProjector());
			this.cache(meetingRoom.getRegion(), meetingRoomBot.path().getPath());
		}
	}

	private void cache(String region, String actorRefPath) {
		Document doc = new Document();
		doc.add(new TextField("botType", RegistedBot.MeetingRoomBot.toString(), Field.Store.YES));
		doc.add(new TextField("region", region, Field.Store.YES));
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
