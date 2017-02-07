#!/usr/bin/env groovy

def outputMessage (String ansiescape, String message){
  ansiColor ('xterm') {
   println ansiescape + message + '\033[0m'
  }
}

def outputException (Exception e){
  outputMessage('\033[31m', 'ERROR: ' + e.message + '\n' + e.class)
}

def functionTest () {
  println "Test Call Function is Succeed"
}

return this;
