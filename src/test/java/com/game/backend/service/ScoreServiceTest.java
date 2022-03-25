package com.game.backend.service;

import com.game.backend.model.HighScore;
import com.game.backend.model.UserScore;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScoreServiceTest {

  ScoreService scoreService;

  @Before
  public void setUp() throws Exception {
    scoreService = new ScoreService();
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testSaveScore() throws Exception {
    int levelId = 1;
    int userId = 42;
    int score = 500;
    UserScore userScore = new UserScore(userId, score);
    scoreService.saveScore(userScore, levelId);
    HighScore highScore = scoreService.getHighScore(levelId);
    Assert.assertEquals("HighScore Invalid", "42=500", highScore.toString());
  }

  @Test
  public void testLevelIdWithoutScores() throws Exception {
    HighScore highScore = scoreService.getHighScore(555);
    Assert.assertEquals("HighScore Invalid", "", highScore.toString());
  }

  @Test
  public void testUniqueScorePerUser() throws Exception {
    int levelId = 2;
    int userId = 42;
    int score = 500;
    HighScore highScore;
    UserScore userScore1 = new UserScore(userId, score);
    scoreService.saveScore(userScore1, levelId);
    highScore = scoreService.getHighScore(levelId);
    System.out.println("levelScore = " + highScore);
    Assert.assertEquals("HighScore Invalid", "42=500", highScore.toString());
    UserScore userScore2 = new UserScore(userId, score + 50);
    scoreService.saveScore(userScore2, levelId);
    highScore = scoreService.getHighScore(levelId);
    System.out.println("levelScore = " + highScore);
    Assert.assertEquals("HighScore Invalid", "42=550", highScore.toString());
    UserScore userScore3 = new UserScore(userId, score - 50);
    scoreService.saveScore(userScore3, levelId);
    highScore = scoreService.getHighScore(levelId);
    System.out.println("levelScore = " + highScore);
    Assert.assertEquals("HighScore Invalid", "42=550", highScore.toString());
    UserScore userScore4 = new UserScore(userId, score + 100);
    scoreService.saveScore(userScore4, levelId);
    highScore = scoreService.getHighScore(levelId);
    System.out.println("levelScore = " + highScore);
    Assert.assertEquals("HighScore Invalid", "42=600", highScore.toString());
  }

  @Test
  public void testSeveralScore() throws Exception {
    int levelId = 3;
    HighScore highScore;
    UserScore userScore1 = new UserScore(42, 500);
    scoreService.saveScore(userScore1, levelId);
    highScore = scoreService.getHighScore(levelId);
    System.out.println("levelScore = " + highScore);
    Assert.assertEquals("HighScore Invalid", "42=500", highScore.toString());
    UserScore userScore2 = new UserScore(72, 450);
    scoreService.saveScore(userScore2, levelId);
    highScore = scoreService.getHighScore(levelId);
    System.out.println("levelScore = " + highScore);
    Assert.assertEquals("HighScore Invalid", "42=500,72=450", highScore.toString());
    UserScore userScore3 = new UserScore(42, 600);
    scoreService.saveScore(userScore3, levelId);
    highScore = scoreService.getHighScore(levelId);
    System.out.println("levelScore = " + highScore);
    Assert.assertEquals("HighScore Invalid", "42=600,72=450", highScore.toString());
    UserScore userScore4 = new UserScore(72, 700);
    scoreService.saveScore(userScore4, levelId);
    highScore = scoreService.getHighScore(levelId);
    System.out.println("levelScore = " + highScore);
    Assert.assertEquals("HighScore Invalid", "72=700,42=600", highScore.toString());
    UserScore userScore5 = new UserScore(10, 650);
    scoreService.saveScore(userScore5, levelId);
    highScore = scoreService.getHighScore(levelId);
    System.out.println("levelScore = " + highScore);
    Assert.assertEquals("HighScore Invalid", "72=700,10=650,42=600", highScore.toString());
  }

  @Test
  public void testMaxLimitScore() throws Exception {
    int levelId = 4;
    for (int i = 1; i < 16; i++) {
      UserScore userScore1 = new UserScore(i, i);
      scoreService.saveScore(userScore1, levelId);
    }
    HighScore highScore = scoreService.getHighScore(levelId);
    Assert.assertEquals("HighScore Invalid",
      "15=15,14=14,13=13,12=12,11=11,10=10,9=9,8=8,7=7,6=6,5=5,4=4,3=3,2=2,1=1", highScore.toString());
    int userId = 42;
    UserScore userScore = new UserScore(userId, userId);
    scoreService.saveScore(userScore, levelId);
    highScore = scoreService.getHighScore(levelId);
    Assert.assertEquals("HighScore Invalid",
      "42=42,15=15,14=14,13=13,12=12,11=11,10=10,9=9,8=8,7=7,6=6,5=5,4=4,3=3,2=2", highScore.toString());
  }

  @Test
  public void testGetLevelScore() throws Exception {

  }
}
