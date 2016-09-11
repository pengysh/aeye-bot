package com.a.eye.bot.common.message.robot.tsne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.plot.BarnesHutTsne;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agibsonccc on 9/20/14.
 *
 * Dimensionality reduction for high-dimension datasets
 */
public class TSNEStandardExample {

    private static Logger logger = LogManager.getLogger(TSNEStandardExample.class.getName());

    public static void main(String[] args) throws Exception  {
        int iterations = 12;
        Nd4j.dtype = DataBuffer.Type.DOUBLE;
        Nd4j.factory().setDType(DataBuffer.Type.DOUBLE);
        List<String> cacheList = new ArrayList<>();

        logger.info("Load & Vectorize data....");
        File wordFile = new ClassPathResource("words.txt").getFile();
        Pair<InMemoryLookupTable,VocabCache> vectors = WordVectorSerializer.loadTxt(wordFile);
        VocabCache cache = vectors.getSecond();
        INDArray weights = vectors.getFirst().getSyn0();

        for(int i = 0; i < cache.numWords(); i++)
            cacheList.add(cache.wordAtIndex(i));

        logger.info("Build model....");
        BarnesHutTsne tsne = new BarnesHutTsne.Builder()
                .setMaxIter(iterations).theta(0.5)
                .normalize(false)
                .learningRate(500)
                .useAdaGrad(false)
                .usePca(false)
                .build();

        logger.info("Store TSNE Coordinates for Plotting....");
        String outputFile = "d:/tsne-standard-coords.csv";
        (new File(outputFile)).getParentFile().mkdirs();
        tsne.plot(weights,2,cacheList,outputFile);
    }
}
