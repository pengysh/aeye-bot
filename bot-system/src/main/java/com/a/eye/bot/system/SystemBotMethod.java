package com.a.eye.bot.system;

import com.a.eye.bot.common.message.BotReqMessage;
import com.a.eye.bot.common.message.actor.ActorRef;
import com.a.eye.bot.common.util.DateUtil;
import com.a.eye.bot.nlp.machine.match.Word;
import com.a.eye.bot.nlp.machine.match.result.NNResult;
import com.a.eye.bot.nlp.machine.match.result.SpeechResult;
import com.a.eye.bot.nlp.machine.match.speech.NNMatcher;
import com.a.eye.bot.nlp.machine.match.speech.SentenceMatcher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemBotMethod {

	private Logger logger = LogManager.getLogger(this.getClass());

	private static Gson gson = new GsonBuilder().setDateFormat(DateUtil.DataFormatStr).create();;

	private static Map<Integer, Word> words = new HashMap<Integer, Word>();

	public void parsePersonContent(String messageId, ActorRef bridgeActorRef, ActorRef actorRef, String sentence) {
		logger.debug("传入的消息：" + sentence);
		JsonArray sentenceJson = gson.fromJson(sentence, JsonArray.class);
		for (int i = 0; i < sentenceJson.size(); i++) {
			JsonObject wordJson = sentenceJson.get(i).getAsJsonObject();

			Word word = new Word();
			word.setIndex(wordJson.get("dep.index").getAsInt());
			word.setParentIndex(wordJson.get("gov.index").getAsInt());
			word.setSpeech(wordJson.get("dep.speech").getAsString());
			word.setWord(wordJson.get("dep.word").getAsString());
			words.put(wordJson.get("dep.index").getAsInt(), word);
		}

		for (Map.Entry<Integer, Word> entry : words.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue().getIndex() + "\t" + entry.getValue().getParentIndex() + "\t" + entry.getValue().getWord() + "\t"
					+ entry.getValue().getSpeech());
		}

		System.out.println("\n");
		NNMatcher nnMatcher = new NNMatcher();
		List<SpeechResult> speechResultAllList = new ArrayList<SpeechResult>();
		SpeechResult speechResultFirst = nnMatcher.matcherFirst(words, -1, -1);
		speechResultAllList.add(speechResultFirst);

		if (speechResultFirst != null) {
			NNResult nnResult = (NNResult) speechResultFirst;
			System.out.println(nnResult.getId() + "\t" + nnResult.getParentId() + "\t" + nnResult.getWord() + "\t" + nnResult.getPropertyName() + "\t" + nnResult.getValue());
			SentenceMatcher sentenceMatcher = new SentenceMatcher();
			List<SpeechResult> resultList = sentenceMatcher.matcher(words, nnResult.getId(), nnResult.getParentId());
			speechResultAllList.addAll(resultList);
			for (SpeechResult result : resultList) {
				System.out.println(result.getId() + "\t" + result.getParentId() + "\t" + result.getWord() + "\t" + result.getPropertyName() + "\t" + result.getValue());
			}

			JsonObject json = new JsonObject();
			json.addProperty("id", speechResultFirst.getValue());
			for (SpeechResult speechResult : speechResultAllList) {
				if (StringUtils.isNotBlank(speechResult.getPropertyName())) {
					json.addProperty(speechResult.getPropertyName(), speechResult.getValue());
				}
			}

			logger.debug(json.toString());

			String event = json.get("event").getAsString();
			Class clazz = null;
			try {
				clazz = Class.forName(event);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			BotReqMessage message = (BotReqMessage) gson.fromJson(json, clazz);

			actorRef.tell(messageId, message, bridgeActorRef);
		}
	}

	public void parseSystemContent(String messageId, ActorRef bridgeActorRef, ActorRef actorRef, String encodeMessage) {
		String messageDecode = new String(Base64.decodeBase64(encodeMessage));
		logger.debug("messageDecode：" + messageDecode);
		JsonObject messageJson = gson.fromJson(messageDecode, JsonObject.class);
		logger.debug("messageJson：" + messageJson.toString());

		String event = messageJson.get("event").getAsString();
		Class clazz = null;
		try {
			clazz = Class.forName(event);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		BotReqMessage message = (BotReqMessage) gson.fromJson(messageJson.get("content").toString(), clazz);

		actorRef.tell(messageId, message, bridgeActorRef);
	}
}
