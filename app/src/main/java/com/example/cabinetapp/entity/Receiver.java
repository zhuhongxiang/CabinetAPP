package com.example.cabinetapp.entity;

public class Receiver {

    private String receiver_id;
    private String rec_name;
    private String rec_phone;
    private String rec_addr;
    private String rec_detail;

    public Receiver(){

    }

    public Receiver(String receiver_id, String rec_name, String rec_phone, String rec_addr, String rec_detail) {
        this.receiver_id = receiver_id;
        this.rec_name = rec_name;
        this.rec_phone = rec_phone;
        this.rec_addr = rec_addr;
        this.rec_detail = rec_detail;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getRec_name() {
        return rec_name;
    }

    public void setRec_name(String rec_name) {
        this.rec_name = rec_name;
    }

    public String getRec_phone() {
        return rec_phone;
    }

    public void setRec_phone(String rec_phone) {
        this.rec_phone = rec_phone;
    }

    public String getRec_addr() {
        return rec_addr;
    }

    public void setRec_addr(String rec_addr) {
        this.rec_addr = rec_addr;
    }

    public String getRec_detail() {
        return rec_detail;
    }

    public void setRec_detail(String rec_detail) {
        this.rec_detail = rec_detail;
    }

    @Override
    public String toString() {
        return "Receiver{" +
                "receiver_id='" + receiver_id + '\'' +
                ", rec_name='" + rec_name + '\'' +
                ", rec_phone='" + rec_phone + '\'' +
                ", rec_addr='" + rec_addr + '\'' +
                ", rec_detail='" + rec_detail + '\'' +
                '}';
    }
}
