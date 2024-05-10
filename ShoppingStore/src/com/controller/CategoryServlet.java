package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ProductDAO;
import com.model.Product;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGET(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int category = Integer.parseInt(request.getParameter("category"));
		ProductDAO productDAO = new ProductDAO();
		List<Product> products = productDAO.getAllCategoryProducts(category);
		System.out.println(products.toString());

		String productListHtml = productDAO.generateProductListHtml(products);

		// Set the content type of the response
		response.setContentType("text/html");

		// Get the PrintWriter object to write the response
		PrintWriter out = response.getWriter();

		// Write the product list HTML to the response
		out.println(productListHtml);

		// Close the PrintWriter
		out.close();
		// request.setAttribute("products", products);
		// request.getRequestDispatcher("products.jsp").forward(request, response);
	}

}
