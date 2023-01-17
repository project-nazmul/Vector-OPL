package com.opl.pharmavector;

public class Category4 {
    private String sl;
    private String id;
    private String name;
    private int quantity;
    private String PROD_RATE;
    private String PROD_VAT;
    private String PPM_CODE;
    private String P_CODE;
    private int array_length;




    public Category4(String sl, String id, String name,int quantity,String PROD_RATE,String PROD_VAT,String PPM_CODE){
        this.sl=sl;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.PROD_RATE = PROD_RATE;
        this.PROD_VAT = PROD_VAT;
        this.PPM_CODE = PPM_CODE;

        //this.array_length = array_length;getPPM_CODE
    }




    public Category4(){
        this.sl=sl;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.PROD_RATE = PROD_RATE;
        this.PROD_VAT = PROD_VAT;
        this.PPM_CODE = PPM_CODE;
        this.P_CODE = P_CODE;
        //this.array_length = array_length;getPPM_CODE
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


    public void setPPM_CODE(String PPM_CODE){
        this.PPM_CODE=PPM_CODE;
    }

    public void setP_CODE(String PPM_CODE){
        this.P_CODE=P_CODE;
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

    public int getQuantity(){
        return this.quantity;
    }


    public String getPROD_RATE(){
        return this.PROD_RATE;
    }
    public String getPROD_VAT(){
        return this.PROD_VAT;
    }


    public String getPPM_CODE(){
        return this.PPM_CODE;
    }



    public String getP_CODE(){
        return this.P_CODE;
    }




	/*

		public int getArray_length(){
		return this.array_length;
	}
	*/



}
