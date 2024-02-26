package com.github.wojciechk92.carrental.security.user;

public class UserTokenResponse {
  private String access_token;
  private String refresh_token;
  private String token_type;
  private int expires_in;
  private int refresh_expires_in;
  private int not_before_policy;
  private String session_state;
  private String scope;

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public int getExpires_in() {
    return expires_in;
  }

  public void setExpires_in(int expires_in) {
    this.expires_in = expires_in;
  }

  public int getRefresh_expires_in() {
    return refresh_expires_in;
  }

  public void setRefresh_expires_in(int refresh_expires_in) {
    this.refresh_expires_in = refresh_expires_in;
  }

  public String getRefresh_token() {
    return refresh_token;
  }

  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }

  public String getToken_type() {
    return token_type;
  }

  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }

  int getNot_before_policy() {
    return not_before_policy;
  }

  void setNot_before_policy(int not_before_policy) {
    this.not_before_policy = not_before_policy;
  }

  String getSession_state() {
    return session_state;
  }

  void setSession_state(String session_state) {
    this.session_state = session_state;
  }

  String getScope() {
    return scope;
  }

  void setScope(String scope) {
    this.scope = scope;
  }
}