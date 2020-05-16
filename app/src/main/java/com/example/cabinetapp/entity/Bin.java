package com.example.cabinetapp.entity;

public class Bin {
    private String bin_id;
    private String cab_id;
    private String type;
    private String status;
    private String price;

    public Bin(){

    }


    public Bin(String bin_id,String cab_id, String type, String status, String price) {
        this.bin_id = bin_id;
        this.cab_id = cab_id;
        this.type = type;
        this.status = status;
        this.price = price;
    }

    public String getBin_id() {
        return bin_id;
    }

    public void setBin_id(String bin_id) {
        this.bin_id = bin_id;
    }

    public String getCab_id() {
        return cab_id;
    }

    public void setCab_id(String cab_id) {
        this.cab_id = cab_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Bin{" +
                "bin_id='" + bin_id + '\'' +
                ",cab_id='" + cab_id + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
