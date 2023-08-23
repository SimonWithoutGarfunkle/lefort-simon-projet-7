package com.nnk.springboot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rulename")
public class RuleName {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	String name;
	String description;
	String json;
	String template;
	String sqlStr;
	String sqlPart;

}
