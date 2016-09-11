package com.a.eye.bot.common.message.robot.rnn;

import com.google.gson.Gson;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.graph.rnn.DuplicateToTimeSeriesVertex;
import org.deeplearning4j.nn.conf.graph.rnn.LastTimeStepVertex;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.util.DataTypeUtil;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.MultiDataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.util.ArrayList;


/**
 * Created by susaneraly on 3/27/16.
 */
public class AdditionRNN {
    /*
        This example is modeled off the sequence to sequence RNNs described in http://arxiv.org/abs/1410.4615
        Specifically, a sequence to sequence NN is build for the addition operation
        Two numbers and the addition operator are encoded as a sequence and passed through an "encoder" RNN
        The output from the last time step of the encoder RNN is reinterpreted as a time series and passed through the "decoder" RNN
        The result is the output of the decoder RNN which in training is the sum, encoded as a sequence.
        One hot vectors are used for encoding/decoding
        20 epochs give >85% accuracy for 2 digits
        To try out addition for numbers with different number of digits simply change "NUM_DIGITS"
     */

    //Random number generator seed, for reproducability
    public static final int seed = 80;

    public static final int NUM_DIGITS = 2;
    public static final int FEATURE_VEC_SIZE = 10;

    //Tweak these to tune - dataset size = batchSize * totalBatches
    public static final int batchSize = 1;
    public static final int totalBatches = 10;
    public static final int nEpochs = 50;
    public static final int nIterations = 1;
    public static final int numHiddenNodes = 128;

    private static Gson gson = new Gson();

    //Currently the sequences are implemented as length = max length
    //This is a placeholder for an enhancement
    public static final int timeSteps = NUM_DIGITS * 2 + 1;

    public static void main(String[] args) throws Exception {

        // Split on white spaces in the line to get words
        TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

        Word2Vec wordVectors = new Word2Vec.Builder()
                .minWordFrequency(1)
                .iterations(1)
                .epochs(1)
                .layerSize(10)
                .seed(42)
                .windowSize(5)
                .tokenizerFactory(tokenizerFactory)
                .build();

        DataTypeUtil.setDTypeForContext(DataBuffer.Type.DOUBLE);
        //Training data iterator
        CustomSequenceIterator iterator = new CustomSequenceIterator(seed, 300, timeSteps, wordVectors);

        ComputationGraphConfiguration configuration = new NeuralNetConfiguration.Builder()
                //.regularization(true).l2(0.000005)
                .weightInit(WeightInit.XAVIER)
                .learningRate(0.5)
                .updater(Updater.RMSPROP)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).iterations(nIterations)
                .seed(seed)
                .graphBuilder()
                .addInputs("additionIn", "sumOut")
                .setInputTypes(InputType.recurrent(FEATURE_VEC_SIZE), InputType.recurrent(FEATURE_VEC_SIZE))
                .addLayer("encoder", new GravesLSTM.Builder().nIn(FEATURE_VEC_SIZE).nOut(numHiddenNodes).activation("softsign").build(), "additionIn")
                .addVertex("lastTimeStep", new LastTimeStepVertex("additionIn"), "encoder")
                .addVertex("duplicateTimeStep", new DuplicateToTimeSeriesVertex("sumOut"), "lastTimeStep")
                .addLayer("decoder", new GravesLSTM.Builder().nIn(FEATURE_VEC_SIZE + numHiddenNodes).nOut(numHiddenNodes).activation("softsign").build(), "sumOut", "duplicateTimeStep")
                .addLayer("output", new RnnOutputLayer.Builder().nIn(numHiddenNodes).nOut(FEATURE_VEC_SIZE).activation("softmax").lossFunction(LossFunctions.LossFunction.MCXENT).build(), "decoder")
                .setOutputs("output")
                .pretrain(false).backprop(true)
                .build();

