package com.a.eye.bot.meetingscene.creator;

import com.a.eye.bot.common.base.BotApiBase;
import com.a.eye.bot.common.lucene.LuceneUtil;
import com.a.eye.bot.common.message.actor.ActorContext;
import com.a.eye.bot.common.message.actor.ActorRef;
import com.a.eye.bot.common.message.actor.Props;
import com.a.eye.bot.common.util.RegistedBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.BooleanClause.Occur;

import java.io.IOException;
import java.util.List;

public class MeetingSceneBotApi extends BotApiBase {

    private LuceneUtil luceneUtil = new LuceneUtil();

    public MeetingSceneBotApi(ActorContext context) {
        super(context);
    }

    private Logger logger = LogManager.getLogger(this.getClass());

    public ActorRef getCache(Long userId) {
        String[] stringQuery = {RegistedBot.MeetingSceneBot.toString(), String.valueOf(userId)};
        String[] fields = {"botType", "accountId"};
        Occur[] occ = {Occur.MUST, Occur.MUST};
        List<String> actorRefPathList = luceneUtil.searcher(stringQuery, fields, occ);

        if (actorRefPathList != null && actorRefPathList.size() == 1) {
            ActorRef actorRef = ActorContext.getInstance().getActorRef(actorRefPathList.get(0));
            return actorRef;
        }
        return null;
    }

    public ActorRef createBot(Long userId, String userName) {
        String meetingSceneActorId = RegistedBot.MeetingSceneBot + String.valueOf(userId);
        MeetingSceneCreater creator = new MeetingSceneCreater(meetingSceneActorId, userId, userName);
        ActorRef meetingRoomSceneBot = getContext().actorOf(Props.create(creator, RegistedBot.MeetingSceneBot.toString()));
        logger.debug("创建会议室场景机器人：" + creator.getId() + "," + meetingRoomSceneBot.path());
        this.cache(userId, meetingRoomSceneBot.path().getPath());
        return meetingRoomSceneBot;
    }

    private void cache(Long userId, String actorRefPath) {
        Document doc = new Document();
        doc.add(new TextField("botType", RegistedBot.MeetingSceneBot.toString(), Field.Store.YES));
        doc.add(new TextField("userId", String.valueOf(userId), Field.Store.YES));
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
