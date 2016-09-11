package com.a.eye.bot.common.message.robot.test;

import org.apache.commons.io.FilenameUtils;
import org.deeplearning4j.datasets.iterator.AsyncDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
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
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;

/**
 * Example: Given a movie review (raw text), classify that movie review as either positive or negative based on the words it contains.
 * This is done by combining Word2Vec vectors and a recurrent neural network model. Each word in a review is vectorized
 * (using the Word2Vec model) and fed into a recurrent neural network.
 * Training data is the "Large Movie Review Dataset" from http://ai.stanford.edu/~amaas/data/sentiment/
 * <p>
 * Process:
 * 1. Download data (movie reviews) + extract. Download + extraction is done automatically.
 * 2. Load existing Word2Vec model (for example: Google News word vectors. You will have to download this MANUALLY)
 * 3. Load each each review. Convert words to vectors + reviews to sequences of vectors
 * 4. Train network for multiple epochs. At each epoch: evaluate performance on the test set.
 * <p>
 * NOTE / INSTRUCTIONS:
 * You will have to download the Google News word vector model manually. ~1.5GB before extraction.
 * The Google News vector model available here: https://code.google.com/p/word2vec/
 * Download the GoogleNews-vectors-negative300.bin.gz file, and extract to a suitable location
 * Then: set the WORD_VECTORS_PATH field to point to this location.
 *
 * @author Alex Black
 */
public class Word2VecSentimentRNN {

    /**
     * Location to save and extract the training/testing data
     */
    public static final String DATA_PATH = FilenameUtils.concat(System.getProperty("java.io.tmpdir"), "dl4j_w2vSentiment/");

    public static void main(String[] args) throws Exception {
        int batchSize = 1;     //Number of examples in each minibatch
        int vectorSize = 10;   //Size of the word vectors. 300 in the Google News model
        int nEpochs = 10;        //Number of epochs (full passes of training data) to train on
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
                .layer(0, new GravesLSTM.Builder().nIn(vectorSize).nOut(2)
                        .activation("softsign").build())
                .layer(1, new RnnOutputLayer.Builder().activation("softmax")
                        .lossFunction(LossFunctions.LossFunction.MCXENT).nIn(2).nOut(2).build())
                .pretrain(false).backprop(true).build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        net.setListeners(new ScoreIterationListener(1));

        //DataSetIterators for training and testing respectively
        //Using AsyncDataSetIterator to do data loading in a separate thread; this may improve performance vs. waiting for data to load
        WordVectors wordVectors = WordVectorSerializer.loadTxtVectors(new File("D:\\pathToSaveModel.txt"));
        DataSetIterator train = new AsyncDataSetIterator(new SentimentExampleIterator(DATA_PATH, wordVectors, batchSize, truncateReviewsToLength, true), 1);
        DataSetIterator test = new AsyncDataSetIterator(new SentimentExampleIterator(DATA_PATH, wordVectors, 1, truncateReviewsToLength, false), 1);

        System.out.println("Starting training");
        for (int i = 0; i < nEpochs; i++) {
            net.fit(train);
            train.reset();
            System.out.println("Epoch " + i + " complete. Starting evaluation:");

            //Run evaluation. This is on 25k reviews, so can take some time
            Evaluation evaluation = new Evaluation();
            while (test.hasNext()) {
                DataSet t = test.next();
                INDArray features = t.getFeatureMatrix();
                INDArray lables = t.getLabels();
                INDArray inMask = t.getFeaturesMaskArray();
                INDArray outMask = t.getLabelsMaskArray();
                INDArray predicted = net.output(features, false, inMask, outMask);

                evaluation.evalTimeSeries(lables, predicted, outMask);
            }
            test.reset();

            System.out.println(evaluation.stats());
        }


        System.out.println("----- Example complete -----");
    }
}
