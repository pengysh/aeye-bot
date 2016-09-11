package com.a.eye.bot.common.message.robot.ann;

import com.a.eye.bot.common.message.robot.rnn.QACollection;
import com.google.gson.Gson;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.MultiDataSet;
import org.nd4j.linalg.dataset.api.MultiDataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

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
    private char[][] num1Arr;
    private char[][] sumArr;
    private boolean toTestSet;
    private final int seed;
    private final int batchSize;
    private final int totalBatches;
    private final int numdigits;
    private final int encoderSeqLength;
    private final int decoderSeqLength;
    private final int outputSeqLength;
    private final int timestep;

    private QACollection qaCollection;

    private static final int SEQ_VECTOR_DIM = 30;

    public CustomSequenceIterator(int seed, int batchSize, int totalBatches, int numdigits, int timestep, QACollection qaCollection) {

        this.seed = seed;
        this.randnumG = new Random(seed);

        this.batchSize = batchSize;
        this.totalBatches = totalBatches;

        this.numdigits = numdigits;
        this.timestep = timestep;

        this.encoderSeqLength = numdigits * 2 + 1;
        this.decoderSeqLength = numdigits + 1 + 1; // (numdigits + 1)max the sum can be
        this.outputSeqLength = numdigits + 1 + 1; // (numdigits + 1)max the sum can be and "."

        this.currentBatch = 0;

        this.qaCollection = qaCollection;
    }

    public MultiDataSet generateTest(int testSize) {
        toTestSet = true;
        MultiDataSet testData = next(testSize);
        return testData;
    }

    public char[][] testFeatures() {
        return num1Arr;
    }

    public char[][] testLabels() {
        return sumArr;
    }

    @Override
    public MultiDataSet next(int sampleSize) {
        System.out.println("sampleSize :" + sampleSize);
        /* PLEASE NOTE:
            I don't check for repeats from pair to pair with the generator
            Enhancement, to be fixed later
         */
        //Initialize everything with zeros - will eventually fill with one hot vectors
        INDArray encoderSeq = Nd4j.zeros(sampleSize, SEQ_VECTOR_DIM, encoderSeqLength);
        INDArray decoderSeq = Nd4j.zeros(sampleSize, SEQ_VECTOR_DIM, decoderSeqLength);
        INDArray outputSeq = Nd4j.zeros(sampleSize, SEQ_VECTOR_DIM, outputSeqLength);

        //Since these are fixed length sequences of timestep
        //Masks are not required
        INDArray encoderMask = Nd4j.ones(sampleSize, encoderSeqLength);
        INDArray decoderMask = Nd4j.ones(sampleSize, decoderSeqLength);
        INDArray outputMask = Nd4j.ones(sampleSize, outputSeqLength);

        if (toTestSet) {
            num1Arr = new char[sampleSize][10];
            sumArr = new char[sampleSize][10];
        }

        /* ========================================================================== */
        for (int iSample = 0; iSample < sampleSize; iSample++) {
            //Generate two random numbers with numdigits
            char[] question = qaCollection.getQuestion(iSample);
            char[] answer = qaCollection.getAnswer(iSample);

            if (toTestSet) {
                num1Arr[iSample] = question;
                sumArr[iSample] = answer;
            }
            /*
            Encoder sequence:
            Eg. with numdigits=4, num1=123, num2=90
                123 + 90 is encoded as "   09+321"
                Converted to a string to a fixed size given by 2*numdigits + 1 (for operator)
                then reversed and then masked
                Reversing input gives significant gain
                Each character is transformed to a 12 dimensional one hot vector
                    (index 0-9 for corresponding digits, 10 for "+", 11 for " ")
            */
            int spaceFill = (encoderSeqLength) - question.length;
            int iPos = 0;
            //Fill in spaces, as necessary
            while (spaceFill > 0) {
                //spaces encoded at index 12
                encoderSeq.putScalar(new int[]{iSample, 11, iPos}, 1);
                iPos++;
                spaceFill--;
            }

            //Fill in operator in this case "+", encoded at index 11
//            encoderSeq.putScalar(new int[]{iSample, 10, iPos}, 1);
//            iPos++;
            //Fill in the digits in num1 backwards
            for (int j = question.length - 1; j >= 0; j--) {
                int onehot = Character.getNumericValue(question[j]);
                System.out.println(String.valueOf(question[j]).toLowerCase());
                INDArray vector = qaCollection.getWordVectors().getWordVectorMatrix(String.valueOf(question[j]).toLowerCase());
                System.out.println(vector.data());
                encoderSeq.putScalar(new int[]{iSample, onehot, iPos}, 1);
                iPos++;
            }

            //Mask input for rest of the time series
            //while (iPos < timestep) {
            //    encoderMask.putScalar(new []{iSample,iPos},1);
            //    iPos++;
            // }
            /*
            Decoder and Output sequences:
            */
            //Fill in the digits from the sum
            iPos = 0;
            System.out.println("answer size : " + answer.length + "\t" + gson.toJson(answer));
            for (char c : answer) {
                int digit = Character.getNumericValue(c);
                System.out.println("digit: " + digit);
                outputSeq.putScalar(new int[]{iSample, digit, iPos}, 1);
                //decoder input filled with spaces
//                decoderSeq.putScalar(new int[]{iSample, 11, iPos}, 1);
                iPos++;
            }
            //Fill in spaces, as necessary
            //Leaves last index for "."
            while (iPos < numdigits + 1) {
                //spaces encoded at index 12
                outputSeq.putScalar(new int[]{iSample, 11, iPos}, 1);
                //decoder input filled with spaces
                decoderSeq.putScalar(new int[]{iSample, 11, iPos}, 1);
                iPos++;
            }
            //Predict final " "
            outputSeq.putScalar(new int[]{iSample, 10, iPos}, 1);
            decoderSeq.putScalar(new int[]{iSample, 11, iPos}, 1);
        }
        //Predict "."
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
        toTestSet = false;
        randnumG = new Random(seed);
    }

    public boolean resetSupported() {
        return true;
    }

    @Override
    public boolean hasNext() {
        //This generates numbers on the fly
        return currentBatch < totalBatches;
    }

    @Override
    public MultiDataSet next() {
        return next(batchSize);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported");
    }

    public void setPreProcessor(MultiDataSetPreProcessor multiDataSetPreProcessor) {

    }
}

