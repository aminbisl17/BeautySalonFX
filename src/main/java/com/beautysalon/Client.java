package com.beautysalon;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Client {

    @JsonProperty("id")
    private int ID;

    @JsonProperty("emri")
    private String emri;

    @JsonProperty("mbiemri")
    private String mbiemri;

    @JsonProperty("gjinia")
    private char gjinia;

    @JsonProperty("numriTelefonit")
    private String numri_telefonit;

    @JsonProperty("dataRegjistrimit")
    private Timestamp data_regjistrimit;

    @JsonProperty("pershkrimi")
    private String pershkrimi;

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

    public char getGjinia() {
        return gjinia;
    }

    public void setGjinia(char gjinia) {
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

    public Client() {
        // Required by Jackson
    }

    public Client(int ID, String emri, String mbiemri, char gjinia,
                  String numri_telefonit, Timestamp data_regjistrimit, String pershkrimi) {
        this.ID = ID;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.gjinia = gjinia;
        this.numri_telefonit = numri_telefonit;
        this.data_regjistrimit = data_regjistrimit;
        this.pershkrimi = pershkrimi;
    }

    // getters and setters...
}
