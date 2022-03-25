package com.game.backend.httpserver;

import com.game.backend.controller.GameController;
import com.game.backend.exception.BackEndException;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main Class where the HttpServer for the BackEnd is deployed.
 */
public class BackEndServer {

  public static int PORT = 8081;

  /**
   * Main Method where the HttpServer is deployed
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    try {
      System.out.println("\n\n   Starting HTTPServer.");
      String hostName = "localhost";
      try {
        hostName = InetAddress.getLocalHost().getCanonicalHostName();
      } catch (UnknownHostException ex) {
        System.err.println("Unknown Host: " + ex);
      }
      HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
      HttpContext httpContext = httpServer.createContext("/", new BackEndHttpHandler(GameController.getInstance()));
      httpContext.getFilters().add(new BackEndHttpFilter());
      ExecutorService executorService = Executors.newCachedThreadPool();
      httpServer.setExecutor(executorService);
      httpServer.start();
      System.out.println("Successfully HTTPServer started in http://" + hostName + ":" + PORT + "/");
    } catch (Exception e) {
      System.err.println("Error with the HTTPServer.");
      System.err.println(e.getMessage());

    }
  }
}
