package com.example.cabinetapp.entity;

public class Sender {

    private String sender_id;
    private String send_name;
    private String send_phone;
    private String send_addr;
    private String send_detail;

    public Sender(){

    }

    public Sender(String sender_id, String send_name, String send_phone, String send_addr, String send_detail) {
        this.sender_id = sender_id;
        this.send_name = send_name;
        this.send_phone = send_phone;
        this.send_addr = send_addr;
        this.send_detail = send_detail;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getSend_phone() {
        return send_phone;
    }

    public void setSend_phone(String send_phone) {
        this.send_phone = send_phone;
    }

    public String getSend_addr() {
        return send_addr;
    }

    public void setSend_addr(String send_addr) {
        this.send_addr = send_addr;
    }

    public String getSend_detail() {
        return send_detail;
    }

    public void setSend_detail(String send_detail) {
        this.send_detail = send_detail;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "sender_id='" + sender_id + '\'' +
                ", send_name='" + send_name + '\'' +
                ", send_phone='" + send_phone + '\'' +
                ", send_addr='" + send_addr + '\'' +
                ", send_detail='" + send_detail + '\'' +
                '}';
    }
}
