
package com.danielrom.coupons.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.danielrom.coupons.entities.CouponEntity;
import com.danielrom.coupons.exceptions.ApplicationException;

@Repository
public class CouponDao {

	@PersistenceContext(unitName="CouponSpringUnit")
	private EntityManager entityManager;
	
// ------------------------------Create a new coupon-----------------------------

	@Transactional(propagation=Propagation.REQUIRED)
	public long createCoupon (CouponEntity coupon) throws ApplicationException {
		
		entityManager.persist(coupon);
		
		return coupon.getId();
	}
	
// ------------------------------Remove a coupon(s)---------------------------------

	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCoupon (long id) throws ApplicationException {
		
		CouponEntity coupon = entityManager.find(CouponEntity.class, id);
		
		entityManager.remove(coupon);
	}

// ------------------------------Update a coupon---------------------------------
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCoupon (CouponEntity coupon) throws ApplicationException {
		
		entityManager.merge(coupon);
		
	}

// -------------------------------------Getters-----------------------------------
	
	// If the coupon doesn't exist, returns null
	@Transactional(propagation=Propagation.REQUIRED)
	public CouponEntity getCoupon (long id) throws ApplicationException {
		
		CouponEntity coupon = entityManager.find(CouponEntity.class, id);
		
		return coupon;
	}
	
	// If the coupon doesn't exist, returns null
	@Transactional(propagation=Propagation.REQUIRED)
	public CouponEntity getCoupon (String title) throws ApplicationException {
		System.out.println(title);
		Query query = entityManager.createQuery("FROM CouponEntity coupon WHERE coupon.title=:couponTitle").setParameter("couponTitle", title);
		
		CouponEntity coupon; 
		
		List results = query.getResultList();

		if(results.isEmpty()) {
			coupon = null;
		} else {
			coupon = (CouponEntity) query.getSingleResult();
		}
		
		return coupon;
	}

	// Gets all the coupons that exist in the database
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getAllCoupons() throws ApplicationException {
		Query query = entityManager.createQuery("FROM CouponEntity");
		
		@SuppressWarnings("unchecked")
		List<CouponEntity> allCoupons = query.getResultList();
		
		return allCoupons;
	}
	
	// Gets all the coupons that were made by a specific company
	@Transactional(propagation = Propagation.REQUIRED)
	public List <CouponEntity> getCompanyCoupons (long companyId) throws ApplicationException {
		Query query = entityManager.createQuery("FROM CouponEntity coupon WHERE coupon.company.id=:companyId").setParameter("companyId", companyId);
		
		@SuppressWarnings("unchecked")
		List<CouponEntity> allCoupons = query.getResultList();
		
		return allCoupons;
	}

}
