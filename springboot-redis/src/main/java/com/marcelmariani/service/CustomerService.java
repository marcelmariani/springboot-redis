package com.marcelmariani.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcelmariani.entity.Customer;
import com.marcelmariani.repository.CustomerRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> findAll() {

		log.info("Finding All Person!");
		return customerRepository.findAll();
	}
	
	public void saveAll(List<Customer> customerList) {
		customerRepository.saveAll(customerList);
	};

}
