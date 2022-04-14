package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.Product;

public class ProductServiceImpl implements ProductService {
	private ProductDAO productDAO;
	
	public ProductServiceImpl(){
		productDAO=new ProductDAO(); //생성함과 동시에 productDAO 생성
	}
	
	public void addProduct(Product product) throws Exception{
		productDAO.insertProduct(product);
	}

	public Product getProduct(int no) throws Exception{
		return productDAO.findProduct(no);
	}
	
	public Map<String, Object> getProductList(Search search) throws Exception{
		return productDAO.getProductList(search);
	}
	
	public void updateProduct(Product product) throws Exception{
		productDAO.updateProduct(product);
	}
	
	public int getProductNo(String name) throws Exception{
		return productDAO.findProductNo(name);
	}
	
}
