package com.opl.pharmavector;

public class Customer {
	
	private int id;
	private String name;
	private String name2;
	
	public Customer(){}
	
	public Customer(int id, String name){
		this.id = id;
		this.name = name;
	}

	public Customer(String name, String name2){
		this.name = name;
		this.name2 = name2;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public void setName2(String name2){
		this.name2 = name2;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	public String getName2(){
		return this.name2;
	}

}