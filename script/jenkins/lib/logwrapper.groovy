#!/usr/bin/env groovy

def consoleHighlight (String status, String branch , String revision  , Exception error = null){
  ansiColor("xterm"){
  def color
  def errorMessage = " "
  
  switch (status){
    case "SUCCESS":
      color = "\033[32m"
      break
    case "FAILURE":
      color = "\033[31m"
      errorMessage = error == null ? " " : "ERROR:\t" + error.class + "\nMessage:\t" + error.message + "\n"
      break
    default:
      return
  }
  
  println( 
    color
    + status + "\n"
    + errorMessage 
    + "JOB_NAME:\t" + env.JOB_NAME + "\n"
    + "NODE_NAME:\t" + env.NODE_NAME + "\n"
    + "NODE_LABELS:\t" + env.NODE_LABELS + "\n"
    + "JOB_BASE_NAME:\t" + env.JOB_BASE_NAME + "\n"
    + "BUILD_TAG:\t" + env.BUILD_TAG + "\n"
    + "Git branch:\t" + branch + "\n" 
    + "Git revision:\t" + revision + "\n"
    + "\033[0m" 
    )
  }

return this;