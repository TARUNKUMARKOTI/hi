package com.model;

public class PricingDetails {
	private double actualCombinedPrice;
	private double combinedGST;
	private double totalshippingCharges;
	private double shippingGST;
	private double totalPrice;

	public double getActualCombinedPrice() {
		return actualCombinedPrice;
	}

	public void setActualCombinedPrice(double actualCombinedPrice) {
		this.actualCombinedPrice = actualCombinedPrice;
	}

	public double getCombinedGST() {
		return combinedGST;
	}

	public void setCombinedGST(double combinedGST) {
		this.combinedGST = combinedGST;
	}

	public double getTotalshippingCharges() {
		return totalshippingCharges;
	}

	public void setTotalshippingCharges(double totalshippingCharges) {
		this.totalshippingCharges = totalshippingCharges;
	}

	public double getShippingGST() {
		return shippingGST;
	}

	public void setShippingGST(double shippingGST) {
		this.shippingGST = shippingGST;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
