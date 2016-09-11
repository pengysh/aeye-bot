package com.a.eye.bot.common.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class LuceneStore {

	private static Directory directory;

	private static Analyzer analyzer;

	public static void Initialization() throws Exception {
		directory = new RAMDirectory();
		analyzer = new StandardAnalyzer();
	}

	public static Directory getDirectory() {
		return directory;
	}

	public static Analyzer getAnalyzer() {
		return analyzer;
	}
}
