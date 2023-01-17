package com.opl.pharmavector.prescriptionsurvey;


public class rx_model  {
    
    public String  ff_code,brandcount,ff_roll,ff_type,title,emp_code,img_path;

    public String getFF_Code(){
        return ff_code;
    }
    public void setFF_Code(String ff_code){   this.ff_code = ff_code;  }

    public String getBrandcount(){
        return brandcount;
    }
    public void setBrandcount(String brandcount){   this.brandcount = brandcount;  }

    public String getImg_path(){
        return img_path;
    }
    public void setImg_paths(String img_path){   this.img_path = img_path;  }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){   this.title = title;  }

    public String getEmp_code(){
        return emp_code;
    }
    public void setEmp_code(String emp_code){   this.emp_code = emp_code;  }

    public String getFf_roll(){
        return ff_roll;
    }
    public void setFf_roll(String ff_roll){   this.ff_roll = ff_roll;  }

    public String getFf_type(){
        return ff_type;
    }
    public void setFf_type(String ff_type){   this.ff_type = ff_type;  }

    public rx_model(String brandcount, String title, String ff_code, String img_path) {
        this.brandcount = brandcount;
        this.title = title;
        this.ff_code  = ff_code;
        this.img_path = img_path;
    }

}
