package com.model;

public class Product {

	private int id;
	private String title;
	private double price;
	private String image;
	private String prod_brand;
	private int hsnc_id;
	private int ct_id;
	private int shipping_charges;
	private int discount_percentage;

	public int getHsnc_id() {
		return hsnc_id;
	}

	public void setHsnc_id(int hsnc_id) {
		this.hsnc_id = hsnc_id;
	}

	public int getCt_id() {
		return ct_id;
	}

	public void setCt_id(int ct_id) {
		this.ct_id = ct_id;
	}

	public int getShipping_charges() {
		return shipping_charges;
	}

	public void setShipping_charges(int shipping_charges) {
		this.shipping_charges = shipping_charges;
	}

	public int getDiscount_percentage() {
		return discount_percentage;
	}

	public void setDiscount_percentage(int discount_percentage) {
		this.discount_percentage = discount_percentage;
	}

	public String getProd_brand() {
		return prod_brand;
	}

	public void setProd_brand(String prod_brand) {
		this.prod_brand = prod_brand;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
