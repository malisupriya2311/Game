package com.game.backend.httpserver;

import com.game.backend.exception.AuthorizationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BackEndServerTest {

  private String PORT = "8081";

  @Before
  public void setUp() throws Exception {
    String[] arg = {"-p", PORT};
    BackEndServer.main(arg);
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testAllRequests() throws Exception {
    List<String> sessionKeys = new ArrayList<>();
    for (int i = 0; i < 18; i++) {
      sessionKeys.add(curlLoginRequest(i));
    }
    for (int i = 0; i < 15; i++) {
      String response = curlScoreRequest(sessionKeys.get(i), 42, i);
      Assert.assertNull("ScoreRequest Invalid", response);
    }
    Assert.assertEquals("HighScore Invalid",
      "14=14,13=13,12=12,11=11,10=10,9=9,8=8,7=7,6=6,5=5,4=4,3=3,2=2,1=1,0=0", curlHighScoreListRequest(42));
    curlScoreRequest(sessionKeys.get(15), 42, 15);
    curlScoreRequest(sessionKeys.get(16), 42, 16);
    curlScoreRequest(sessionKeys.get(17), 42, 1);
    Assert.assertEquals("HighScore Invalid",
      "16=16,15=15,14=14,13=13,12=12,11=11,10=10,9=9,8=8,7=7,6=6,5=5,4=4,3=3,2=2",
      curlHighScoreListRequest(42));
    String response = curlScoreRequest("QWERTY", 42, 20);
    Assert.assertEquals("ScoreRequest Invalid", AuthorizationException.AUTHORIZATION_ERROR, response);
    Assert.assertNull("HighScore Invalid", curlHighScoreListRequest(32));
  }

  private String curlScoreRequest(final String sessionKey, final int levelId, final int score) throws IOException {
    URL url = new URL("http://localhost:" + PORT + "/" + levelId + "/score?sessionkey=" + sessionKey);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setDoInput(true);
    conn.setRequestMethod("POST");
    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
    dos.writeBytes(Integer.toString(score));
    dos.flush();
    dos.close();
    conn.getResponseCode();
    String response = null;
    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
      response = br.readLine();
    }
    conn.disconnect();
    return response;
  }

  private String curlHighScoreListRequest(final int levelId) throws IOException {
    URL url = new URL("http://localhost:" + PORT + "/" + levelId + "/highscorelist");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
    String highScoreList;
    highScoreList = br.readLine();
    conn.disconnect();
    return highScoreList;
  }

  private String curlLoginRequest(final int userId) throws IOException {
    URL url = new URL("http://localhost:" + PORT + "/" + userId + "/login");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
    String sessionKey;
    sessionKey = br.readLine();
    conn.disconnect();
    return sessionKey;
  }
}
