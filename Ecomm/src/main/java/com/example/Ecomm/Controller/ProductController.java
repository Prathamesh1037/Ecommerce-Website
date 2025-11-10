package com.example.Ecomm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Ecomm.Entity.Product;
import com.example.Ecomm.Service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
  @Autowired
  private ProductService productService;
  
  public List<Product> getAllProducts(){
	  return productService.getAllProducts();
  }
  @GetMapping("/{id}")
  public Product getProductById(@PathVariable Long id) {
	  return productService.getProductById(id);
  }
  
  @PostMapping
  public Product addProduct(@RequestBody Product product) {
	return productService.addProduct(product); 
}
  
  public void deleteProduct(@PathVariable long id) {
	  productService.deleteProduct(id);
  }
}