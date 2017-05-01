package com.sales.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sales.exceptions.*;
import com.sales.models.*;
import com.sales.repositories.*;

@Service
public class OrderService 
{

	@Autowired
	private OrderInterface orderInterface;

	@Autowired
	private ProductInterface productInterface;

	@Autowired
	private CustomerInterface customerInterface;

	private Customer cust;
	private Product prod;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private Date date = new Date();

	public ArrayList<Order> getAll() 
	{

		return (ArrayList<Order>) orderInterface.findAll();
	}//ArrayList getAll()

	public Order save(Order o)
			throws NullCIdException, NullPIdException, NotCIdException, NotPIdException, QtyException 
	{

		cust = customerInterface.findOne(o.getCust().getcId());
		prod = productInterface.findOne(o.getProd().getpId());

		if (cust == null) 
		{
			throw new NullCIdException("No Customer Id entered");
		}//if 
		else if (prod == null) 
		{
			throw new NullPIdException("No Product Id entered");
		}//else if
		else if (o.getCust().getcId() != cust.getcId())
		{
			throw new NotCIdException("Customer Id doestnt exist: " + cust.getcId());
		}//else if
		else if (o.getProd().getpId() != prod.getpId()) 
		{
			throw new NotPIdException("Product Id doestnt exist: " + prod.getpId());
		}//else if
		else if (o.getQty() > prod.getQtyInStock()) 
		{
			throw new QtyException("Not enough stock ");
		}//else if 
		else 
		{

			prod.setQtyInStock(prod.getQtyInStock() - o.getQty());
			o.setOrderDate(dateFormat.format(date));
			o.getCust().setcName(cust.getcName());
			o.getProd().setpDesc(prod.getpDesc());

			orderInterface.save(o);

			return orderInterface.save(o);
		}//else
	}//Order
}//OrderService