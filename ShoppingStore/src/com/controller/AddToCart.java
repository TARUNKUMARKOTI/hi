package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ProductDAO;
import com.model.Product;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/addToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddToCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		List<Product> cartItems = (List<Product>) session.getAttribute("cartItems");

		int productId = Integer.parseInt(request.getParameter("productId"));
		ProductDAO productDAO = new ProductDAO();
		Product productToAdd = productDAO.getProductById(productId);

		if (cartItems == null) {
			cartItems = new ArrayList<>();
			session.setAttribute("cartItems", cartItems);
		}
		// Check if the item is already present in the cartItems list
		boolean alreadyInCart = cartItems.stream().anyMatch(item -> item.getId() == productToAdd.getId());

		// If not present, add it to the cartItems list
		if (!alreadyInCart) {
			cartItems.add(productToAdd);
		}

		// cartItems.add(productToAdd);

		response.sendRedirect(request.getContextPath() + "/products");
	}

}
