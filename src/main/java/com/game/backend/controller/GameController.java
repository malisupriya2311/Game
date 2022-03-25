package com.game.backend.controller;

import com.game.backend.exception.AuthorizationException;
import com.game.backend.model.HighScore;
import com.game.backend.model.Session;
import com.game.backend.model.UserScore;
import com.game.backend.scheduler.SessionCleanerScheduler;
import com.game.backend.service.ScoreService;
import com.game.backend.service.SessionService;

/**
 * Singleton Class
 */
public class GameController {

  public volatile static GameController instance;
  private final ScoreService scoreService;
  private final SessionService sessionService;
  private final SessionCleanerScheduler sessionCleaner;

  /**
   * Creates a new instance of GameController
   */
  public GameController() {
    scoreService = new ScoreService();
    sessionService = new SessionService();
    sessionCleaner = new SessionCleanerScheduler(sessionService);
    sessionCleaner.startService();
  }

  /**
   * singlton instance
   * @return the instance initialized
   */
  public static GameController getInstance() {
    if (instance == null) {
      synchronized (GameController.class) {
        if (instance == null) {
          instance = new GameController();
        }
      }
    }
    return instance;
  }

  /**
   * User Login Request
   * @param userId userId who want to make a login
   * @return String with the sessionKey for the active session
   */
  public String login(int userId) {
    Session session = sessionService.createNewSession(userId);
    return session.getSessionKey();
  }

  /**
   * Highest Score Request
   * @param sessionKey,levelId, score
   * @throws AuthorizationException added session validation exception
   */
  public void score(String sessionKey, int levelId, int score) throws AuthorizationException {
    if (!sessionService.isSessionValid(sessionKey)) {
      throw new AuthorizationException();
    }
    Session session = sessionService.getSessionActive(sessionKey);
    if (session == null) {
      throw new AuthorizationException();
    }
    UserScore userScore = new UserScore(session.getUserId(), score);
    scoreService.saveScore(userScore, levelId);
  }

  /**
   * HighScoreList Service Request
   *
   * @param levelId level to print the High Score List
   * @return CSV of <userid>=<score>
   */
  public String highScoreList(int levelId) {
    HighScore highScore = scoreService.getHighScore(levelId);
    return highScore.toString();
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }
}
