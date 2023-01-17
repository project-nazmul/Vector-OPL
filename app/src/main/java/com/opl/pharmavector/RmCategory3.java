package com.opl.pharmavector;

public class RmCategory3 {
	private String sl;
	private String id;
	private String name;
	private String quantity;
	private String PROD_RATE;
	private String PROD_VAT;
	private int array_length;
	private String PROD_VAT_2;
	private String PROD_VAT_3;
	private String PROD_VAT_4;
	
	public RmCategory3(String sl, String id, String name,String quantity,String PROD_RATE,String PROD_VAT){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
		
		//this.array_length = array_length;
	}



	public RmCategory3(String sl, String id, String name,String quantity,String PROD_RATE,String PROD_VAT,String PROD_VAT_2){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
		this.PROD_VAT_2 = PROD_VAT_2;
		//this.array_length = array_length;
	}





	public RmCategory3(String sl, String id, String name,String quantity,String PROD_RATE,String PROD_VAT,String PROD_VAT_2,String PROD_VAT_3,String PROD_VAT_4){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
		this.PROD_VAT_2 = PROD_VAT_2;
		this.PROD_VAT_3 = PROD_VAT_3;
		this.PROD_VAT_4= PROD_VAT_4;
		//this.array_length = array_length;
	}







	public void setsl(String sl){
		this.sl = sl;
	}
	
	
	
	public void setId(String id){
		this.id = id;
	}
	public void setName(String name){this.name = name;}
	public void setQuantity(String quantity){
		 this.quantity=quantity;
	}
	

	
	
	
	public void setPROD_RATE(String PROD_RATE){
		 this.PROD_RATE=PROD_RATE;
	}	
	
	public void setPROD_VAT(String PROD_VAT){
		 this.PROD_VAT=PROD_VAT;
	}	
	
	
	
	
	
	
	
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
	
	public String getQuantity(){
		return this.quantity;
	}
	
	
	public String getPROD_RATE(){
		return this.PROD_RATE;
	}
	public String getPROD_VAT(){
		return this.PROD_VAT;
	}


	public String getPROD_VAT_2(){
		return this.PROD_VAT_2;
	}


	public String getPROD_VAT_3(){
		return this.PROD_VAT_3;
	}

	public String getPROD_VAT_4(){
		return this.PROD_VAT_4;
	}
	
	
	/*
	
		public int getArray_length(){
		return this.array_length;
	}
	*/
	
	
	
}
