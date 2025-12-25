package com.beautysalon.gate.Model.clients;

import java.sql.Time;
import java.time.LocalDateTime;

public class ClientHistory {
    
    private int id_historiku;
    private String emri_sherbimit;
    private String emri_atributit;
    private LocalDateTime data_sherbimit;
    private Double pagesa;
    private Double qmimiBazik;
    private int zbritja;
    private String pershkrimi;
    private Time kohezgjatja;

    public ClientHistory(){}

    public int getId_historiku() {
        return id_historiku;
    }
    public void setId_historiku(int id_historiku) {
        this.id_historiku = id_historiku;
    }
    public String getEmri_sherbimit() {
        return emri_sherbimit;
    }
    public void setEmri_sherbimit(String emri_sherbimit) {
        this.emri_sherbimit = emri_sherbimit;
    }

     public String getEmri_atributit() {
        return emri_atributit;
    }
    public void setEmri_atributit(String emri_atributit) {
        this.emri_atributit = emri_atributit;
    }
    public LocalDateTime getData_sherbimit() {
        return data_sherbimit;
    }
    public void setData_sherbimit(LocalDateTime data_sherbimit) {
        this.data_sherbimit = data_sherbimit;
    }
    public Double getPagesa() {
        return pagesa;
    }
    public void setPagesa(Double pagesa) {
        this.pagesa = pagesa;
    }
    public Double getQmimiBazik() {
        return qmimiBazik;
    }
    public void setQmimiBazik(Double qmimiBazik) {
        this.qmimiBazik = qmimiBazik;
    }
    public int getZbritja() {
        return zbritja;
    }
    public void setZbritja(int zbritja) {
        this.zbritja = zbritja;
    }
    public String getPershkrimi() {
        return pershkrimi;
    }
    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
    public Time getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(Time kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
}
