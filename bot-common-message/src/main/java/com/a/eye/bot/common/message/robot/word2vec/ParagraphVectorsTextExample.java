package com.a.eye.bot.common.message.robot.word2vec;

import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: pengysh
 * @date 2016/8/27 0027 下午 11:16
 * @Description
 */
public class ParagraphVectorsTextExample {
    private static final Logger log = LoggerFactory.getLogger(ParagraphVectorsTextExample.class);

    public static void main(String[] args) throws Exception {
        ClassPathResource resource = new ClassPathResource("/raw_sentences.txt");
        File file = resource.getFile();
        SentenceIterator iter = new BasicLineIterator(file);

        InMemoryLookupCache cache = new InMemoryLookupCache();

        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        /*
             if you don't have LabelAwareIterator handy, you can use synchronized labels generator
              it will be used to label each document/sequence/line with it's own label.

              But if you have LabelAwareIterator ready, you can can provide it, for your in-house labels
        */
        LabelsSource source = new LabelsSource("DOC_");

        ParagraphVectors vec = new ParagraphVectors.Builder()
                .minWordFrequency(1)
                .iterations(5)
                .epochs(1)
                .layerSize(100)
                .learningRate(0.025)
                .labelsSource(source)
                .windowSize(5)
                .iterate(iter)
                .trainWordVectors(false)
                .vocabCache(cache)
                .tokenizerFactory(t)
                .sampling(0)
                .build();

//        vec.fit();

        /*
            In training corpus we have few lines that contain pretty close words invloved.
            These sentences should be pretty close to each other in vector space

            line 3721: This is my way .
            line 6348: This is my case .
            line 9836: This is my house .
            line 12493: This is my world .
            line 16393: This is my work .

            this is special sentence, that has nothing common with previous sentences
            line 9853: We now have one .

            Note that docs are indexed from 0
         */

        vec = WordVectorSerializer.readParagraphVectorsFromText(new File("d:\\paraVectors.txt"));
        vec.setTokenizerFactory(t);
//
//        Collection<String> iterator1 = vec.inferVector("We");
//        for (String word : iterator1) {
//            System.out.println(word);
//        }

        INDArray w1 = vec.lookupTable().vector("I");
        INDArray w2 = vec.lookupTable().vector("am");
        INDArray w3 = vec.lookupTable().vector("sad.");

        INDArray words = Nd4j.create(3, vec.lookupTable().layerSize());

        words.putRow(0, w1);
        words.putRow(1, w2);
        words.putRow(2, w3);

//        Collection<String> iterator12 = vec.wordsNearest(words, 5);
//        for (String word : iterator12) {
//            System.out.println(word);
//        }

        double sim119 = vec.similarityToLabel("This is my house .", "DOC_12492");
        System.out.println("sim119:" + sim119);

        String response = vec.predict("But I like her .");
        System.out.println("response:" + response);

//        System.out.println("Day: " + Arrays.toString(vec.getWordVectorMatrix("day").dup().data().asDouble()));

        double similarity1 = vec.similarity("DOC_9835", "DOC_12492");
        log.info("9836/12493 ('This is my house .'/'This is my world .') similarity: " + similarity1);

        double similarity4 = vec.similarity("DOC_9835", "DOC_5435");
        log.info("9836/5436 ('This is my house .'/' This is our city .') similarity: " + similarity4);

        System.out.println("***************************************");
        double similarity11 = vec.similarity("DOC_2", "DOC_3");
        System.out.println(similarity11);

        List<String> question = new ArrayList<String>();
        question.add("This");
//        question.add("is");
//        question.add("my");

        List<String> questiona = new ArrayList<String>();
        question.add("test");

//        Collection<String> iterator13 = vec.nearestLabels("This is my part .", 2);
//        for (String word : iterator13) {
//            System.out.println(word);
//        }
        System.out.println("***************************************");

        double similarity2 = vec.similarity("DOC_3720", "DOC_16392");
        log.info("3721/16393 ('This is my way .'/'This is my work .') similarity: " + similarity2);

        double similarity3 = vec.similarity("DOC_6347", "DOC_3720");
        log.info("6348/3721 ('This is my case .'/'This is my way .') similarity: " + similarity3);

        // likelihood in this case should be significantly lower
//        double similarityX = vec.similarity("DOC_3720", "DOC_9852");
//        log.info("3721/9853 ('This is my way .'/'We now have one .') similarity: " + similarityX +
//                "(should be significantly lower)");

        WordVectorSerializer.writeWordVectors(vec, "d:\\paraVectors.txt");
    }
}