package com.inventory.UI.datepicker;

public class SelectedDate {

    @Override
    public String toString() {
        String month = "";
        String day = "";
        if(getMonth()<= 9)
        {
            month = "0"+getMonth();
        }
        else
        {
            month = ""+getMonth();
        }
        
        if(getDay()<= 9)
        {
            day = "0"+getDay();
        }
        else
        {
            day = ""+getDay();
        }
        return getYear()+"-"+month+"-"+day; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SelectedDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public SelectedDate() {
    }

    private int day;
    private int month;
    private int year;
}
