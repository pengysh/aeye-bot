package com.a.eye.bot.common.message.robot.word2vec;

import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author: pengysh
 * @date 2016/8/25 0025 下午 1:05
 * @Description
 */
public class NewTest {

    public static final int batchSize = 5;

    private static final int SEQ_VECTOR_DIM = 10;

    private static final int encoderSeqLength = SEQ_VECTOR_DIM * 2 + 1;

    public static void main(String[] args) throws IOException {
        List<String> collection = new ArrayList<String>();
//        collection.add("我 是 彭勇升 <a> <a> <a> <a> <a> <a> <a>");
//        collection.add("我 叫 彭勇升 <a> <a> <a> <a> <a> <a> <a>");
//        collection.add("我 的 名字 是 彭勇升 <a> <a> <a> <a> <a>");
//        collection.add("我 今年 32 岁 <a> <a> <a> <a> <a> <a>");
        collection.add("我 的 年龄 是 32 岁 <a> <a> <a> <a>");
        collection.add("我 是 彭勇升");
//        collection.add("彭勇升 叫 我");

        SentenceIterator iter = new CollectionSentenceIterator(collection);
        // Split on white spaces in the line to get words
        TokenizerFactory t1 = new DefaultTokenizerFactory();
        t1.setTokenPreProcessor(new CommonPreprocessor());
        Word2Vec wordVectors = new Word2Vec.Builder()
                .minWordFrequency(1)
                .iterations(1)
                .layerSize(10)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t1)
                .build();

        wordVectors.fit();

        INDArray w1 = wordVectors.lookupTable().vector("我");
        INDArray w2 = wordVectors.lookupTable().vector("年龄");

        INDArray words = Nd4j.create(2, wordVectors.lookupTable().layerSize());

        words.putRow(0, w1);
        words.putRow(1, w2);

        Collection<String> iterator12 = wordVectors.wordsNearest(words, 5);
        for (String word : iterator12) {
            System.out.println(word);
        }

//        Collection<String> kingList = wordVectors.wordsNearest(Arrays.asList("我", "是"), Arrays.asList("是"), 10);
        Collection<String> kingList = wordVectors.wordsNearest("我 是 彭勇升", 5);


        INDArray ind = wordVectors.getWordVectorMatrix("我");
        INDArray otherVec = wordVectors.lookupTable().vector("我");
        Double sim = Transforms.cosineSim(ind, otherVec);
        String word = wordVectors.lookupTable().getVocabCache().wordAtIndex((sim).intValue());
        System.out.println("word:" + word);

//        System.out.println("***************************************");
//        for (String word : kingList) {
//            System.out.print(word + " ");
//        }

//        double weight = wordVectors.similarity("我", "彭勇升");
//        System.out.println("weight:" + weight);

//        System.out.println("");

//        kingList = wordVectors.wordsNearest(Arrays.asList("我"), Arrays.asList("我", "叫"), 10);
//        for (String word : kingList) {
//            System.out.print(word + " ");
//        }

        INDArray encoderSeq = Nd4j.zeros(batchSize, SEQ_VECTOR_DIM, SEQ_VECTOR_DIM);

        Iterator<INDArray> inte = wordVectors.getLookupTable().vectors();


//        int i = 0;
//        while (inte.hasNext()) {
//            System.out.println(inte.next().data());
//            encoderSeq.put(new INDArrayIndex[]{NDArrayIndex.point(i), NDArrayIndex.all(), NDArrayIndex.point(0)}, Nd4j.create(new double[]{0.1, 0.2}));
//        }

        WordVectorSerializer.writeWordVectors(wordVectors, "d:\\pathToSaveModel.txt");

        Pair<InMemoryLookupTable, VocabCache> vectors = WordVectorSerializer.loadTxt(new File("d:\\pathToSaveModel.txt"));
    }
}
