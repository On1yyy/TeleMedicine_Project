package com.example.carryingmedicine.Doctorlist;

import android.os.Parcel;
import android.os.Parcelable;

public class DoctorData implements Parcelable { //어레이리스트의 값을 넘기기위해 Parcelable를 사용

        private String doctorID,doctorPass,doctorName,doctorBirth,doctorSchool,doctorHospital,
                doctorhospitaladress,doctorhospitalnumber; //의사 정보를 받아옴
        private String doctorClass;
        private int doctorLicense;;
        public DoctorData(String doctorID,String doctorPass,String doctorName,int doctorLicense,String doctorBirth,
                          String doctorSchool,String doctorHospital,
                          String doctorhospitalnumber, String doctorhospitaladress,String doctorClass)
        {
            this.doctorID=doctorID;this.doctorPass=doctorPass;
            this.doctorName=doctorName;this.doctorLicense=doctorLicense;
            this.doctorBirth=doctorBirth;this.doctorSchool=doctorSchool;
            this.doctorHospital=doctorHospital;this.doctorhospitalnumber = doctorhospitalnumber;
            this.doctorhospitaladress=doctorhospitaladress;this.doctorClass=doctorClass;
        }

    protected DoctorData(Parcel in) {
        doctorID = in.readString();
        doctorPass = in.readString();
        doctorName = in.readString();
        doctorLicense = in.readInt();
        doctorBirth = in.readString();
        doctorSchool = in.readString();
        doctorHospital = in.readString();
        doctorhospitalnumber = in.readString();
        doctorhospitaladress = in.readString();
        doctorClass=in.readString();

    }
    public static final Creator<DoctorData> CREATOR = new Creator<DoctorData>() {
        @Override
        public DoctorData createFromParcel(Parcel in) {
            return new DoctorData(in);
        }

        @Override
        public DoctorData[] newArray(int size) {
            return new DoctorData[size];
        }
    };

    public String getDoctor_id() {
            return doctorID;
        }
        public void setDoctor_id(String doctorID) {
            this.doctorID = doctorID;
        }

        public String getDoctor_pass() {
            return doctorPass;
        }
        public void setDoctor_pass(String doctorpass) {
            this.doctorPass = doctorpass;
        }

        public String getDoctor_Name() {
            return doctorName;
        }
        public void setDoctor_Name(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getDoctor_Birth() {
            return doctorBirth;
        }
        public void setDoctor_Birth(String doctorBirth) {
            this.doctorBirth = doctorBirth;
        }

        public String getDoctor_School() {
            return doctorSchool;
        }
        public void setDoctor_school(String doctorSchool) {
            this.doctorSchool = doctorSchool;
        }

        public String getDoctor_Hospital_Number() {
        return doctorhospitalnumber;
    }
        public void setDoctor_Hospital_Number(String doctorhospitalnumber) {
        this.doctorhospitalnumber = doctorhospitalnumber; }

       public String getDoctor_Hospital_Adress() {
        return doctorhospitaladress;
    }
        public void setDoctor_Hospital_Adress(String doctorhospitaladress) {
        this.doctorhospitaladress = doctorhospitaladress; }

        public String getDoctor_Hospital() {
            return doctorHospital;
        }
        public void setDoctor_Hospital(String doctorHospital) {
            this.doctorHospital = doctorHospital;
        }


        public int getMember_License() {
            return doctorLicense;
        }
        public void setDoctor_license(int doctorLicense) {
            this.doctorLicense = doctorLicense;
        }

        public String getDoctor_Class() {
        return doctorClass;
    }
        public void setDoctor_Class(String doctorClass) {
            this.doctorClass = doctorClass;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {  //순서 주의해서 하기 생성자랑 순서같아야함
        parcel.writeString(doctorID);
        parcel.writeString(doctorPass);
        parcel.writeString(doctorName);
        parcel.writeInt(doctorLicense);
        parcel.writeString(doctorBirth);
        parcel.writeString(doctorSchool);
        parcel.writeString(doctorHospital);
        parcel.writeString(doctorhospitalnumber);
        parcel.writeString(doctorhospitaladress);
        parcel.writeString(doctorClass);

    }
}

