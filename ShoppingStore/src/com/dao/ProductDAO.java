package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.model.PricingDetails;
import com.model.Product;

public class ProductDAO {
	Connection conn;

	public ProductDAO() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://192.168.110.48:5432/plf_training",
					"plf_training_admin", "pff123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		String query = "SELECT prod_id, prod_title, prod_prct_id, prod_hsnc_id, prod_brand, prod_image, shipping_charges, discount_percentage FROM public.i194_products;";
		String GET_PRICE_BY_ID_QUERY = "SELECT prod_price FROM public.i194_productstock WHERE prod_id = ?";

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("prod_id"));
				product.setTitle(rs.getString("prod_title"));

				PreparedStatement stmt1 = conn.prepareStatement(GET_PRICE_BY_ID_QUERY);

				// Set the parameter (product ID) in the prepared statement
				stmt1.setInt(1, rs.getInt("prod_id"));

				// Execute the query
				ResultSet rs1 = stmt1.executeQuery();
				if (rs1.next()) {
					// Retrieve the product price from the result set
					product.setPrice(rs1.getDouble("prod_price"));
					// price = rs.getDouble("prod_price");
				}
				product.setProd_brand(rs.getString("prod_brand"));
				product.setImage(rs.getString("prod_image"));
				product.setHsnc_id(rs.getInt("prod_hsnc_id"));
				product.setCt_id(rs.getInt("prod_prct_id"));
				product.setDiscount_percentage(rs.getInt("discount_percentage"));
				product.setShipping_charges(rs.getInt("shipping_charges"));
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(products.toString());
		return products;
	}

	public List<Product> getAllCategoryProducts(int category) {
		// TODO Auto-generated method stub

		List<Product> products = new ArrayList<>();
		String query = "SELECT prod_id, prod_title, prod_brand, prod_image, prod_prct_id, prod_hsnc_id, shipping_charges, discount_percentage FROM public.i194_products WHERE prod_prct_id=?";
		String GET_PRICE_BY_ID_QUERY = "SELECT prod_price FROM public.i194_productstock WHERE prod_id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, category);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("prod_id"));
				product.setTitle(rs.getString("prod_title"));

				PreparedStatement stmt1 = conn.prepareStatement(GET_PRICE_BY_ID_QUERY);

				// Set the parameter (product ID) in the prepared statement
				stmt1.setInt(1, rs.getInt("prod_id"));

				// Execute the query
				ResultSet rs1 = stmt1.executeQuery();
				if (rs1.next()) {
					// Retrieve the product price from the result set
					product.setPrice(rs1.getDouble("prod_price"));
					// price = rs.getDouble("prod_price");
				}
				product.setProd_brand(rs.getString("prod_brand"));
				product.setImage(rs.getString("prod_image"));
				product.setHsnc_id(rs.getInt("prod_hsnc_id"));
				product.setCt_id(rs.getInt("prod_prct_id"));
				product.setDiscount_percentage(rs.getInt("discount_percentage"));
				product.setShipping_charges(rs.getInt("shipping_charges"));
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(products.toString());
		return products;
	}

	public List<Product> getAllCategoryOrderProducts(int category, int order) {
		// TODO Auto-generated method stub
		List<Product> products = new ArrayList<>();

		String query = "SELECT ps.prod_id, p.prod_title, p.prod_brand,p.prod_image, p.prod_prct_id, p.prod_hsnc_id, p.shipping_charges, p.discount_percentage, ps.prod_price "
				+ "FROM i194_productstock ps " + "INNER JOIN i194_products p ON ps.prod_id = p.prod_id "
				+ "WHERE p.prod_prct_id = ? " + "ORDER BY ps.prod_price " + (order == 1 ? "ASC" : "DESC");

		try (PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, category);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("prod_id"));
				product.setTitle(rs.getString("prod_title"));
				product.setPrice(rs.getDouble("prod_price"));
				product.setProd_brand(rs.getString("prod_brand"));
				product.setImage(rs.getString("prod_image"));
				product.setHsnc_id(rs.getInt("prod_hsnc_id"));
				product.setCt_id(rs.getInt("prod_prct_id"));
				product.setDiscount_percentage(rs.getInt("discount_percentage"));
				product.setShipping_charges(rs.getInt("shipping_charges"));
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;

	}

	public String generateProductListHtml(List<Product> products) {
		// TODO Auto-generated method stub
		StringBuilder htmlBuilder = new StringBuilder();

		// Check if products list is not null
		if (products != null) {
			// Iterate over the products and generate HTML for each product
			for (Product product : products) {
				htmlBuilder.append("<div class=\"product\">");
				htmlBuilder.append("<div class=\"product-info\">");
				htmlBuilder.append(
						"<img src=\"" + product.getImage() + "\" alt=\"Product Image\" class=\"product-image\">");
				htmlBuilder.append("<div class=\"product-title\">" + product.getTitle() + "</div>");
				htmlBuilder.append("<div class=\"product-brand\">" + product.getProd_brand() + "</div>");
				htmlBuilder.append("<div class=\"product-price\">$" + product.getPrice() + "</div>");
				htmlBuilder.append("</div>");
				// Add button with product id as class for adding to cart
				htmlBuilder.append("<button class=\"add-to-cart\" data-product-id=\"" + product.getId()
						+ "\" onclick=\"addToCart(" + product.getId() + ")\">Add to Cart</button>");

				htmlBuilder.append("</div>");
			}
		}

		// Return the generated HTML content
		return htmlBuilder.toString();
	}

	public List<Product> getAllOrderProducts(int order) {
		// TODO Auto-generated method stub
		List<Product> products = new ArrayList<>();

		String query = "SELECT ps.prod_id, p.prod_title, p.prod_brand,p.prod_image, p.prod_prct_id, p.prod_hsnc_id, p.shipping_charges, p.discount_percentage, ps.prod_price "
				+ "FROM i194_productstock ps " + "INNER JOIN i194_products p ON ps.prod_id = p.prod_id "
				+ "ORDER BY ps.prod_price " + (order == 1 ? "ASC" : "DESC");

		try (PreparedStatement stmt = conn.prepareStatement(query)) {

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("prod_id"));
				product.setTitle(rs.getString("prod_title"));
				product.setPrice(rs.getDouble("prod_price"));
				product.setProd_brand(rs.getString("prod_brand"));
				product.setImage(rs.getString("prod_image"));
				product.setHsnc_id(rs.getInt("prod_hsnc_id"));
				product.setCt_id(rs.getInt("prod_prct_id"));
				product.setDiscount_percentage(rs.getInt("discount_percentage"));
				product.setShipping_charges(rs.getInt("shipping_charges"));
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		Product product = new Product();
		String query = "SELECT ps.prod_id, p.prod_title, p.prod_brand,p.prod_image, p.prod_prct_id, p.prod_hsnc_id, p.shipping_charges, p.discount_percentage, ps.prod_price "
				+ "FROM i194_productstock ps " + "INNER JOIN i194_products p ON ps.prod_id = p.prod_id "
				+ "WHERE ps.prod_id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, productId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				product.setId(rs.getInt("prod_id"));
				product.setTitle(rs.getString("prod_title"));
				product.setPrice(rs.getDouble("prod_price"));
				product.setProd_brand(rs.getString("prod_brand"));
				product.setImage(rs.getString("prod_image"));
				product.setHsnc_id(rs.getInt("prod_hsnc_id"));
				product.setCt_id(rs.getInt("prod_prct_id"));
				product.setDiscount_percentage(rs.getInt("discount_percentage"));
				product.setShipping_charges(rs.getInt("shipping_charges"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public PricingDetails priceCalculation(List<Product> cartItems) {
		// TODO Auto-generated method stub
		PricingDetails details = new PricingDetails();
		String query = "SELECT  hsnc_gstc_percentage FROM public.i194_hscodes where hsnc_id= ?";
		// String shippingOnOrder=""
		double productSpecificGSTPerc = 0.0;
		double salePrice = 0.0;
		double productActualPrice = 0.0;
		double productGST = 0.0;
		double productDiscount = 0.0;
		double productShippingCharge = 0.0;
		double actualCombinedPrice = 0.0;
		double combinedGST = 0.0;
		double combinedDiscount = 0.0;
		double totalshippingCharges = 0.0;
		double totalPrice = 0.0;

		for (Product product : cartItems) {

			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setInt(1, product.getHsnc_id());
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					productSpecificGSTPerc = rs.getDouble("hsnc_gstc_percentage");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			productSpecificGSTPerc = productSpecificGSTPerc / 100;
			salePrice = product.getPrice();
			productActualPrice = salePrice / (1 + productSpecificGSTPerc);
			actualCombinedPrice += productActualPrice;

			productGST = salePrice - productActualPrice;
			combinedGST += productGST;

			productDiscount = (productActualPrice / 100) * product.getDiscount_percentage();
			combinedDiscount += productDiscount;

			productShippingCharge = product.getShipping_charges();
			totalshippingCharges += productShippingCharge;

		}

		totalPrice = actualCombinedPrice + combinedGST - combinedDiscount;

		System.out.println("Actual price : " + actualCombinedPrice);
		System.out.println("GST : " + combinedGST);
		System.out.println("Shipping Charges : " + totalshippingCharges);
		System.out.println("Total price : " + totalPrice);
		// try(PreparedStatement stmt = conn.prepareStatement(query)) {
		//
		// }

		details.setActualCombinedPrice(actualCombinedPrice);
		details.setCombinedGST(combinedGST);
		details.setTotalshippingCharges(totalshippingCharges);
		// details.setShippingGST(shippingGST);
		return details;
	}
}
