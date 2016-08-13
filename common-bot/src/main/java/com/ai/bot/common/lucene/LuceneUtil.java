package com.ai.bot.common.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneUtil {

	public List<String> searcher(String[] stringQuery, String[] fields, Occur[] occ) {
		List<String> resultList = new ArrayList<String>();

		int hitsPerPage = 10;
		IndexReader reader = null;

		ScoreDoc[] hits = null;
		try {
			Query query = MultiFieldQueryParser.parse(stringQuery, fields, occ, LuceneStore.getAnalyzer());
			reader = DirectoryReader.open(LuceneStore.getDirectory());
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs results = searcher.search(query, 5 * hitsPerPage);
			hits = results.scoreDocs;
			for (int i = 0; i < hits.length; ++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				System.out.println(d.get("botType") + "\t" + d.get("region") + "\t" + d.get("actorRefPath"));
				resultList.add(d.get("actorRefPath"));
			}
			reader.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public IndexWriter getIndexWriter() throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(LuceneStore.getAnalyzer());
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		config.setRAMBufferSizeMB(64.0);
		IndexWriter writer = new IndexWriter(LuceneStore.getDirectory(), config);
		return writer;
	}
}
