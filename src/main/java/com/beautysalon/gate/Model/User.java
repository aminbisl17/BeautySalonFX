package com.beautysalon.gate.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("ID")
    private Long ID;
    
      private String emri, mbiemri, username, numri_telefonit, email;

      public User(){}
      public Long getID() {
          return ID;
      }
      public void setID(Long iD) {
          ID = iD;
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
      public String getUsername() {
          return username;
      }
      public void setUsername(String username) {
          this.username = username;
      }
      public String getNumri_telefonit() {
          return numri_telefonit;
      }
      public void setNumri_telefonit(String numri_telefonit) {
          this.numri_telefonit = numri_telefonit;
      }
      public String getEmail() {
          return email;
      }
      public void setEmail(String email) {
          this.email = email;
      }
}
