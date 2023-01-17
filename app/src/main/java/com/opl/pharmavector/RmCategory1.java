package com.opl.pharmavector;

public class RmCategory1 {
	private String sl;
	private String id;
	private String name;

	private int array_length;
	

	
	public RmCategory1(String sl, String id, String name){
		this.sl=sl;
		this.id = id;
		this.name = name;

		
		//this.array_length = array_length;
	}
	
	
	public void setsl(String sl){
		this.sl = sl;
	}
	
	
	
	public void setId(String id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}

	/*
	
	public void setQuantity(int quantity){
		 this.quantity=quantity;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	
	public void setPROD_RATE(String PROD_RATE){
		 this.PROD_RATE=PROD_RATE;
	}	
	
	public void setPROD_VAT(String PROD_VAT){
		 this.PROD_VAT=PROD_VAT;
	}	
	
	
	*/
	
	
	
	
	/*
	
	
		public void setArray_length(int array_length){
		this.array_length = array_length;
	}
	*/
	public String getsl() {
		// TODO Auto-generated method stub
		return this.sl;
	}
		
	
	public String getId(){
		return this.id;
	}
	
	
	public String getName(){
		return this.name;
	}

	/*
	
	public int getQuantity(){
		return this.quantity;
	}
	
	
	public String getPROD_RATE(){
		return this.PROD_RATE;
	}
	public String getPROD_VAT(){
		return this.PROD_VAT;
	}


	*/
	
	
	
	
	
	
	/*
	
		public int getArray_length(){
		return this.array_length;
	}
	*/
	
	
	
}
