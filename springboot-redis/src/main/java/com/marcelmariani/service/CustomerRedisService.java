package com.marcelmariani.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.marcelmariani.entity.Customer;
import com.marcelmariani.entity.CustomerRedis;
import com.marcelmariani.repository.CustomerRedisRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@EnableScheduling
public class CustomerRedisService {
	
	private final long MINUTE = 1 * (1000 * 60);

	@Autowired
	private CustomerRedisRepository customerRedisRepository;

	// ModelMapper abstracts getter and setters
	@Autowired
	private ModelMapper modelMapper;
	
	// Instantiate customerService to save information in the relational database
	@Autowired
	private CustomerService customerService;

	public CustomerRedis save(CustomerRedis customerRedis) {
		return customerRedisRepository.save(customerRedis);
	}

	public List<CustomerRedis> findAll() {
		return (List<CustomerRedis>) customerRedisRepository.findAll();
	}

	// Implemented a schedule to synchronize every X minute of data between Redis and the relational database.
	@Scheduled(fixedDelay = MINUTE)
	public void syncCustomerFromRedisToDatabaseRelational() {

		// Get the list of Customers from Redis
		List<CustomerRedis> customerRedisList = findAll();

		// Valid if Customer from Redis is not Null
		if (CollectionUtils.isEmpty(customerRedisList)) {
			log.info("List of Customers empty or null.");
		} else {

			// Save Customer list from Redis to relational database

			// Create a List of Customers and convert Redis information to a Relational database
			List<Customer> customerList = new ArrayList<>();
			customerRedisList.stream().forEach(customerRedis -> {

				log.info(customerRedis);

				// For each Customer stored in Redis will be create a Customer at List
				Customer customer = modelMapper.map(customerRedis, Customer.class);
				customerList.add(customer);
			});
			
			customerService.saveAll(customerList);
			customerRedisRepository.deleteAll(customerRedisList);
		}
	}

}
