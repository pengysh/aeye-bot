package com.a.eye.bot.nlp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseTreebankLanguagePack;
import edu.stanford.nlp.util.CoreMap;

public class CoreNLP {

	private static Logger logger = LogManager.getLogger(CoreNLP.class.getName());

	private static final String ROOT = "ROOT";
	private static final String Value = "Value";
	private static final String Word = "Word";
	private static final String NodeId = "NodeId";
	private static final String PartOfSpeech = "PartOfSpeech";
	private static final String SubNodes = "SubNodes";

	public static JsonArray parseMessage(StanfordCoreNLP pipeline, String message) {
		Annotation document = new Annotation(message);
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			// 获取parse tree
			Tree tree = sentence.get(TreeAnnotation.class);

			TreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
			GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
			GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
			List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

			JsonArray words = new JsonArray();
			for (TypedDependency dependency : tdl) {
				System.out.println(dependency);
				System.out.println(dependency.gov().getString(PartOfSpeechAnnotation.class));
				System.out.println(dependency.gov().index() + "\t" + dependency.gov().word() + "\t" + dependency.gov().value() + "\t" + dependency.dep().index() + "\t"
						+ dependency.dep().word() + "\t" + dependency.dep().value());

				JsonObject word = new JsonObject();
				word.addProperty("gov.index", dependency.gov().index());
				word.addProperty("gov.word", dependency.gov().word());
				// word.addProperty("gov.value", dependency.gov().value());
				word.addProperty("gov.speech", dependency.gov().getString(PartOfSpeechAnnotation.class));
				word.addProperty("dep.index", dependency.dep().index());
				word.addProperty("dep.word", dependency.dep().word());
				// word.addProperty("dep.value", dependency.dep().value());
				word.addProperty("dep.speech", dependency.dep().getString(PartOfSpeechAnnotation.class));
				words.add(word);
			}

			System.out.println("word" + "\t" + "offsetBegin" + "\t" + "offsetEnd" + "\t" + "index" + "\t" + "sentenceIndex" + "\t" + "pos" + "\t" + "lemma" + "\t" + "ne" + "\t"
					+ "value" + "\t" + "begin" + "\t" + "end");
			JsonObject rootJson = findRoot(tdl);
			parseTree(tdl, rootJson);
			logger.debug(rootJson.toString());
			return words;
		}

		return null;
	}

	private static JsonObject findRoot(List<TypedDependency> tdl) {
		for (TypedDependency dependency : tdl) {
			if (ROOT.equals(dependency.gov().word()) && ROOT.equals(dependency.gov().value())) {
				logger.debug(dependency.gov().word() + "\t" + dependency.gov().value());
				JsonObject rootJson = new JsonObject();
				rootJson.addProperty(NodeId, dependency.dep().index());
				rootJson.addProperty(Word, dependency.dep().word());
				rootJson.addProperty(Value, dependency.dep().value());
				rootJson.addProperty(PartOfSpeech, dependency.dep().getString(PartOfSpeechAnnotation.class));
				return rootJson;
			}
		}
		return null;
	}

	private static void parseTree(List<TypedDependency> tdl, JsonObject parentNodeJson) {
		int nodeId = parentNodeJson.get(NodeId).getAsInt();
		JsonArray subNodes = new JsonArray();
		for (TypedDependency dependency : tdl) {
			if (nodeId == dependency.gov().index()) {
				logger.debug(dependency.gov().value() + "\t" + dependency.gov().word() + "\t" + dependency.dep().value() + "\t" + dependency.dep().word());
				JsonObject nodeJson = new JsonObject();
				nodeJson.addProperty(NodeId, dependency.dep().index());
				nodeJson.addProperty(Word, dependency.dep().word());
				nodeJson.addProperty(Value, dependency.dep().value());
				nodeJson.addProperty(PartOfSpeech, dependency.dep().getString(PartOfSpeechAnnotation.class));
				subNodes.add(nodeJson);
				parseTree(tdl, nodeJson);
			}
		}
		parentNodeJson.add(SubNodes, subNodes);
	}
}
