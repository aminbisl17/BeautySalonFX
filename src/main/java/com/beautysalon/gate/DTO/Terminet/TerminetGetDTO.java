package com.beautysalon.gate.DTO.Terminet;

import java.time.LocalDateTime;
import java.util.List;

import com.beautysalon.gate.Model.clients.Client;

public class TerminetGetDTO {

    private Client client;

    private LocalDateTime data_caktimit;
    private LocalDateTime data_krijimit;

    private List<DetajetTerminitDTO> detajet_terminit;

    private Long employee_id;
    private Long id_terminit;

    private String pershkrimi;

    public TerminetGetDTO() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getData_caktimit() {
        return data_caktimit;
    }

    public void setData_caktimit(LocalDateTime data_caktimit) {
        this.data_caktimit = data_caktimit;
    }

    public LocalDateTime getData_krijimit() {
        return data_krijimit;
    }

    public void setData_krijimit(LocalDateTime data_krijimit) {
        this.data_krijimit = data_krijimit;
    }

    public List<DetajetTerminitDTO> getDetajet_terminit() {
        return detajet_terminit;
    }

    public void setDetajet_terminit(List<DetajetTerminitDTO> detajet_terminit) {
        this.detajet_terminit = detajet_terminit;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public Long getId_terminit() {
        return id_terminit;
    }

    public void setId_terminit(Long id_terminit) {
        this.id_terminit = id_terminit;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
}