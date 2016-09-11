package com.a.eye.bot.meetingroom.creator;

import com.a.eye.bot.common.base.BotApiBase;
import com.a.eye.bot.common.lucene.LuceneUtil;
import com.a.eye.bot.common.message.actor.ActorContext;
import com.a.eye.bot.common.message.actor.ActorRef;
import com.a.eye.bot.common.message.actor.ActorSystem;
import com.a.eye.bot.common.message.actor.Props;
import com.a.eye.bot.common.util.BooleanUtil;
import com.a.eye.bot.common.util.RegistedBot;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.a.eye.bot.interfaces.share.entity.MeetingRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.BooleanClause.Occur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MeetingRoomBotApi extends BotApiBase {

    private Logger logger = LogManager.getLogger(this.getClass());

    private MeetingRoomServiceManager meetingRoomServiceManager = SpringContextUtil.getBean(MeetingRoomServiceManager.class);

    private LuceneUtil luceneUtil = new LuceneUtil();

    public MeetingRoomBotApi(ActorContext context) {
        super(context);
    }

    public List<ActorRef> getCache(String region) {
        String[] stringQuery = {RegistedBot.MeetingRoomBot.toString().toLowerCase(), region};
        String[] fields = {"botType", "region"};
        Occur[] occ = {Occur.MUST, Occur.MUST};
        List<String> actorRefPathList = luceneUtil.searcher(stringQuery, fields, occ);

        List<ActorRef> refList = new ArrayList<ActorRef>();
        for (String path : actorRefPathList) {
            refList.add(ActorContext.getInstance().getActorRef(path));
        }
        return refList;
    }

    public void initBots() {
        List<MeetingRoom> roomList = meetingRoomServiceManager.getMeetingRoomTime();
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
