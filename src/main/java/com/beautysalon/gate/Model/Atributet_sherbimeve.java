package com.beautysalon.gate.Model;

import java.time.LocalTime;

public class Atributet_sherbimeve {
        
    private Long id_atributit;
    private String opsioni;
    private String pershkrimi_opsionit;
    private Double qmimi;
    private Integer zbritja;
    private LocalTime kohezgjatja;

    public Atributet_sherbimeve(){}
    
    public Long getId_atributit() {
        return id_atributit;
    }
    public void setId_atributit(Long id_atributit) {
        this.id_atributit = id_atributit;
    }
    public String getOpsioni() {
        return opsioni;
    }
    public void setOpsioni(String opsioni) {
        this.opsioni = opsioni;
    }
    public String getPershkrimi_opsionit() {
        return pershkrimi_opsionit;
    }
    public void setPershkrimi_opsionit(String pershkrimi_opsionit) {
        this.pershkrimi_opsionit = pershkrimi_opsionit;
    }
    public Double getQmimi() {
        return qmimi;
    }
    public void setQmimi(Double qmimi) {
        this.qmimi = qmimi;
    }
    public Integer getZbritja() {
        return zbritja;
    }
    public void setZbritja(Integer zbritja) {
        this.zbritja = zbritja;
    }
    public LocalTime getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(LocalTime kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
}
