package com.opl.pharmavector;

public class AmCustomer {
	
	private int id;
	private String name;
	
	public AmCustomer(){

	}
	
	public AmCustomer(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}

}