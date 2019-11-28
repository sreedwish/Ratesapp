package com.revolut.ratesapp.models;


/**
 * Created by Sreedwish
 */
public class BeanRate {

    String c_code, c_name, base_code;
    double c_value;
    int c_flag;


    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public double getC_value() {
        return c_value;
    }

    public void setC_value(double c_value) {
        this.c_value = c_value;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public int getC_flag() {
        return c_flag;
    }

    public void setC_flag(int c_flag) {
        this.c_flag = c_flag;
    }



    public void setBase_code(String base_code) {
        this.base_code = base_code;
    }

    @Override
    public String toString() {
        return "BeanRate{" +
                "c_code='" + c_code + '\'' +
                ", c_name='" + c_name + '\'' +
                ", base_code='" + base_code + '\'' +
                ", c_value=" + c_value +
                ", c_flag=" + c_flag +
                '}';
    }
}
