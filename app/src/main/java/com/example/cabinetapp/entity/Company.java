package com.example.cabinetapp.entity;

public class Company {
    private String company_id;
    private String company_name;
    private String exp_price;

    public Company(){

    }


    public Company(String company_id, String company_name) {
        this.company_id = company_id;
        this.company_name = company_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getExp_price() {
        return exp_price;
    }

    public void setExp_price(String exp_price) {
        this.exp_price = exp_price;
    }

    @Override
    public String toString() {
        return "Company{" +
                "company_id='" + company_id + '\'' +
                ",company_name='" + company_name + '\'' +
                ",exp_price='" + exp_price + '\'' +
                '}';
    }
}
