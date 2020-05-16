package com.example.cabinetapp.entity;
/*
* 主要用于获取取件所需的信息
*/
public class PickUp {
    private String code;
    private String address;
    private String openCode;
    private String deliveryTime;
    public PickUp() {
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getOpenCode() {
        return openCode;
    }
    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }
    public String getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    @Override
    public String toString() {
        return "PickUp{" +
                "code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", openCode='" + openCode + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                '}';
    }
}
