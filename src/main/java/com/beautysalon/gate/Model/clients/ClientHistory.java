package com.beautysalon.gate.Model.clients;

import java.time.LocalDateTime;
import java.util.List;

public class ClientHistory {
    
    private Long id_historikut;
    private String emri_mbiemri_klientit, emri_mbiemri_punonjesit;
    private LocalDateTime data_sherbimit;
    private List<Historiku_detajet> detajet;


        public Long getId_historikut() {
        return id_historikut;
    }


    public void setId_historikut(Long id_historikut) {
        this.id_historikut = id_historikut;
    }


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


    public String getEmri_mbiemri_punonjesit() {
        return emri_mbiemri_punonjesit;
    }


    public void setEmri_mbiemri_punonjesit(String emri_mbiemri_punonjesit) {
        this.emri_mbiemri_punonjesit = emri_mbiemri_punonjesit;
    }
   
    public ClientHistory(){}


    public LocalDateTime getData_sherbimit() {
        return data_sherbimit;
    }
    public void setData_sherbimit(LocalDateTime data_sherbimit) {
        this.data_sherbimit = data_sherbimit;
    }
   
}
 
