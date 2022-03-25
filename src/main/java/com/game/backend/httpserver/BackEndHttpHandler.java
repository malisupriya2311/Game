package com.game.backend.httpserver;

import com.game.backend.constant.Constants;
import com.game.backend.controller.GameController;
import com.game.backend.exception.AuthorizationException;
import com.game.backend.exception.BackEndException;
import com.game.backend.exception.NotValidHttpException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * HttpHandler for to deploy the Http Rest Web Services,
 * for the BackEnd MiniGame Server.
 */
public class BackEndHttpHandler implements HttpHandler {

  /*
   *  Http Content type constants
   */
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String CONTENT_TEXT = "text/plain";

  /**
   * Instance for the GameController,
   * where all the data are stored.
   */
  private final GameController gameController;

  /**
   * Creates a new instance of BackEndHttpHandler
   *
   * @param gameController
   */
  public BackEndHttpHandler(GameController gameController) {
    this.gameController = gameController;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    String httpBody = "";
    int httpCode = HttpURLConnection.HTTP_OK;
    Map<String, String> parameters = (Map<String, String>) httpExchange.getAttribute(Constants.PARAMETER_ATTRIBUTE);
    String request = parameters.get(Constants.REQUEST_PARAMETER);
    try {
      switch (request) {
        case Constants.LOGIN_REQUEST:
          final int userId = Integer.parseInt(parameters.get(Constants.USER_ID_PARAMETER));
          httpBody = gameController.login(userId);
          break;
        case Constants.SCORE_REQUEST:
          final String sessionKey = parameters.get(Constants.SESSION_KEY_PARAMETER);
          final int score = Integer.parseInt(parameters.get(Constants.SCORE_PARAMETER));
          final int levelId = Integer.parseInt(parameters.get(Constants.LEVEL_ID_PARAMETER));
          gameController.score(sessionKey, levelId, score);
          break;
        case Constants.HIGH_SCORE_LIST_REQUEST:
          final int levelId1 = Integer.parseInt(parameters.get(Constants.LEVEL_ID_PARAMETER));
          httpBody = gameController.highScoreList(levelId1);
          break;
        default:
          throw new NotValidHttpException("Invalid Request.");
      }
    } catch (NumberFormatException ex) {
      httpBody = "Invalid number format.";
      httpCode = HttpURLConnection.HTTP_BAD_REQUEST;
    } catch (NotValidHttpException ex) {
      httpBody = ex.getMessage();
      httpCode = HttpURLConnection.HTTP_NOT_FOUND;
    } catch (AuthorizationException ex) {
      httpBody = ex.getMessage();
      httpCode = HttpURLConnection.HTTP_UNAUTHORIZED;
    } catch (Exception ex) {
      httpBody = BackEndException.GENERIC_ERROR_MESSAGE;
      httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
    }
    httpExchange.getResponseHeaders().add(CONTENT_TYPE, CONTENT_TEXT);
    httpExchange.sendResponseHeaders(httpCode, httpBody.length());
    OutputStream os = httpExchange.getResponseBody();
    os.write(httpBody.getBytes());
    os.close();
  }
}
