package com.sales.controllers;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sales.exceptions.NotCIdException;
import com.sales.exceptions.NotPIdException;
import com.sales.exceptions.NullCIdException;
import com.sales.exceptions.NullPIdException;
import com.sales.exceptions.QtyException;
import com.sales.models.Order;
import com.sales.services.OrderService;

@ControllerAdvice
@Controller
public class OrderController 
{

	@Autowired
	private OrderService orderServ;
	

	@RequestMapping(value = "/showOrders", method = RequestMethod.GET)
	public String showOrder(Model m) 
	{

		ArrayList<Order> orders = orderServ.getAll();

		for (Order o1 : orders) 
		{
			System.out.println("Orderid=" + o1.getoId());
		}//for loop

		m.addAttribute("orders", orders);

		return "displayOrder";
	}//showOrder

	@RequestMapping(value = "/addOrder", method = RequestMethod.GET)
	public String getOrder(@ModelAttribute("order1") Order o, HttpServletRequest h)
	{
		System.out.println("HTTP Request = " + h.getMethod());
		return "addOrder";
	}//getOrder

	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public String postOrder(@Valid @ModelAttribute("order1") Order o,	
	BindingResult result, HttpServletRequest h, Model m) 
	{

		if (result.hasErrors())
		{
			return "addOrder";
		}//if
		else 
		{

			try 
			{
				System.out.println("HTTP Request = " + h.getMethod());
				orderServ.save(o);

				ArrayList<Order> orders = orderServ.getAll();

				//for loop -
				for (Order o1 : orders) 
				{
					System.out.println("Orderid=" + o1.getoId());
				}//for loop

				m.addAttribute("orders", orders);

				return "displayOrder";
			}//try
			catch (NullCIdException | NullPIdException | NotCIdException | NotPIdException | QtyException e)
			{
				e.printStackTrace();
				m.addAttribute("message", e.getMessage());
				m.addAttribute("cid", o.getCust().getcId());
				m.addAttribute("pid", o.getProd().getpId());
				m.addAttribute("Qty", o.getQty());
				return "errorpage1";
			}//catch
			
		}//else
	}//BindingResult
}//OrederController