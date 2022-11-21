package com.example.carryingmedicine.Doctorlist;

import java.io.Serializable;

public class Doctor implements Serializable { //닥터의 정보를 정하는 닥터객체 클래스
    String name,birth,hospital_name,hospital_address,hospital_number,major,id;
    String school;


    public Doctor(String id, String name, String birth,String school,String hospital_name,
                  String hospital_number,String hospital_address, String major) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.school = school;
        this.hospital_name = hospital_name;
        this.hospital_number = hospital_number;
        this.hospital_address = hospital_address;
        this.major=major;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getBirth() {
        return birth;
    }
    public String getSchool() {
        return school;
    }
    public String getHospital_name() {
        return hospital_name;
    }
    public String getHospital_number() {
        return hospital_number;
    }
    public String getHospital_address() {
        return hospital_address;
    }
    public String getMajor() {
        return major;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setBirth(String birth) {
        this.name = birth;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }
    public void setHospital_number(String hospital_number) {
        this.hospital_number = hospital_number;
    }
    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }
    public void setMajor(String major) {
        this.major = major;
    }
}
