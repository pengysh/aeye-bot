package com.ai.bot.nlp;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class Test {

	public static void main(String[] args) {
		String grammar = args.length > 0 ? args[0] : "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
		String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
		LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
		TreebankLanguagePack tlp = lp.getOp().langpack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

		Iterable<List<? extends HasWord>> sentences;
		if (args.length > 1) {
			DocumentPreprocessor dp = new DocumentPreprocessor(args[1]);
			List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
			for (List<HasWord> sentence : dp) {
				tmp.add(sentence);
			}
			sentences = tmp;
		} else {
			// Showing tokenization and parsing in code a couple of different
			// ways.
			String[] sent = { "This", "is", "an", "easy", "sentence", "." };
			List<HasWord> sentence = new ArrayList<HasWord>();
			for (String word : sent) {
				sentence.add(new Word(word));
			}
			String sent2 = ("It has long been known that the rate of oxidative metabolism (the process that uses oxygen to convert food into energy) in any animal has a profound effect on its living patterns. The high metabolic rate of small animals, for example, gives them sustained power and activity per unit of weight, but at the cost of requiring constant consumption of food and water. Very large animals, with their relatively low metabolic rates, can survive well on a sporadic food supply, but can generate little metabolic energy per gram of body weight. If only oxidative metabolic rate is considered, therefore, one might assume that smaller, more active, animals could prey on larger ones, at least if they attacked in groups. Perhaps they could if it were not for anaerobic glycolysis, the great equalizer.");
			// Use the default tokenizer for this TreebankLanguagePack
			Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sent2));
			List<? extends HasWord> sentence2 = toke.tokenize();
			List<List<? extends HasWord>> tmp = new ArrayList<List<? extends HasWord>>();
			tmp.add(sentence);
			tmp.add(sentence2);
			sentences = tmp;
		}

		for (List<? extends HasWord> sentence : sentences) {
			Tree parse = lp.parse(sentence);
			parse.pennPrint();

			//
			//
			//
			System.out.println();
			GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
			List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
			System.out.println(tdl);
			System.out.println();

			System.out.println("The words of the sentence:");
			for (Label lab : parse.yield()) {
				if (lab instanceof CoreLabel) {
					System.out.println(((CoreLabel) lab).toString());
				} else {
					System.out.println(lab);
				}
			}
			System.out.println();
			System.out.println(parse.taggedYield());
			System.out.println();

		}

		// This method turns the String into a single sentence using the
		// default tokenizer for the TreebankLanguagePack.
		String sent3 = "This is one last test!";
		lp.parse(sent3).pennPrint();
	}

}
