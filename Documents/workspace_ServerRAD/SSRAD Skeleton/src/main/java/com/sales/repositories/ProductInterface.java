package com.sales.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sales.models.Product;

public interface ProductInterface extends CrudRepository<Product, Long>
{

}//ProductInterface
