package com.game.backend.model;

import java.io.Serializable;

/**
 * Class that represents the entity UserScore
 */
public class UserScore implements Serializable, Comparable<UserScore> {

  /**
   * Integer for the UserId
   */
  private int userId;
  /**
   * Integer for the score of the User
   */
  private int score;

  /**
   * Creates a new instance of UserScore
   *
   * @param userId
   * @param score
   */
  public UserScore(int userId, int score) {
    this.userId = userId;
    this.score = score;
  }

  /**
   * Get the value of userId
   *
   * @return the value of userId
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Set the value of userId
   *
   * @param userId new value of userId
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * Get the value of score
   *
   * @return the value of score
   */
  public int getScore() {
    return score;
  }

  /**
   * Set the value of score
   *
   * @param score new value of score
   */
  public void setScore(int score) {
    this.score = score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserScore userScore = (UserScore) o;
    return userId == userScore.userId;
  }

  @Override
  public int hashCode() {
    return userId;
  }

  @Override
  public int compareTo(UserScore o) {
    int compare = Integer.compare(o.score, this.score);
    //for the HighScore Set, if the both scores are equals, the oldest is the greatest
    if (compare == 0 && Integer.compare(o.userId, this.userId) != 0)
      compare = 1;
    return compare;
  }

  @Override
  public String toString() {
    return userId + "=" + score;
  }
}
