package com.beautysalon.gate.DTO.Terminet;

import java.time.LocalDateTime;
import java.util.List;

public class SherbimetDTO {

    private Long ID;

    private List<Object> atributet;

    private Object avaSkillId;

    private LocalDateTime created_at;

    private String emri_sherbimit;
    private String imagePath;

    private Boolean is_active;

    private Integer kohezgjatja;

    private String pershkrimi;

    private Double qmimi_baze;

    private LocalDateTime update_at;

    private Integer zbritja;

    public SherbimetDTO() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public List<Object> getAtributet() {
        return atributet;
    }

    public void setAtributet(List<Object> atributet) {
        this.atributet = atributet;
    }

    public Object getAvaSkillId() {
        return avaSkillId;
    }

    public void setAvaSkillId(Object avaSkillId) {
        this.avaSkillId = avaSkillId;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getEmri_sherbimit() {
        return emri_sherbimit;
    }

    public void setEmri_sherbimit(String emri_sherbimit) {
        this.emri_sherbimit = emri_sherbimit;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Integer getKohezgjatja() {
        return kohezgjatja;
    }

    public void setKohezgjatja(Integer kohezgjatja) {
        this.kohezgjatja = kohezgjatja;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public Double getQmimi_baze() {
        return qmimi_baze;
    }

    public void setQmimi_baze(Double qmimi_baze) {
        this.qmimi_baze = qmimi_baze;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }

    public Integer getZbritja() {
        return zbritja;
    }

    public void setZbritja(Integer zbritja) {
        this.zbritja = zbritja;
    }
}