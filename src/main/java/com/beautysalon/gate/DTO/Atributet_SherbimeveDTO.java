package com.beautysalon.gate.DTO;

public class Atributet_SherbimeveDTO {
    private String opsioni;
    private String pershkrimi;
    private Double qmimi;
    private Integer zbritja, kohezgjatja;

    public Atributet_SherbimeveDTO(){}

    public String getOpsioni() {
        return opsioni;
    }
    public void setOpsioni(String opsioni) {
        this.opsioni = opsioni;
    }
    public String getPershkrimi() {
        return pershkrimi;
    }
    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
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
    public Integer getKohezgjatja() {
        return kohezgjatja;
    }
    public void setKohezgjatja(Integer kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }
}
