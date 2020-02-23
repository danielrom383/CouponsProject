package com.danielrom.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danielrom.coupons.entities.CouponEntity;
import com.danielrom.coupons.exceptions.ApplicationException;
import com.danielrom.coupons.logic.CouponController;


@RestController
@RequestMapping(value = "/loggedin/coupons")
public class CouponApi {

	@Autowired
	private CouponController couponController;

	// ------------------------------------Creates a new coupon---------------------------------

	@PostMapping
	public void createCoupon (@RequestBody CouponEntity coupon) throws ApplicationException {

		couponController.createCoupon(coupon);
	}

	// ------------------------------------Removes a coupon---------------------------------

	@DeleteMapping("/{id}")
	public void removeCoupon (@PathVariable long id) throws ApplicationException {

		couponController.removeCoupon(id);
	}

	// ------------------------------Updates a coupon---------------------------------

	@PutMapping
	public void updateCoupon (@RequestBody CouponEntity coupon) throws ApplicationException {

		couponController.updateCoupon(coupon);
	}

	// ------------------------------------Getters---------------------------------

	@GetMapping("/{id}")
	public CouponEntity getCoupon (@PathVariable long id) throws ApplicationException {
		return couponController.getCoupon(id);
	}

	@GetMapping("/by-title")
	public CouponEntity getCoupon (@RequestParam String title) throws ApplicationException {
		return couponController.getCoupon(title);
	}

	@GetMapping("all-coupons")
	public List <CouponEntity> getAllCoupons () throws ApplicationException {

		return couponController.getAllCoupons();
	}
	
	@GetMapping("	")
	public List<CouponEntity> getCompanyCoupons (@RequestParam long companyId) throws ApplicationException {
		
		return couponController.getCompanyCoupons(companyId);
	}
}
