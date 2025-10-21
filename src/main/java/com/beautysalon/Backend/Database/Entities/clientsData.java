package com.beautysalon.Backend.Database.Entities;

import java.sql.Timestamp;

public class clientsData {
    
    private int ID;
    private String emri, mbiemri, numri_telefonit, pershkrimi;
    private char gjinia;
    private Timestamp data_regjistrimit;
    
    public clientsData(int ID, String emri, String mbiemri, char gjinia, String numri_telefonit, Timestamp data_regjistrimit, String pershkrimi){
       this.ID = ID;
       this.emri = emri;
       this.mbiemri = mbiemri;
       this.gjinia = gjinia;
       this.numri_telefonit = numri_telefonit;
       this.data_regjistrimit = data_regjistrimit;
       this.pershkrimi = pershkrimi;
    }
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getEmri() {
        return emri;
    }
    public void setEmri(String emri) {
        this.emri = emri;
    }
    public String getMbiemri() {
        return mbiemri;
    }
    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }
    public char getGjinia(){
        return gjinia;
    }
    public void setGjinia(char gjinia){
        this.gjinia = gjinia;
    }
    public String getNumri_telefonit() {
        return numri_telefonit;
    }
    public void setNumri_telefonit(String numri_telefonit) {
        this.numri_telefonit = numri_telefonit;
    }
    public Timestamp getData_regjistrimit() {
        return data_regjistrimit;
    }
    public void setData_regjistrimit(Timestamp data_regjistrimit) {
        this.data_regjistrimit = data_regjistrimit;
    }
    public String getPershkrimi() {
        return pershkrimi;
    }
    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
    public String toString(){
        return getID() + " - " + getEmri() + " : " + getMbiemri() + " : " + getNumri_telefonit() + " : " + getData_regjistrimit();
    }
}