package com.beautysalon.gate.Model.clients;

public class Historiku_detajet {

        private Long id_historikut_detajet;
        private String emri_sherbimit, emri_atributit, pershkrimi;
        private Double pagesa;

            public Long getId_historikut_detajet() {
            return id_historikut_detajet;
        }

        public void setId_historikut_detajet(Long id_historikut_detajet) {
            this.id_historikut_detajet = id_historikut_detajet;
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

        public String getPershkrimi() {
            return pershkrimi;
        }

        public void setPershkrimi(String pershkrimi) {
            this.pershkrimi = pershkrimi;
        }

        public Double getPagesa() {
            return pagesa;
        }

        public void setPagesa(Double pagesa) {
            this.pagesa = pagesa;
        }

        public Historiku_detajet(){

        }
}
