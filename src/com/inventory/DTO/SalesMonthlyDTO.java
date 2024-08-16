/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.DTO;

/**
 *
 * @author pinal
 */
public class SalesMonthlyDTO {
    private int salesid;
    private String salesDate;
    private double sr_local_sales, total_zro, total_otn18;

    public int getSalesid() {
        return salesid;
    }

    public void setSalesid(int salesid) {
        this.salesid = salesid;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public double getSr_local_sales() {
        return sr_local_sales;
    }

    public void setSr_local_sales(double sr_local_sales) {
        this.sr_local_sales = sr_local_sales;
    }

    public double getTotal_zro() {
        return total_zro;
    }

    public void setTotal_zro(double total_zro) {
        this.total_zro = total_zro;
    }

    public double getTotal_otn18() {
        return total_otn18;
    }

    public void setTotal_otn18(double total_otn18) {
        this.total_otn18 = total_otn18;
    }
    
    
}
