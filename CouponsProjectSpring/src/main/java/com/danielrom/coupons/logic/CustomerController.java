package com.danielrom.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.danielrom.coupons.dao.CustomerDao;
import com.danielrom.coupons.entities.CustomerEntity;
import com.danielrom.coupons.enums.ErrorType;
import com.danielrom.coupons.exceptions.ApplicationException;
import com.danielrom.coupons.utilities.InputValidationUtils;

@Controller
public class CustomerController {

	@Autowired
	private CustomerDao customerDao;

	// ------------------------------------Creates a customer-------------------------------------

	public long createCustomer (CustomerEntity customer) throws ApplicationException {

		if (!InputValidationUtils.isNameValid(customer.getCustomerName())) {
			throw new ApplicationException (ErrorType.WORNG_INPUT, "The name you inserted is invalid");
		}

		long autoGeneratedId = customerDao.createCustomer(customer);

		return autoGeneratedId;
	}

	// ------------------------------------Removes a customer---------------------------------

	public void removeCustomer (long id) throws ApplicationException {
		
		CustomerEntity customer = getCustomer(id);

		if (customer == null) {
			throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "The customer you're trying to remove doesn't exist");
		}
		
		customerDao.removeCustomer(id);
	}

	// ------------------------------Update customer---------------------------------

	public void updateCustomer (CustomerEntity customer) throws ApplicationException {

		if (customerDao.getCustomer(customer.getUserLoginDetails().getEmail()) == null) {
			throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "The customer you're trying to update couldn't be found.");
		}

		customerDao.updateCustomer(customer);
	}

	// ------------------------------------Getters-------------------------------------

	public CustomerEntity getCustomer(long id) throws ApplicationException {

		return customerDao.getCustomer(id);
	}

	public CustomerEntity getCustomer(String email) throws ApplicationException {

		return customerDao.getCustomer(email);
	}

	public List <CustomerEntity> getAllCustomers () throws ApplicationException {

		List <CustomerEntity> allCustomers = customerDao.getAllCustomers();

		if (allCustomers == null) {
			throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "No customers could be found.");
		}

		return allCustomers;
	}
}