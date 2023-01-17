package com.opl.pharmavector.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CategoryDB")
public class CategoryDB extends Model {

	@Column(name = "pid")
	public String pid;

	@Column(name = "quantity")
	public int quantity;

	@Column(name = "MPO_CODE")
	public String MPO_CODE;

	@Column(name = "AM_PM")
	public String AM_PM;

	@Column(name = "CUST_CODE")
	public String CUST_CODE;

	@Column(name = "DELIVERY_DATE")
	public String DELIVERY_DATE;

	@Column(name = "ORDER_NUMBER")
	public String ORDER_NUMBER;

	@Column(name = "ENTRY_DATE")
	public String ENTRY_DATE;
}
