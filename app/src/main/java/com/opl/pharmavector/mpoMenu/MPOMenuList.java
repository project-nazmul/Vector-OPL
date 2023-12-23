package com.opl.pharmavector.mpoMenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MPOMenuList {
    @SerializedName("SLNO")
    @Expose
    private Integer slno;
    @SerializedName("MENU_CODE")
    @Expose
    private String menuCode;
    @SerializedName("MENU_DESC")
    @Expose
    private String menuDesc;
    @SerializedName("MENU_LOGO")
    @Expose
    private String menuLogo;
    @SerializedName("STEP")
    @Expose
    private String step;
    @SerializedName("EVENT_NAME")
    @Expose
    private Object eventName;

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getMenuLogo() {
        return menuLogo;
    }

    public void setMenuLogo(String menuLogo) {
        this.menuLogo = menuLogo;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Object getEventName() {
        return eventName;
    }

    public void setEventName(Object eventName) {
        this.eventName = eventName;
    }
}
