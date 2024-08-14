package com.marcelmariani.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelmariani.entity.Customer;
import com.marcelmariani.service.CustomerService;

@RestController
@RequestMapping("/v1/customer")
public class CustomerControllerV1 {

	@Autowired
	private CustomerService customerService;

	@GetMapping()
	public List<Customer> findAll() {
		return customerService.findAll();
	}
}
