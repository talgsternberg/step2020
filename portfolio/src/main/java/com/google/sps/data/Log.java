package com.google.sps.data;

/** A Login Info Collection */
public final class Log {

  private final String status;
  private final String loginUrl;
  private final String logoutUrl;
  private final String userEmail;

  public Log(String status, String loginUrl, String logoutUrl, String userEmail) {
    this.status= status;
    this.loginUrl = loginUrl;
    this.logoutUrl = logoutUrl;
    this.userEmail = userEmail;
  }
}