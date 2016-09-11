package com.a.eye.bot.common.message.robot.rnn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deeplearning4j.datasets.iterator.AsyncDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: pengysh
 * @date 2016/8/24 0024 上午 12:44
 * @Description
 */
public class Word2VecTest {

    private static Logger logger = LogManager.getLogger(Word2VecTest.class.getName());

    public static final String DATA_PATH = "";

    public static void main(String[] args) throws IOException {
        List<String> collection = new ArrayList<String>();
        collection.add("我 叫 彭勇升");
        collection.add("我 是 彭勇升");
        collection.add("我 的 名字 是 彭勇升");
        collection.add("我 今年 32 岁");
        collection.add("我 的 年龄 是 32 岁");

        SentenceIterator iter = new CollectionSentenceIterator(collection);
        // Split on white spaces in the line to get words
        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());
        Word2Vec wordVectors = new Word2Vec.Builder()
                .minWordFrequency(1)
                .iterations(10)
                .epochs(10)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        wordVectors.fit();

        Collection<String> lst = wordVectors.wordsNearest("名字", 5);
        System.out.println(lst);

        int batchSize = 50;     //Number of examples in each minibatch
        int vectorSize = 100;   //Size of the word vectors. 300 in the Google News model
        int nEpochs = 5;        //Number of epochs (full passes of training data) to train on
        int truncateReviewsToLength = 300;  //Truncate reviews with length (# words) greater than this

        //Set up network configuration
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).iterations(1)
                .updater(Updater.RMSPROP)
                .regularization(true).l2(1e-5)
                .weightInit(WeightInit.XAVIER)
                .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue).gradientNormalizationThreshold(1.0)
                .learningRate(0.0018)
                .list()
                .layer(0, new GravesLSTM.Builder().nIn(vectorSize).nOut(200)
                        .activation("softsign").build())
                .layer(1, new RnnOutputLayer.Builder().activation("softmax")
                        .lossFunction(LossFunctions.LossFunction.MCXENT).nIn(200).nOut(2).build())
                .pretrain(false).backprop(true).build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        net.setListeners(new ScoreIterationListener(1));

        //DataSetIterators for training and testing respectively
        //Using AsyncDataSetIterator to do data loading in a separate thread; this may improve performance vs. waiting for data to load
        DataSetIterator train = new AsyncDataSetIterator(new SentimentExampleIterator(DATA_PATH, wordVectors, batchSize, truncateReviewsToLength, true), 1);
        DataSetIterator test = new AsyncDataSetIterator(new SentimentExampleIterator(DATA_PATH, wordVectors, 100, truncateReviewsToLength, false), 1);

        System.out.println("Starting training");
        for (int i = 0; i < nEpochs; i++) {
            net.fit(train);
            train.reset();
            System.out.println("Epoch " + i + " complete. Starting evaluation:");

            //Run evaluation. This is on 25k reviews, so can take some time
            Evaluation evaluation = new Evaluation();
            while (test.hasNext()) {
                DataSet dataSet = test.next();
                INDArray features = dataSet.getFeatureMatrix();
                INDArray lables = dataSet.getLabels();
                INDArray inMask = dataSet.getFeaturesMaskArray();
                INDArray outMask = dataSet.getLabelsMaskArray();
                INDArray predicted = net.output(features, false, inMask, outMask);

                evaluation.evalTimeSeries(lables, predicted, outMask);

                dataSet.getFeatureMatrix();
            }
            test.reset();

            System.out.println(evaluation.stats());
        }


        System.out.println("----- Example complete -----");
    }
}