        ComputationGraph net = new ComputationGraph(configuration);
        net.init();
        //net.setListeners(new ScoreIterationListener(200),new HistogramIterationListener(200));
        net.setListeners(new ScoreIterationListener(1));
        //net.setListeners(new HistogramIterationListener(200));
        //Train model:
        int iEpoch = 0;
        int testSize = 10;
        while (iEpoch < nEpochs) {
            System.out.printf("* = * = * = * = * = * = * = * = * = ** EPOCH %d ** = * = * = * = * = * = * = * = * = * = * = * = * = * =\n", iEpoch);
            net.fit(iterator);
            System.out.println("* = * = * = * = * = * = * = * = * = *");

            MultiDataSet testData = iterator.generateTest(2);
            ArrayList<int[]> testNums = iterator.testFeatures();
            int[] testnum1 = testNums.get(0);
            int[] testnum2 = testNums.get(1);
            System.out.println("testnum1:" + gson.toJson(testnum1).toString() + ",testnum2" + gson.toJson(testnum2).toString());
            int[] testSums = iterator.testLabels();
            System.out.println("Features:" + testData.getFeatures(0) + "\t" + testData.getFeatures(1));
            INDArray[] prediction_array = net.output(new INDArray[]{testData.getFeatures(0), testData.getFeatures(1)});
            INDArray predictions = prediction_array[0];
            INDArray answers = Nd4j.argMax(predictions, 1);

            INDArray otherVec = wordVectors.lookupTable().vector("我");
            Double sim = Transforms.cosineSim(answers, otherVec);
            String word = wordVectors.lookupTable().getVocabCache().wordAtIndex((sim).intValue());
            System.out.println("word result:" + word);

            encode_decode(testnum1, testnum2, testSums, answers, wordVectors);

            iterator.reset();
            iEpoch++;
        }
        System.out.printf("\n* = * = * = * = * = * = * = * = * = ** EPOCH COMPLETE ** = * = * = * = * = * = * = * = * = * = * = * = * = * =\n", iEpoch);

    }

    //This is a helper function to make the predictions from the net more readable
    private static void encode_decode(int[] num1, int[] num2, int[] sum, INDArray answers, Word2Vec wordVectors) {

        int nTests = answers.size(0);
        int wrong = 0;
        int correct = 0;
        for (int iTest = 0; iTest < nTests; iTest++) {
            int aDigit = NUM_DIGITS;
            int thisAnswer = 0;
            String strAnswer = "";
            while (aDigit >= 0) {
                System.out.println("while" + aDigit + strAnswer);

                int thisDigit = (int) answers.getDouble(iTest, aDigit);
                System.out.println(thisDigit);
                if (thisDigit <= 9) {
                    strAnswer += String.valueOf(thisDigit);
                    thisAnswer += thisDigit * (int) Math.pow(10, aDigit);
                } else {
                    System.out.println(thisDigit + " is string " + String.valueOf(thisDigit));
                    strAnswer += " ";
                    //break;
                }
                aDigit--;
            }
            System.out.println("strAnswer:" + strAnswer);
            String strAnswerR = new StringBuilder(strAnswer).reverse().toString();
            strAnswerR = strAnswerR.replaceAll("\\s+", "");
            if (strAnswerR.equals(String.valueOf(sum[iTest]))) {
                System.out.println(num1[iTest] + "+" + num2[iTest] + "==" + strAnswerR);
                correct++;
            } else {
                System.out.println(num1[iTest] + "+" + num2[iTest] + "!=" + strAnswerR + ", should==" + sum[iTest]);
                wrong++;
            }
        }
        double randomAcc = Math.pow(10, -1 * (NUM_DIGITS + 1)) * 100;
        System.out.println("*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*==*=*=*=*=*");
        System.out.println("WRONG: " + wrong);
        System.out.println("CORRECT: " + correct);
        System.out.println("Note randomly guessing digits in succession gives lower than a accuracy of:" + randomAcc + "%");
        System.out.println("The digits along with the spaces have to be predicted\n");
    }

}

