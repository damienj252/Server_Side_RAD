package com.sales.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sales.models.Customer;
import com.sales.models.Order;
import com.sales.models.Product;
import com.sales.services.CustomerService;
import com.sales.services.OrderService;
@Controller
public class CustomerController
{
	@Autowired
	private CustomerService custServ;
	
	@RequestMapping (value = "/addCustomer", method = RequestMethod.GET)
	public String getProduct(@ModelAttribute ("cust1")Customer c, HttpServletRequest h)
	{
		System.out.println("Http Request = " + h.getMethod());
		return "addCustomer";
	}//getProduct
	
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	public String postProduct(@Valid @ModelAttribute("customer1") Customer c, BindingResult result,
			HttpServletRequest h, Model m) 
	{

		if (result.hasErrors())
		{

			return "addCustomer";

		}//if 
		else 
		{
			System.out.println("HTTP Request = " + h.getMethod());

			custServ.save(c);

			ArrayList<Customer> customers = custServ.getAll();

			for (Customer c1 : customers)
			{
				System.out.println("Cid=" + c1.getcId());
				for (Order o1 : c1.getOrders())
				{
					System.out.println("Oid=" + o1.getoId());
				}//for loop
			}//for loop

			m.addAttribute("customers", customers);

			return "displayCustomer";
		}//else
	}//postProdcut

	@RequestMapping(value = "/showCustomers", method = RequestMethod.GET)
	public String getCustomers(Model m) 
	{

		ArrayList<Customer> customers = custServ.getAll();

		for (Customer c1 : customers)
		{
			System.out.println("Cid=" + c1.getcId());
			for (Order o1 : c1.getOrders()) 
			{
				System.out.println("Oid=" + o1.getoId());
			}//for loop
		}//for loop

		m.addAttribute("customers", customers);

		return "displayCustomer";
	}//getCustomer
/*
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null)
		{
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}//
*/
}//CustomerController
