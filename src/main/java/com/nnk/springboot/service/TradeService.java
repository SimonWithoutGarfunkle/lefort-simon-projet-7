package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * Implementation the CRUD for the Trade Entity
 * 
 * @author Simon
 *
 */
@Service
public class TradeService {
	
	@Autowired
	private TradeRepository tradeRepository;
	
	private static Logger logger = LoggerFactory.getLogger(TradeService.class);
	
	/**
	 * Extract all Trade from the Database
	 * 
	 * @return List of all Trades from the Databse
	 */
	public List<Trade> getAllTrades() {
		logger.info("call getAllRule");
		return tradeRepository.findAll();
	}

	
	/**
	 * Convert Optional Trade to Trade
	 * 
	 * @param option to convert
	 * @return Trade or null
	 */
	public Trade convertOptionalToTrade(Optional<Trade> option) {
		logger.info("call convertOptionalToTrade");
		return option.orElse(null);
		
	}

	
	/**
	 * Extract the Trade corresponding to the Id from DB
	 * 
	 * @param id of the Trade
	 * @return Trade
	 */
	public Trade getTradeById(Integer id) {
		logger.info("call getTradeById");		
		return convertOptionalToTrade(tradeRepository.findById(id));		
	}
	
	/**
	 * Add Trade to DB
	 * 
	 * @param trade to add
	 * @return saved trade
	 */
	public Trade addTrade(Trade trade) {
		logger.info("call addTrade");	
		return tradeRepository.save(trade);
	}
	
	
	/**
	 * Update an existing Trade
	 * 
	 * @param trade to update
	 * @return updated Trade
	 */
	public Trade updateTrade(Trade trade) {
		logger.info("call updateTrade");	
		return tradeRepository.save(trade);
	}
	
	/**
	 * Delete the Trade corresponding to the id
	 * 
	 * @param id of the Trade to delete
	 */
	public void deleteTrade(Integer id) {
		logger.info("call deleteTrade");	
		tradeRepository.deleteById(id);		
	}

}
