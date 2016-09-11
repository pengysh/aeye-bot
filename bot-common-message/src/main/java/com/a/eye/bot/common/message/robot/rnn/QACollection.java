package com.a.eye.bot.common.message.robot.rnn;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: pengysh
 * @date 2016/8/26 0026 下午 6:50
 * @Description
 */
public class QACollection {

    private Word2Vec word2Vec;
    private char[][] questions = new char[11][];
    private char[][] answers = new char[11][];

    public void init() {
        List<String> sentence = new ArrayList<String>();
        SentenceIterator iter = new CollectionSentenceIterator(sentence);
        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        word2Vec = new Word2Vec.Builder()
                .minWordFrequency(1)
                .iterations(1)
                .layerSize(10)
                .iterate(iter)
                .tokenizerFactory(t)
                .seed(42)
                .windowSize(5)
                .build();


        questions[0] = fill("我的朋友是谁");
        questions[1] = fill("我的朋友是谁");
        questions[2] = fill("我的朋友是谁");
        questions[3] = fill("我的朋友是谁");
        questions[4] = fill("我的朋友是谁");
        questions[5] = fill("我的朋友是谁");
        questions[6] = fill("我的朋友是谁");
        questions[7] = fill("我的朋友是谁");
        questions[8] = fill("我的朋友是谁");
        questions[9] = fill("我的朋友是谁");
        questions[10] = fill("我的朋友是谁");

        answers[0] = fill("我的朋友是小明");
        answers[1] = fill("我的朋友是小王");
        answers[2] = fill("我的朋友是小李");
        answers[3] = fill("我的朋友是小张");
        answers[4] = fill("我的朋友是小彭");
        answers[5] = fill("我的朋友是小程A");
        answers[6] = fill("我的朋友是小程B");
        answers[7] = fill("我的朋友是小程C");
        answers[8] = fill("我的朋友是小程D");
        answers[9] = fill("我的朋友是小程E");
        answers[10] = fill("我的朋友是小程F");
    }

    public char[] getQuestion(int iSample) {
        return questions[iSample];
    }

    public char[] getAnswer(int iSample) {
        return answers[iSample];
    }

    public char[] fill(String str) {
        int length = str.length();
        for (int i = 0; i < 10 - length; i++) {
            str = str + "T";
        }
        System.out.println(String.valueOf(str));
        createWordVectors(str.toCharArray());
        return str.toCharArray();
    }

    private void createWordVectors(char[] sentenceChar) {
        List<String> sentence = new ArrayList<String>();
        for (char wordChar : sentenceChar) {
            sentence.add(String.valueOf(wordChar));
        }

        SentenceIterator iter = new CollectionSentenceIterator(sentence);
        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        word2Vec.setSentenceIter(iter);
        word2Vec.setTokenizerFactory(t);
        word2Vec.fit();

        try {
            WordVectorSerializer.writeWordVectors(word2Vec, "d:/pathToWriteto.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Word2Vec getWordVectors() {
        return word2Vec;
    }
}
