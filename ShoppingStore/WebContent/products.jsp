<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.Product" %>
<%@ page import="com.controller.ProductServlet" %>
<!DOCTYPE html>
<html>
<head>
    <title>Online Shopping Store</title>
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
            width: 60%;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .cart-container {
            width: 20%;
            margin-right: 20px;
            margin-top:20px;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
            text-align: center;
        }

		.cart-icon {
			width:40px;
			height:40px;
			margin-right: 20px;
            margin-top:20px;
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

        .add-to-cart {
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
    </style>
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script type="text/javascript">
    
    $(document).ready(function() {
        // Attach change event handler to category dropdown
        $('#category').change(function() {
            // Get selected category value
            var category = $(this).val();
            console.log("category : "+category);
            // Make AJAX call to servlet to fetch products based on category
            $.ajax({
            	type:"POST",
            	url:"category",
            	data:{category:category},
            	success:function(response){
            		 $('#productList').html(response);
            		console.log(response);
            	},
            	error:function(xhr,status,error){
            		 console.error('Error occurred while fetching products:', error);
            	}
            });
        });
        
        
        $('#priceOrder').change(function() {
            // Get selected category value
            var category = $('#category').val();
            var priceorder = $(this).val();
            // Make AJAX call to servlet to fetch products based on category
            $.ajax({
            	type:"POST",
            	url:"priceorder",
            	data:{category:category, priceorder:priceorder},
            	success:function(response){
            		$('#productList').html(response);
            		console.log(response);
            	},
            	error:function(xhr,status,error){
            		 console.error('Error occurred while fetching products:', error);
            	}
            });
        });
        
        
        $('.add-to-cart').click(function() {
            // Get the product ID from the data-product-id attribute
            const productId = $(this).data('product-id');
            // Make an AJAX call to the addToCart servlet
            $.ajax({
                url: 'addToCart',
                method: 'POST',
                data: { productId: productId },
                success: function(response) {
                    console.log('Product added to cart successfully.');
                },
                error: function(xhr, status, error) {
                    console.error('Failed to add product to cart:', error);
                }
            });
        });
    });
    
    function addToCart(productId){
    	$.ajax({
            url: 'addToCart',
            method: 'POST',
            data: { productId: productId },
            success: function(response) {
                console.log('Product added to cart successfully.');
            },
            error: function(xhr, status, error) {
                console.error('Failed to add product to cart:', error);
            }
        });
    }
    
    </script>
</head>
<body>
    <div class="container">
        <h1>Online Shopping Store</h1>

        <!-- Search Options -->
       <div class="search">
        <select id="category" name="category" class="search-select">
            <option value="" selected disabled>Select Category</option>
            <option value="1">Electronics</option>
            <option value="2">Clothes</option>
            <option value="3">Gadgets</option>
        </select>
        <select id="priceOrder" name="priceOrder" class="search-select">
            <option value="" selected disabled>Sort by Price</option>
            <option value="1">Price: Low to High</option>
            <option value="2">Price: High to Low</option>
        </select>
</div>
        <!-- Product List -->
        <div id="productList" class="product-list">
    <%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products != null) {
        for (Product product : products) {
%>
    <div class="product">
        <div class="product-info">
            <img src="<%= product.getImage() %>" alt="Product Image" class="product-image">
            <div class="product-title"><%= product.getTitle() %></div>
            <div class="product-brand"><%= product.getProd_brand() %></div>
            <div class="product-price">$<%= product.getPrice() %></div>
        </div>
        <button class="add-to-cart" data-product-id="<%= product.getId() %>">Add to Cart</button>
    </div>
<%
        }
    }
%>

        </div>
    </div>
    <div>
    <a href="cart">
    <img class="cart-icon" alt="" src="https://static.vecteezy.com/system/resources/previews/000/356/583/original/vector-shopping-cart-icon.jpg">
    </a>
    </div>
</body>
</html>
