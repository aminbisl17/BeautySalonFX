package com.beautysalon.gate.responses;
import com.fasterxml.jackson.annotation.JsonProperty;

public class loginResponse {
    
  @JsonProperty("token")
  private String token;

  private String role;

  private String username;


  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }
    public loginResponse() {}
}
