package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	Integer curveId;
	Timestamp asOfDate;
	Double term;
	Double value;
	Timestamp creationDate;
}
