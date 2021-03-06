package com.danielrom.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.danielrom.coupons.dao.CouponDao;
import com.danielrom.coupons.dao.CustomerDao;
import com.danielrom.coupons.dao.PurchaseDao;
import com.danielrom.coupons.entities.CouponEntity;
import com.danielrom.coupons.entities.PurchaseEntity;
import com.danielrom.coupons.enums.ErrorType;
import com.danielrom.coupons.exceptions.ApplicationException;

@Controller
public class PurchaseController {

	@Autowired
	private PurchaseDao purchaseDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CouponDao couponDao;

	// ------------------------------------Creates a purchase-------------------------------------

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
	public long createPurchase (PurchaseEntity purchase) throws ApplicationException {

		long purchaseCouponId = purchase.getCoupon().getId();
		
		if (purchase.getCustomer() == null || customerDao.getCustomer(purchase.getCustomer().getUserLoginDetails().getId()) == null) {
			throw new ApplicationException (ErrorType.WORNG_INPUT, "A purchase must include an existing customer");
		}

		if (purchase.getCoupon() == null || couponDao.getCoupon(purchase.getCoupon().getId()) == null) {
			throw new ApplicationException (ErrorType.WORNG_INPUT, "A purchase must include an existing coupon");
		}

		CouponEntity purchaseCoupon = couponDao.getCoupon(purchaseCouponId);

		if (purchaseCoupon.getAmount() <= purchase.getAmount()) {
			throw new ApplicationException (ErrorType.OUT_OF_STOCK_OR_EXPIRED, "Sorry, the amount you're trying to buy is out of stock");
		}
		
		// Reducing coupon amount by the amount purchased (Going straight to couponDao since the coupon is coming from the database)
		purchaseCoupon.setAmount(purchaseCoupon.getAmount() - purchase.getAmount());
		couponDao.updateCoupon(purchaseCoupon);
		
		long autoGeneratedId = purchaseDao.createPurchase(purchase);

		return autoGeneratedId;
	}

	// ------------------------------Updates purchase---------------------------------

	public void updatePurchase (PurchaseEntity purchase) throws ApplicationException {

		if (getPurchase(purchase.getId()) == null) {
			throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "The purchase you're trying to update doesn't exist");
		}

		purchaseDao.updatePurchase(purchase);
	}

	// ------------------------------------Removes a purchase---------------------------------

	public void removePurchase (long id) throws ApplicationException {

		PurchaseEntity purchase = getPurchase(id);

		if (purchase == null) {
			throw new ApplicationException(ErrorType.DATA_NOT_FOUND, "The purchase you're trying to remove doesn't exist");
		}
		
		purchaseDao.removePurchase(id);
	}

	// ------------------------------------Getters-------------------------------------

	public PurchaseEntity getPurchase(long id) throws ApplicationException {

		return purchaseDao.getPurchase(id);
	}
	
	public List<PurchaseEntity> getCustomerPurchases (long customerId) throws ApplicationException {

		if (customerDao.getCustomer(customerId) == null) {
			throw new ApplicationException (ErrorType.WORNG_INPUT, "The customer whose purchases you're trying to get doesn't exist");
		}
		
		return purchaseDao.getCustomerPurchases(customerId);
	}
}
