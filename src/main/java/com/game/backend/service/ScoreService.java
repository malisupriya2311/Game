package com.game.backend.service;

import com.game.backend.model.HighScore;
import com.game.backend.model.UserScore;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Service Class where all the score data are stored.
 */
public class ScoreService {

  /**
   * ConcurrentMap<levelId, HighScore> with all the HighScore for all the Levels
   */
  private ConcurrentMap<Integer, HighScore> highScoresMap;

  /**
   * Creates a new instance of ScoreService
   */
  public ScoreService() {
    highScoresMap = new ConcurrentHashMap<>();
  }

  /**
   * Method where save a new UserScore in a Level
   * @param userScore new UserScore to save
   * @param levelId level to add the UserScore
   */
  public synchronized void saveScore(final UserScore userScore, final Integer levelId) {
    Optional<HighScore> highScore = Optional.ofNullable(highScoresMap.get(levelId));
    if (!highScore.isPresent()) {
      highScore = Optional.of(new HighScore());
      highScoresMap.putIfAbsent(levelId, highScore.get());
    }
    highScore.get().add(userScore);
  }

  /**
   * Method to get HighScore for a specific level
   * @param levelId level to get the HighScore
   * @return the HighScore for the selected level
   */
  public HighScore getHighScore(final int levelId) {
    if (!highScoresMap.containsKey(levelId)) {
      return new HighScore();
    }
    return highScoresMap.get(levelId);
  }

}
