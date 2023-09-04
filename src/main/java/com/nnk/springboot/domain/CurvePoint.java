package com.nnk.springboot.domain;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "curvepoint")
@DynamicUpdate
public class CurvePoint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer curvePointId;
	
	Integer curveId;
	
	Timestamp asOfDate;
	Double term;
	Double value;
	Timestamp creationDate;
	
	
	public CurvePoint() {
		super();
	}
	

	public CurvePoint(Integer curveId, Double term, Double value) {
		super();
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

	public Integer getCurvePointId() {
		return curvePointId;
	}
	public void setCurvePointId(Integer id) {
		this.curvePointId = id;
	}
	public Integer getCurveId() {
		return curveId;
	}
	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}
	public Timestamp getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}
	public Double getTerm() {
		return term;
	}
	public void setTerm(Double term) {
		this.term = term;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

}
