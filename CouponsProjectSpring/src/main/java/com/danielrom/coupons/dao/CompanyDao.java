package com.danielrom.coupons.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.danielrom.coupons.entities.CompanyEntity;
import com.danielrom.coupons.exceptions.ApplicationException;

@Repository
public class CompanyDao {
	
	@PersistenceContext(unitName="CouponSpringUnit")
	private EntityManager entityManager;

// ---------------------------------------Create a new company-----------------------------
	
	@Transactional(propagation=Propagation.REQUIRED)
	public long createCompany(CompanyEntity company){
		
		entityManager.persist(company);
		
		return company.getId();
	}
	
// ------------------------------remove a company--------------------------------------

	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCompany(long id) throws ApplicationException {
		
		CompanyEntity company = entityManager.find(CompanyEntity.class, id);
		
		entityManager.remove(company);
	}

// ----------------------------------Update a company--------------------------------------
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCompany(CompanyEntity company) {
		
		entityManager.merge(company);
	}
	
// -------------------------------------Getters----------------------------------------------

	// If the company doesn't exist, returns null
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompany(long id) throws ApplicationException {
		
		CompanyEntity company = entityManager.find(CompanyEntity.class, id);
		
		return company;
	}
	
	// If the company doesn't exist, returns null
	@Transactional(propagation=Propagation.REQUIRED)
	public CompanyEntity getCompany(String companyName) throws ApplicationException {
		
		Query query = entityManager.createQuery("FROM CompanyEntity company WHERE company.companyName=:companyName").setParameter("companyName", companyName);
		
		CompanyEntity company;
		
		List results = query.getResultList();
		
		if(results.isEmpty()) {
			company = null;
		} else {
			company = (CompanyEntity) query.getSingleResult();
		}
		
		return company;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CompanyEntity> getAllCompanies() throws ApplicationException {
		
		Query query = entityManager.createQuery("FROM CompanyEntity");
	
		@SuppressWarnings("unchecked")
		List<CompanyEntity> allCompanies = query.getResultList();
		
		return allCompanies;
	}

// ------------------------------------------------------------------------------------------
}
