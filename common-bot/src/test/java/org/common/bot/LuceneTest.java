package org.common.bot;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.BooleanClause.Occur;

import com.ai.bot.common.lucene.LuceneStore;
import com.ai.bot.common.lucene.LuceneUtil;
import com.ai.bot.common.util.RegistedBot;

public class LuceneTest {

	private LuceneUtil luceneUtil = new LuceneUtil();

	public static void main(String[] args) throws Exception {
		LuceneStore.Initialization();

		LuceneTest test = new LuceneTest();
		test.cache("beijing", "1");
		test.cache("beijing", "2");
		test.cache("beijing", "3");
		test.cache("beijing", "4");
		test.cache("nanjing", "5");

		List<String> list = test.getCache("beijing");

		System.out.println(list.size());
	}

	public List<String> getCache(String region) {
		String[] stringQuery = { RegistedBot.MeetingRoomBot.toString(), region };
		String[] fields = { "botType", "region" };
		Occur[] occ = new Occur[] { Occur.MUST, Occur.MUST };
		List<String> actorRefPathList = luceneUtil.searcher(stringQuery, fields, occ);

		return actorRefPathList;
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
