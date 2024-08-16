/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.DTO;

/**
 *
 * @author asjad
 */

// Data Transfer Object (DTO) class for Suppliers

public class SupplierDTO {

    private int suppID;
    private double debit, credit, balance;
    private String suppCode, fullName, location, phone,tpin_no,vat_no,tax_category_no,tax_category_name;
    private double vat_percent;
    private String bank_name, account_name, account_number, branch_name,short_code,nrc;

    @Override
    public String toString() {
        return  fullName;
    }
    
    
    
    public int getSuppID() {
        return suppID;
    }

    public void setSuppID(int suppID) {
        this.suppID = suppID;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getSuppCode() {
        return suppCode;
    }

    public void setSuppCode(String suppCode) {
        this.suppCode = suppCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTpin_no() {
        return tpin_no;
    }

    public void setTpin_no(String tpin_no) {
        this.tpin_no = tpin_no;
    }

    public String getVat_no() {
        return vat_no;
    }

    public void setVat_no(String vat_no) {
        this.vat_no = vat_no;
    }

    public String getTax_category_no() {
        return tax_category_no;
    }

    public void setTax_category_no(String tax_category_no) {
        this.tax_category_no = tax_category_no;
    }

    public String getTax_category_name() {
        return tax_category_name;
    }

    public void setTax_category_name(String tax_category_name) {
        this.tax_category_name = tax_category_name;
    }

    public double getVat_percent() {
        return vat_percent;
    }

    public void setVat_percent(double vat_percent) {
        this.vat_percent = vat_percent;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    
    
    
}
