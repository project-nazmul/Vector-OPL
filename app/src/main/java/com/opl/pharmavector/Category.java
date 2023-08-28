package com.opl.pharmavector;

public class Category {
	private String sl;
	private String id;
	private String name;
	private int quantity;
	private String PROD_RATE;
	private String PROD_VAT;
	private int array_length;
	private String PPM_CODE;
	private String P_CODE;
	private String SHIFT_CODE;
	private String PACK_CODE;
	private String TOTAL_CODE;
	private String FF_NAME;

	public Category(String sl, String id, String name,int quantity,String PROD_RATE,String PROD_VAT,String PPM_CODE,String P_CODE,String SHIFT_CODE){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
		this.PPM_CODE =  PPM_CODE;
		this.P_CODE =  P_CODE;
		this.SHIFT_CODE =  SHIFT_CODE;
		//this.array_length = array_length;
	}

	public Category(String sl, String id, String name,int quantity,String PROD_RATE,String PROD_VAT,String PPM_CODE,String P_CODE){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
		this.PPM_CODE =  PPM_CODE;
		this.P_CODE =  P_CODE;
		//this.array_length = array_length;
	}

	public Category(String sl, String id, String name,int quantity,String PROD_RATE,String PROD_VAT,String PPM_CODE){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
		this.PPM_CODE =  PPM_CODE;
		//this.array_length = array_length;
	}

	public Category(String sl, String id, String name,int quantity,String PROD_RATE,String PROD_VAT){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
		//this.array_length = array_length;
	}

	public Category(String sl, String id, String name,String PPM_CODE,String PROD_RATE,String PROD_VAT){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.PPM_CODE = PPM_CODE;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
	}

	public Category(String sl, String id, String name,int quantity,String PROD_RATE,String PROD_VAT,String PPM_CODE,String P_CODE,String SHIFT_CODE,String PACK_CODE  ){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
		this.PPM_CODE =  PPM_CODE;
		this.P_CODE =  P_CODE;
		this.SHIFT_CODE =  SHIFT_CODE;
		this.PACK_CODE =  PACK_CODE;
	}

	public Category(String sl, String id, String name,int quantity,String PROD_RATE,String PROD_VAT,String PPM_CODE,String P_CODE,String SHIFT_CODE,String PACK_CODE,String TOTAL_CODE  ){
		this.sl=sl;
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.PROD_RATE = PROD_RATE;
		this.PROD_VAT = PROD_VAT;
		this.PPM_CODE =  PPM_CODE;
		this.P_CODE =  P_CODE;
		this.SHIFT_CODE =  SHIFT_CODE;
		this.PACK_CODE =  PACK_CODE;
		this.TOTAL_CODE =  TOTAL_CODE;
	}

	public void setsl(String sl){
		this.sl = sl;
	}
	public void setId(String id){
		this.id = id;
	}
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
	public void setP_CODE(String P_CODE){
		this.P_CODE=P_CODE;
	}
	public void setPACK_CODE(String PACK_CODE){
		this.PACK_CODE=PACK_CODE;
	}
	public void setTOTAL_CODE(String TOTAL_CODE){
		this.TOTAL_CODE=TOTAL_CODE;
	}
	public String getsl() {
		return this.sl;
	}
	public String getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public int getQuantity(){
		return this.quantity;
	}
	public String getPROD_RATE(){
		return this.PROD_RATE;
	}
	public String getPROD_VAT(){
		return this.PROD_VAT;
	}
	public String getPPM_CODE(){ return this.PPM_CODE; }
	public String getP_CODE(){ return this.P_CODE; }
	public String getSHIFT_CODE(){ return this.SHIFT_CODE; }
	public String getPACK_CODE(){ return this.PACK_CODE; }
	public String getTOTAL_CODE(){ return this.TOTAL_CODE; }
}
