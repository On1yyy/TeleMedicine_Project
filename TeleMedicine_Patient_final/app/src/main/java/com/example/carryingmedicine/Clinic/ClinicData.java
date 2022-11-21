package com.example.carryingmedicine.Clinic;

import android.os.Parcel;

public class ClinicData implements android.os.Parcelable { //어레이리스트의 값을 넘기기위해 Parcelable를 사용

    private String doctorID,doctorName,now_disease,reservation_date,reservation_number,reservation_time,userID,userName; //의사 정보를 받아옴

    public ClinicData(String reservation_number, String userName, String doctorName, String userID, String doctorID, String reservation_date,String reservation_time,
                  String now_disease)
        {
            this.reservation_number=reservation_number;
            this.userName = userName;
            this.doctorName=doctorName;
            this.userID=userID;
            this.doctorID=doctorID;
            this.reservation_date=reservation_date;
            this.reservation_time=reservation_time;
            this.now_disease=now_disease;

        }
    protected ClinicData(Parcel in) {
        reservation_number = in.readString();
        userName = in.readString();
        doctorName = in.readString();
        userID = in.readString();
        doctorID = in.readString();
        reservation_date = in.readString();
        reservation_time = in.readString();
        now_disease = in.readString();
    }
    public static final Creator<ClinicData> CREATOR = new Creator<ClinicData>() {
        @Override
        public ClinicData createFromParcel(Parcel in) {
            return new ClinicData(in);
        }

        @Override
        public ClinicData[] newArray(int size) {
            return new ClinicData[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {  //순서 주의해서 하기 생성자랑 순서같아야함
        parcel.writeString(reservation_number);
        parcel.writeString(userName);
        parcel.writeString(doctorName);
        parcel.writeString(userID);
        parcel.writeString(doctorID);
        parcel.writeString(reservation_date);
        parcel.writeString(reservation_time);
        parcel.writeString(now_disease);
    }
}

