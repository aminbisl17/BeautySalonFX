package com.beautysalon.gate.Model.clients;

import java.time.LocalDateTime;
import java.util.List;

public class ClientHistory {
    
    private String emri_mbiemri_klientit, emri_mbiemri_puntorit;
    private LocalDateTime data_sherbimit;
    private List<Historiku_detajet> detajet;



    public List<Historiku_detajet> getDetajet() {
        return detajet;
    }


    public void setDetajet(List<Historiku_detajet> detajet) {
        this.detajet = detajet;
    }


    public String getEmri_mbiemri_klientit() {
        return emri_mbiemri_klientit;
    }


    public void setEmri_mbiemri_klientit(String emri_mbiemri_klientit) {
        this.emri_mbiemri_klientit = emri_mbiemri_klientit;
    }


    public String getEmri_mbiemri_puntorit() {
        return emri_mbiemri_puntorit;
    }


    public void setEmri_mbiemri_puntorit(String emri_mbiemri_puntorit) {
        this.emri_mbiemri_puntorit = emri_mbiemri_puntorit;
    }
   
    public ClientHistory(){}


    public LocalDateTime getData_sherbimit() {
        return data_sherbimit;
    }
    public void setData_sherbimit(LocalDateTime data_sherbimit) {
        this.data_sherbimit = data_sherbimit;
    }
   
}
 
