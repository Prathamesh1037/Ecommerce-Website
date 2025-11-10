package com.example.Ecomm.Entity;

import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class OrderRequest {
	
	 
	  private Map<Long,Integer> productQuantities;
	  
	  private double totalAmount;


	  public Map<Long, Integer> getProductQuantities() {
		  return productQuantities;
	  }

	  public void setProductQuantities(Map<Long, Integer> productQuantities) {
		  this.productQuantities = productQuantities;
	  }

	  public double getTotalAmount() {
		  return totalAmount;
	  }

	  public void setTotalAmount(double totalAmount) {
		  this.totalAmount = totalAmount;
	  }
	  
	  
}
