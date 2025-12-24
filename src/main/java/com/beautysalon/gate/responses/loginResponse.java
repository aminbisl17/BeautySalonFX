package com.beautysalon.gate.responses;

import com.beautysalon.gate.Model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class loginResponse {
    
  @JsonProperty("token")
  private String token;

  @JsonProperty("empdto")
  private User user;

  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
    public loginResponse() {}
}
