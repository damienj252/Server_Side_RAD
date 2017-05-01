package com.sales.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.models.Product;
import com.sales.repositories.ProductInterface;

public class ProductService
{
	@Autowired
	private ProductInterface prodInter;
	
	public Product save(Product product)
	{
		return prodInter.save(product);
		
	}//Product save
	
	public ArrayList<Product> getAll()
	{
		return (ArrayList<Product>) prodInter.findAll();
	}//ArrayList getAll()
	
	
}//ProdService
