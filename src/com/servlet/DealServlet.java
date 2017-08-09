package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.TestPost;

/**
 * Servlet implementation class DealServlet
 */
@WebServlet("/DealServlet")
public class DealServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DealServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		System.out.println("****df******");
		
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		String title = "Stats for this deal.";
		String docType =
				"<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

		out.println(docType +
				"<html>\n" +
				"<head><title>" + title + "</title></head>\n" +
				"<body bgcolor = \"#f0f0f0\">\n" +
				"<h1 align = \"center\">" + title + "</h1>\n" +
				"<ul>\n" +
				"  <li><b>Total Number of Simulations: </b>: "
				+ TestPost.postMe()[0] + "\n" +
				"  <li><b>Number of Splits: </b>: "
				+ TestPost.postMe()[1] + "\n" +
				"  <li><b>Number of Straight Flushes: </b>: "
				+ TestPost.postMe()[2] + "\n" +
				"  <li><b>Number of Four of a Kinds: </b>: "
				+ TestPost.postMe()[3] + "\n" +
				"  <li><b>Number of Full Houses: </b>: "
				+ TestPost.postMe()[4] + "\n" +
				"  <li><b>Number of Flushes: </b>: "
				+ TestPost.postMe()[5] + "\n" +
				"  <li><b>Number of Straights: </b>: "
				+ TestPost.postMe()[6] + "\n" +
				"  <li><b>Number of Sets: </b>: "
				+ TestPost.postMe()[7] + "\n" +
				"  <li><b>Number of Two Pairs: </b>: "
				+ TestPost.postMe()[8] + "\n" +
				"  <li><b>Number of High Cards: </b>: "
				+ TestPost.postMe()[9] + "\n" +
				"</ul>\n" +
				"</body> "
				+ "</html>"
				);

	}

}
