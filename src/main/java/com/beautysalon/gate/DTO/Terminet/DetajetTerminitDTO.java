package com.beautysalon.gate.DTO.Terminet;

public class DetajetTerminitDTO {

    private Object atributet;

    private Long id_detajet_termineve;
    private Long id_terminit;

    private Integer kohezgjatja;
    private Double pagesa;

    private SherbimetDTO sherbimet;

    public DetajetTerminitDTO() {
    }

    public Object getAtributet() {
        return atributet;
    }

    public void setAtributet(Object atributet) {
        this.atributet = atributet;
    }

    public Long getId_detajet_termineve() {
        return id_detajet_termineve;
    }

    public void setId_detajet_termineve(Long id_detajet_termineve) {
        this.id_detajet_termineve = id_detajet_termineve;
    }

    public Long getId_terminit() {
        return id_terminit;
    }

    public void setId_terminit(Long id_terminit) {
        this.id_terminit = id_terminit;
    }

    public Integer getKohezgjatja() {
        return kohezgjatja;
    }

    public void setKohezgjatja(Integer kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }

    public Double getPagesa() {
        return pagesa;
    }

    public void setPagesa(Double pagesa) {
        this.pagesa = pagesa;
    }

    public SherbimetDTO getSherbimet() {
        return sherbimet;
    }

    public void setSherbimet(SherbimetDTO sherbimet) {
        this.sherbimet = sherbimet;
    }
}