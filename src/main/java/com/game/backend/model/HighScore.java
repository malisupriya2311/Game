package com.game.backend.model;

import java.io.Serializable;
import java.util.concurrent.ConcurrentSkipListSet;

public class HighScore implements Serializable {

  /**
   * Static variable with the value for the max size for the HighScore list.
   */
  private static final int MAX_SCORES = 15;

  /**
   * ConcurrentSkipListSet with all the UserScores for a Level
   */
  private ConcurrentSkipListSet<UserScore> highScores;

  /**
   * Creates a new instance of HighScore
   */
  public HighScore() {
    this.highScores = new ConcurrentSkipListSet<>();
  }

  /**
   * Get the value of highScores
   *
   * @return the value of highScores
   */
  public ConcurrentSkipListSet<UserScore> getHighScores() {
    return highScores;
  }

  /**
   * Set the value of highScores
   *
   * @param highScores new value of highScores
   */
  public void setHighScores(ConcurrentSkipListSet<UserScore> highScores) {
    this.highScores = highScores;
  }

  /**
   * Method to add an new UserScore to the HighScores
   *
   * @param userScore the new UserScore to add
   */
  public void add(UserScore userScore) {
    UserScore oldUserScore = findExistingUserScore(userScore);
    if (oldUserScore != null) {
      if (oldUserScore.getScore() >= userScore.getScore())
        return;
      highScores.remove(oldUserScore);
    }
    highScores.add(userScore);
    if (highScores.size() > MAX_SCORES) {
      highScores.pollLast();
    }
  }

  /**
   * Method to find an UserScore with the same UserId from the selected UserScore
   *
   * @param userScore UserScore to compare if exist another with the same UserId
   * @return the UserScore in the level with se same userId, return null if isn't exist any.
   */
  public UserScore findExistingUserScore(UserScore userScore) {
    for (UserScore score : highScores) {
      if (score.getUserId() == userScore.getUserId()) {
        return score;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return highScores.toString().replace("[", "").replace("]", "").replace(", ", ",");
  }
}
