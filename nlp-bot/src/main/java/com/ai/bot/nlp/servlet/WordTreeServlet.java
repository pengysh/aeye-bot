package com.ai.bot.nlp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ai.bot.nlp.CoreNLP;
import com.google.gson.JsonArray;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class WordTreeServlet extends HttpServlet {

	private static final long serialVersionUID = 7279715851727549380L;
	private StanfordCoreNLP pipeline;

	public WordTreeServlet(StanfordCoreNLP pipeline) {
		this.pipeline = pipeline;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String question = request.getParameter("question");
		System.out.println("问题：" + question);
		JsonArray treeJson = CoreNLP.parseMessage(pipeline, question);

		String treeJsonStr = treeJson.toString();
		reply(treeJsonStr, response);
	}

	private void reply(String result, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter writer = response.getWriter();
		writer.write(result);
	}
}
