package com.game.backend.scheduler;

import com.game.backend.service.SessionService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class created to be execute in a thread to clean all the sessions expired.
 */
public class SessionCleanerScheduler implements Runnable {

  /**
   * Instance for the SessionService,
   * where all the session data are stored.
   */
  private final SessionService sessionService;

  /**
   * Creates a new instance of SessionCleanerScheduler
   *
   * @param sessionService
   */
  public SessionCleanerScheduler(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  /**
   * Method to start the Thread to clean all the Invalid Sessions
   */
  public void startService() {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(this, SessionService.TIME_TO_LIVE, SessionService.TIME_TO_LIVE, TimeUnit.MILLISECONDS);
  }

  @Override
  public void run() {
    sessionService.removeInvalidSessions();
  }
}

