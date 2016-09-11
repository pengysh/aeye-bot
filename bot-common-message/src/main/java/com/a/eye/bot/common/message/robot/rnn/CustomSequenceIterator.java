package com.a.eye.bot.common.message.robot.rnn;

import com.google.gson.Gson;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.MultiDataSet;
import org.nd4j.linalg.dataset.api.MultiDataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by susaneraly on 3/27/16.
 * This is class to generate pairs of random numbers given a maximum number of digits
 * This class can also be used as a reference for dataset iterators and writing one's own custom dataset iterator
 */

public class CustomSequenceIterator implements MultiDataSetIterator {

    private Gson gson = new Gson();

    private Random randnumG;
    private int currentBatch;
    private int[] num1Arr;
    private int[] num2Arr;
    private int[] sumArr;
    private final int seed;
    private final int batchSize;
    private final int truncateLength;
    private List<String> questionCollection = new ArrayList<String>();
    private List<String> answerCollection = new ArrayList<String>();

    private static final int SEQ_VECTOR_DIM = 10;
    private Word2Vec word2Vec;

    public CustomSequenceIterator(int seed, int truncateLength, int timestep, Word2Vec word2Vec) {
        this.seed = seed;
        this.randnumG = new Random(seed);

        questionCollection.add("你 叫 什么");
        questionCollection.add("你 是 谁");
        questionCollection.add("你 的 名字 是 什么");
        questionCollection.add("你 今年 多少 岁");
        questionCollection.add("你 的 年龄 是 多少 岁");

        answerCollection.add("我 叫 彭勇升");
        answerCollection.add("我 是 彭勇升");
        answerCollection.add("我 的 名字 是 彭勇升");
        answerCollection.add("我 今年 32 岁");
        answerCollection.add("我 的 年龄 是 32 岁");

        this.batchSize = questionCollection.size();

        this.truncateLength = truncateLength;

        this.currentBatch = 0;
        this.word2Vec = word2Vec;
    }

    public MultiDataSet generateTest(int currentBatch) {
        MultiDataSet testData = next(currentBatch);
        return testData;
    }

    public ArrayList<int[]> testFeatures() {
        ArrayList<int[]> testNums = new ArrayList<int[]>();
        testNums.add(num1Arr);
        testNums.add(num2Arr);
        return testNums;
    }

    public int[] testLabels() {
        return sumArr;
    }

    @Override
    public MultiDataSet next(int current) {
        System.out.println("MultiDataSet next");
        List<String> questionOneceCollection = new ArrayList<String>();
        for (String word : questionCollection.get(current).split(" ")) {
            questionOneceCollection.add(word);
            System.out.println("word:" + word);
        }

        SentenceIterator questionIterator = new CollectionSentenceIterator(questionOneceCollection);

        word2Vec.setSentenceIter(questionIterator);
        word2Vec.fit();

        List<String> answerOneceCollection = new ArrayList<String>();
        for (String word : answerCollection.get(current).split(" ")) {
            answerOneceCollection.add(word);
            System.out.println("word:" + word);
        }

        SentenceIterator answerIterator = new CollectionSentenceIterator(answerOneceCollection);

        //Second: tokenize reviews and filter out unknown words
        List<List<String>> allTokens = new ArrayList<>(batchSize);
        int maxLength = 0;
        for (String s : questionOneceCollection) {
            List<String> tokens = word2Vec.getTokenizerFactory().create(s).getTokens();
            List<String> tokensFiltered = new ArrayList<>();
            for (String t : tokens) {
                if (word2Vec.hasWord(t)) tokensFiltered.add(t);
            }
            allTokens.add(tokensFiltered);
            maxLength = Math.max(maxLength, tokensFiltered.size());
        }

        //If longest review exceeds 'truncateLength': only take the first 'truncateLength' words
        if (maxLength > truncateLength) maxLength = truncateLength;

        //Predict "."
        /* ========================================================================== */
        INDArray encoderSeq = Nd4j.zeros(10, SEQ_VECTOR_DIM, 10);
        INDArray decoderSeq = Nd4j.zeros(10, SEQ_VECTOR_DIM, 10);
        INDArray outputSeq = Nd4j.zeros(10, SEQ_VECTOR_DIM, 10);

        //Since these are fixed length sequences of timestep
        //Masks are not required
        INDArray encoderMask = Nd4j.ones(10, 10);
        INDArray decoderMask = Nd4j.ones(10, 10);
        INDArray outputMask = Nd4j.ones(10, 10);

        System.out.println("***********************");
        INDArray[] encoder = new INDArray[10];
        for (int i = 0; i < 10; i++) {
            if (i < questionOneceCollection.size()) {
                System.out.println(questionOneceCollection.get(i));
                System.out.println(gson.toJson(word2Vec.getWordVectorMatrix(questionOneceCollection.get(i))).toString());
                encoder[i] = word2Vec.getWordVectorMatrix(questionOneceCollection.get(i));
                encoderSeq.put(new INDArrayIndex[]{NDArrayIndex.point(current), NDArrayIndex.point(i), NDArrayIndex.all()}, encoder[i]);
            } else {
                encoder[i] = Nd4j.zeros(1, 10);
                encoderSeq.put(new INDArrayIndex[]{NDArrayIndex.point(current), NDArrayIndex.point(i), NDArrayIndex.all()}, encoder[i]);
            }

        }
        System.out.println("***********************");

        System.out.println("***********************");
        INDArray[] decoder = new INDArray[10];
        for (int i = 0; i < 10; i++) {
            if (i < answerOneceCollection.size()) {
                System.out.println(answerOneceCollection.get(i));
                System.out.println(gson.toJson(word2Vec.getWordVectorMatrix(answerOneceCollection.get(i))).toString());
                decoder[i] = word2Vec.getWordVectorMatrix(answerOneceCollection.get(i));
                decoderSeq.put(new INDArrayIndex[]{NDArrayIndex.point(current), NDArrayIndex.point(i), NDArrayIndex.all()}, decoder[i]);
            } else {
                decoder[i] = Nd4j.zeros(1, 10);
                decoderSeq.put(new INDArrayIndex[]{NDArrayIndex.point(current), NDArrayIndex.point(i), NDArrayIndex.all()}, decoder[i]);
            }

        }
        System.out.println("***********************");

        /* ========================================================================== */
        INDArray[] inputs = new INDArray[]{encoderSeq, decoderSeq};
        INDArray[] inputMasks = new INDArray[]{encoderMask, decoderMask};
        INDArray[] labels = new INDArray[]{outputSeq};
        INDArray[] labelMasks = new INDArray[]{outputMask};
        currentBatch++;
        return new org.nd4j.linalg.dataset.MultiDataSet(inputs, labels, inputMasks, labelMasks);
    }

    @Override
    public void reset() {
        currentBatch = 0;
        randnumG = new Random(seed);
    }

    public boolean resetSupported() {
        return true;
    }

    @Override
    public boolean hasNext() {
        System.out.println("hasNext:" + currentBatch + "\t" + batchSize + "\t" + (currentBatch < batchSize));
        return currentBatch < batchSize;
    }

    @Override
    public MultiDataSet next() {
        return next(currentBatch);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported");
    }

    public void setPreProcessor(MultiDataSetPreProcessor multiDataSetPreProcessor) {

    }
}

