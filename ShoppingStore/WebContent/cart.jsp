<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.Product" %>
<%@ page import="com.controller.ProductServlet" %>
<%@ page import="com.controller.CartServlet" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            display:flex;
            justify-content:space-around;
            align-items: flex-start;
        }

        .container {
            width: 40%;
            
            margin-left:20px;
            margin-top:20px;
            marign-bottom:20px;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
		
		.checkout-container {
			width:20%;
            float:right;
            float:top;
            margin-right: 20px;
            margin-top:20px;
            marign-bottom:20px;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #333;
            text-align: center;
        }

        .product {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        .product-info {
            flex: 1;
            margin-right: 20px;
        }

        .product-image {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 5px;
            margin-right: 20px;
        }

        .product-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .product-brand {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .product-price {
            color: #007bff;
            font-size: 16px;
        }

        .remove-from-cart {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
        }
        .checkout {
        	background-color: #007bff;
            color: #fff;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
        }

        .search {
            margin-bottom: 20px;
        }

        .search-input {
            padding: 8px;
            width: 200px;
            border-radius: 5px;
            border: 1px solid #ddd;
        }

        .search-button {
            padding: 8px 15px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
         .search-select {
        padding: 8px;
        width: 200px;
        border-radius: 5px;
        border: 1px solid #ddd;
        appearance: none; /* Remove default arrow icon */
        background-image: url('data:image/svg+xml;utf8,<svg fill="#000000" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path d="M7 10l5 5 5-5z" /></svg>'); /* Custom arrow icon */
        background-repeat: no-repeat;
        background-position: right 8px center; /* Position arrow icon */
        background-size: 16px 16px; /* Size of the arrow icon */
    }
    #pincode {
    margin-bottom:10px;
    }
    </style>
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script type="text/javascript">
    
    $(document).ready(function() {
        // Attach change event handler to category dropdown
    	$('.remove-from-cart').click(function() {
            // Get the product ID from the data-product-id attribute
            const productId = $(this).data('product-id');
            // Make an AJAX call to the addToCart servlet
            $.ajax({
                url: 'removeFromCart',
                method: 'POST',
                data: { productId: productId },
                success: function(response) {
                    console.log('Product removed from cart successfully.');
                    location.reload();
                },
                error: function(xhr, status, error) {
                    console.error('Failed to add product to cart:', error);
                }
            });
        });
        
        
    	var checkoutButton = $("#checkoutButton");
        var priceHeader = $("#priceHeader");
        var shippingHeader = $("#shippingHeader");
        var totalPriceHeader = $("#totalPriceHeader");
        var checkoutForm = $("#checkoutForm");

        checkoutButton.click(function() {
            if (priceHeader.is(":hidden")) {
                // Show the hidden content
                priceHeader.show();
                shippingHeader.show();
                totalPriceHeader.show();
                checkoutForm.show();
                // Call priceCalculation servlet using AJAX
                $.ajax({
                    type: "GET",
                    url: "/priceCalculationServlet",
                    success: function(data) {
                        // Handle success response if needed
                        console.log("Price calculation successful");
                    },
                    error: function(xhr, status, error) {
                        // Handle error response if needed
                        console.error("Error occurred:", status, error);
                    }
                });
            } else {
                // Submit the form
                checkoutForm.submit();
            }
        });
    });
    </script>
</head>
<body>
    <div class="container">
        <h1>Shopping Cart</h1>
		<%double checkoutPrice=0; %>
		
		
        <!-- Product List -->
        <div id="productList" class="product-list">
        
    <%
    List<Product> products = (List<Product>) session.getAttribute("cartItems");
    if (products != null) {
        for (Product product : products) {
%>
    <div class="product">
        <div class="product-info">
            <img src="<%= product.getImage() %>" alt="Product Image" class="product-image">
            <div class="product-title"><%= product.getTitle() %></div>
            <div class="product-brand"><%= product.getProd_brand() %></div>
            <div class="product-price">$<%= product.getPrice() %></div>
            <%checkoutPrice+=product.getPrice();%>
        </div>
        <button class="remove-from-cart" data-product-id="<%= product.getId() %>">Remove from Cart</button>
    </div>
<%
        }
    }
%>

        </div>
        
    </div>
    
    <div class="checkout-container">
    <!-- Initially hidden content -->
    <h4 id="priceHeader" style="display: none;">Price : <%= checkoutPrice %></h4>
    <% double shippingCharges = 0; %>
    <h4 id="shippingHeader" style="display: none;">Shipping Charges : <%= shippingCharges %></h4>
    <% double totalPrice = checkoutPrice + shippingCharges; %>
    <h4 id="totalPriceHeader" style="display: none;">Total Price : <%= totalPrice %></h4>
    <form id="checkoutForm" style="display: none;">
        <input placeholder="Enter PinCode" id="pincode" type="text" name="pincode" />
    </form>
    <button id="checkoutButton" class="checkout">Checkout</button>
</div>


    
    
</body>
</html>
