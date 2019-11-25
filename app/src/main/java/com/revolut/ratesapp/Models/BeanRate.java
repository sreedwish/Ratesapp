package com.revolut.ratesapp.Models;

public class BeanRate {

    String c_code, c_name, base_code;
    double c_value;
    int c_flag;
    double rate_difference ;

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

    public double getRate_difference() {
        return rate_difference;
    }

    public void setRate_difference(double rate_difference) {
        this.rate_difference = rate_difference;
    }

    public String getBase_code() {
        return base_code;
    }

    public void setBase_code(String base_code) {
        this.base_code = base_code;
    }
}
