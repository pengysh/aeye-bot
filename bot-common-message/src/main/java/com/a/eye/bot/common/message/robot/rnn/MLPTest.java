package com.a.eye.bot.common.message.robot.rnn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deeplearning4j.datasets.iterator.AsyncDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.setup.ConvolutionLayerSetup;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.IOException;

/**
 * @author: pengysh
 * @date 2016/8/24 0024 下午 2:01
 * @Description
 */
public class MLPTest {

    private static Logger logger = LogManager.getLogger(MLPTest.class.getName());

    public static void main(String[] args) throws IOException {
        int batchSize = 50;     //Number of examples in each minibatch
        int truncateReviewsToLength = 300;

        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(1)
                .iterations(1)
                .epochs(1)
                .layerSize(3)
                .seed(42)
                .windowSize(5)
                .tokenizerFactory(t)
                .build();

        DataSetIterator train = new AsyncDataSetIterator(new SentimentIterator(vec, batchSize, truncateReviewsToLength, true), 1);

        int nChannels = 2;
        int nEpochs = 10;

        int seed = 123;
        double learningRate = 0.01;

        final int numRows = 5;//图像宽
        final int numColumns = 5;//图像长
        int outputNum = 10;//输出的类别数

        MultiLayerConfiguration.Builder builder = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(1)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(learningRate)
                .updater(Updater.NESTEROVS).momentum(0.9)
                .list()
                .layer(0, new ConvolutionLayer.Builder(5, 5)
                        //nIn and nOut specify depth. nIn here is the nChannels and nOut is the number of filters to be applied
                        .nIn(nChannels)
                        .stride(1, 1)
                        .nOut(20)
                        .activation("identity")
                        .build());


        new ConvolutionLayerSetup(builder, 5, 5, 1);

        MultiLayerConfiguration conf = builder.build();
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();

        logger.info("Train model....");
        model.setListeners(new ScoreIterationListener(1));
        for (int i = 0; i < nEpochs; i++) {
            logger.debug("nEpochs:" + nEpochs);
            model.fit(train);
            logger.info("*** Completed epoch {} ***", i);

            logger.info("Evaluate model....");
            Evaluation eval = new Evaluation(outputNum);
            while (train.hasNext()) {
                DataSet ds = train.next();
                INDArray output = model.output(ds.getFeatureMatrix(), false);
                eval.eval(ds.getLabels(), output);
            }
            logger.info(eval.stats());
            train.reset();
        }
        logger.info("****************Example finished********************");
    }
}
