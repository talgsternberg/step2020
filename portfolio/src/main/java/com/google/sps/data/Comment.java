package com.google.sps.data;

/** A timestamped comment */
public final class Comment {

  private final long id;
  private final String email;
  private final String text;
  private final long timestamp;

  public Comment(long id, String email, String text, long timestamp) {  //adds String email
    this.id = id;
    this.email = email;
    this.text = text;
    this.timestamp = timestamp;
  }
}