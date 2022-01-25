package com.apisero.processor;

import java.util.logging.*;

public class LogHandler
{
  public static void disable()
  {
      Logger.getLogger("com.gargoylesoftware.htmlunit")
      .setLevel(java.util.logging.Level.OFF);
      Logger.getLogger("org.apache.http").setLevel(
      java.util.logging.Level.OFF);
  }
}
