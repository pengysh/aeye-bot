package com.ai.bot.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.bot.web.nlpmatch.Word;
import com.ai.bot.web.nlpmatch.result.NNResult;
import com.ai.bot.web.nlpmatch.result.SpeechResult;
import com.ai.bot.web.nlpmatch.speech.NNMatcher;
import com.ai.bot.web.nlpmatch.speech.SentenceMatcher;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MatcherTest {

	private static Gson gson = new Gson();

	private static Map<Integer, Word> words = new HashMap<Integer, Word>();

	public static void main(String[] args) {
		String sentence = "[{\"gov.index\":3,\"gov.word\":\"订\",\"gov.speech\":\"VV\",\"dep.index\":1,\"dep.word\":\"我\",\"dep.speech\":\"PN\"},{\"gov.index\":3,\"gov.word\":\"订\",\"gov.speech\":\"VV\",\"dep.index\":2,\"dep.word\":\"要\",\"dep.speech\":\"VV\"},{\"gov.index\":0,\"gov.word\":\"ROOT\",\"gov.speech\":\"\",\"dep.index\":3,\"dep.word\":\"订\",\"dep.speech\":\"VV\"},{\"gov.index\":8,\"gov.word\":\"下午\",\"gov.speech\":\"NT\",\"dep.index\":4,\"dep.word\":\"明天\",\"dep.speech\":\"NT\"},{\"gov.index\":8,\"gov.word\":\"下午\",\"gov.speech\":\"NT\",\"dep.index\":5,\"dep.word\":\"上午\",\"dep.speech\":\"NT\"},{\"gov.index\":8,\"gov.word\":\"下午\",\"gov.speech\":\"NT\",\"dep.index\":6,\"dep.word\":\"8点到\",\"dep.speech\":\"NT\"},{\"gov.index\":8,\"gov.word\":\"下午\",\"gov.speech\":\"NT\",\"dep.index\":7,\"dep.word\":\"后天\",\"dep.speech\":\"NT\"},{\"gov.index\":18,\"gov.word\":\"会议室\",\"gov.speech\":\"NN\",\"dep.index\":8,\"dep.word\":\"下午\",\"dep.speech\":\"NT\"},{\"gov.index\":8,\"gov.word\":\"下午\",\"gov.speech\":\"NT\",\"dep.index\":9,\"dep.word\":\"9点\",\"dep.speech\":\"NT\"},{\"gov.index\":8,\"gov.word\":\"下午\",\"gov.speech\":\"NT\",\"dep.index\":10,\"dep.word\":\"的\",\"dep.speech\":\"DEG\"},{\"gov.index\":12,\"gov.word\":\"个\",\"gov.speech\":\"M\",\"dep.index\":11,\"dep.word\":\"8\",\"dep.speech\":\"CD\"},{\"gov.index\":18,\"gov.word\":\"会议室\",\"gov.speech\":\"NN\",\"dep.index\":12,\"dep.word\":\"个\",\"dep.speech\":\"M\"},{\"gov.index\":18,\"gov.word\":\"会议室\",\"gov.speech\":\"NN\",\"dep.index\":13,\"dep.word\":\"人\",\"dep.speech\":\"NN\"},{\"gov.index\":13,\"gov.word\":\"人\",\"gov.speech\":\"NN\",\"dep.index\":14,\"dep.word\":\"的\",\"dep.speech\":\"DEG\"},{\"gov.index\":18,\"gov.word\":\"会议室\",\"gov.speech\":\"NN\",\"dep.index\":15,\"dep.word\":\"没有\",\"dep.speech\":\"VE\"},{\"gov.index\":15,\"gov.word\":\"没有\",\"gov.speech\":\"VE\",\"dep.index\":16,\"dep.word\":\"投影仪\",\"dep.speech\":\"NN\"},{\"gov.index\":15,\"gov.word\":\"没有\",\"gov.speech\":\"VE\",\"dep.index\":17,\"dep.word\":\"的\",\"dep.speech\":\"DEC\"},{\"gov.index\":3,\"gov.word\":\"订\",\"gov.speech\":\"VV\",\"dep.index\":18,\"dep.word\":\"会议室\",\"dep.speech\":\"NN\"}]";
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

		System.out.println(json.toString());
	}
}
