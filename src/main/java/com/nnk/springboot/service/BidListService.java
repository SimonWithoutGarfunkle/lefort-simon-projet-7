package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

/**
 * Implementation the CRUD for the BidList Entity
 * 
 * @author Simon
 *
 */
@Service
public class BidListService {
	
	@Autowired
	private BidListRepository bidListRepository;
	
	private static Logger logger = LoggerFactory.getLogger(BidListService.class);
	
	/**
	 * Extract all BidList from the Database
	 * 
	 * @return List of all BidLists from the Databse
	 */
	public List<BidList> getAllBidLists() {
		logger.info("call getAllRule");
		return bidListRepository.findAll();
	}

	
	/**
	 * Convert Optional BidList to BidList
	 * 
	 * @param option to convert
	 * @return BidList or null
	 */
	public BidList convertOptionalToBidList(Optional<BidList> option) {
		logger.info("call convertOptionalToBidList");
		return option.orElse(null);
		
	}

	
	/**
	 * Extract the BidList corresponding to the Id from DB
	 * 
	 * @param id of the BidList
	 * @return BidList
	 */
	public BidList getBidListById(Integer id) {
		logger.info("call getBidListById");		
		return convertOptionalToBidList(bidListRepository.findById(id));		
	}
	
	/**
	 * Add BidList to DB
	 * 
	 * @param bidList to add
	 * @return saved bidList
	 */
	public BidList addBidList(BidList bidList) {
		logger.info("call addBidList");	
		return bidListRepository.save(bidList);
	}
	
	
	/**
	 * Update an existing BidList
	 * 
	 * @param bidList to update
	 * @return updated BidList
	 */
	public BidList updateBidList(BidList bidList) {
		logger.info("call updateBidList");	
		return bidListRepository.save(bidList);
	}
	
	/**
	 * Delete the BidList corresponding to the id
	 * 
	 * @param id of the BidList to delete
	 */
	public void deleteBidList(Integer id) {
		logger.info("call deleteBidList");	
		bidListRepository.deleteById(id);		
	}

}
