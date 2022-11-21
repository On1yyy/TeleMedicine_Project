package com.example.carryingmedicine.Clinic;

import java.io.Serializable;

public class Clinic implements Serializable {
    String reservation_number;
    String userName;
    String doctorName;
    String userID;
    String doctorID;
    String reservation_date;
    String reservation_time;
    String now_disease;


    public Clinic(String reservation_number, String userName, String doctorName, String userID, String doctorID, String reservation_date,String reservation_time,
                 String now_disease) {
        this.reservation_number=reservation_number;
        this.userName =userName;
        this.doctorName = doctorName;
        this.userID=userID;
        this.doctorID = doctorID;
        this.reservation_date=reservation_date;
        this.reservation_time=reservation_time;
        this.now_disease=now_disease;
    }

    public String getReservation_number() {
        return reservation_number;
    }

    public void setReservation_number(String reservation_number) {
        this.reservation_number = reservation_number;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDoctorID(){return doctorID;}

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }




    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }


    public String getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(String reservation_time) {
        this.reservation_time = reservation_time;
    }

    public String getNow_disease() {
        return now_disease;
    }

    public void setNow_disease(String now_disease) {
        this.now_disease = now_disease;
    }
}
