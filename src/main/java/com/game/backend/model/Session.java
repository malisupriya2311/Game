package com.game.backend.model;

import java.io.Serializable;

/**
 * Class that represents the entity Session
 */
public class Session implements Serializable {

  /**
   * Integer for the UserId
   */
  private Integer userId;
  /**
   * String with the created SessionKey
   */
  private String sessionKey;
  /**
   * Long with the time where the Session was created
   */
  private long createdTime;

  /**
   * Creates a new instance of HighScore
   *
   * @param userId
   * @param sessionKey
   * @param createdTime
   */
  public Session(Integer userId, String sessionKey, long createdTime) {
    this.userId = userId;
    this.sessionKey = sessionKey;
    this.createdTime = createdTime;
  }

  /**
   * Get the value of userId
   *
   * @return the value of userId
   */
  public Integer getUserId() {
    return userId;
  }

  /**
   * Set the value of userId
   *
   * @param userId new value of userId
   */
  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  /**
   * Get the value of sessionKey
   *
   * @return the value of sessionKey
   */
  public String getSessionKey() {
    return sessionKey;
  }

  /**
   * Set the value of sessionKey
   *
   * @param sessionKey new value of sessionKey
   */
  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }

  /**
   * Get the value of createdTime
   *
   * @return the value of createdTime
   */
  public long getCreatedTime() {
    return createdTime;
  }

  /**
   * Set the value of createdTime
   *
   * @param createdTime new value of createdTime
   */
  public void setCreatedTime(long createdTime) {
    this.createdTime = createdTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Session session = (Session) o;
    if (createdTime != session.createdTime)
      return false;
    if (sessionKey != null ? !sessionKey.equals(session.sessionKey) : session.sessionKey != null)
      return false;
    if (!userId.equals(session.userId))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = userId.hashCode();
    result = 31 * result + (sessionKey != null ? sessionKey.hashCode() : 0);
    result = 31 * result + (int) (createdTime ^ (createdTime >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return sessionKey;
  }
}
