package com.beautysalon.gate.Model.clients;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {

     public Client(){}

     @JsonProperty("ID")
     private Long ID;

    private String emri;
    private String mbiemri;
    private String gjinia;
    private String numri_telefonit;
    private LocalDateTime data_regjistrimit;
    private String pershkrimi;
    private String username;
    private List<ClientHistory> clientHistory;
    public Long getID() {
        return ID;
    }
    public void setID(Long id) {
        this.ID = id;
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
    public String getGjinia() {
        return gjinia;
    }
    public void setGjinia(String gjinia) {
        this.gjinia = gjinia;
    }
     public LocalDateTime getData_regjistrimit() {
        return data_regjistrimit;
    }
    public void setData_regjistrimit(LocalDateTime data_regjistrimit) {
        this.data_regjistrimit = data_regjistrimit;
    }
        public String getNumri_telefonit() {
        return numri_telefonit;
    }
    public void setNumri_telefonit(String numri_telefonit) {
        this.numri_telefonit = numri_telefonit;
    }
    public String getPershkrimi() {
        return pershkrimi;
    }
    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
   public List<ClientHistory> getClientHistory() {
        return clientHistory;
    }
    public void setClientHistory(List<ClientHistory> clientHistory) {
        this.clientHistory = clientHistory;
    }
    

}
