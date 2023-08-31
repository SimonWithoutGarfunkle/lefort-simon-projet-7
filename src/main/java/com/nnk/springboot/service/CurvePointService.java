package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	private static Logger logger = LoggerFactory.getLogger(CurvePointService.class);
	
	/**
	 * Extract all CurvePoint from the Database
	 * 
	 * @return List of all CurvePoints from the Databse
	 */
	public List<CurvePoint> getAllCurvePoints() {
		logger.info("call getAllRule");
		return curvePointRepository.findAll();
	}

	
	/**
	 * Convert Optional<CurvePoint> to CurvePoint
	 * 
	 * @param option to convert
	 * @return CurvePoint or null
	 */
	public CurvePoint convertOptionalToCurvePoint(Optional<CurvePoint> option) {
		logger.info("call convertOptionalToCurvePoint");
		return option.orElse(null);
		
	}

	
	/**
	 * Extract the CurvePoint corresponding to the Id from DB
	 * 
	 * @param id of the CurvePoint
	 * @return CurvePoint
	 */
	public CurvePoint getCurvePointById(Integer id) {
		logger.info("call getCurvePointById");		
		return convertOptionalToCurvePoint(curvePointRepository.findById(id));		
	}
	
	/**
	 * Add CurvePoint to DB
	 * 
	 * @param curvePoint
	 * @return saved curvePoint
	 */
	public CurvePoint addCurvePoint(CurvePoint curvePoint) {
		logger.info("call addCurvePoint");	
		return curvePointRepository.save(curvePoint);
	}
	
	
	/**
	 * Update an existing CurvePoint
	 * 
	 * @param CurvePoint
	 * @return updated CurvePoint
	 */
	public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
		logger.info("call updateCurvePoint");	
		return curvePointRepository.save(curvePoint);
	}
	
	/**
	 * Delete the CurvePoint corresponding to the id
	 * 
	 * @param id of the CurvePoint to delete
	 */
	public void deleteCurvePoint(Integer id) {
		logger.info("call deleteCurvePoint");	
		curvePointRepository.deleteById(id);		
	}
		

}
