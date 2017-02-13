#!/usr/bin/env groovy

def outputMessage (String ansiescape, String message){
  ansiColor ('xterm') {
   println ansiescape + message + '\033[0m'
  }
}

def outputException (Exception e){
    ansiColor('xterm'){
    println('\033[31m' + 'ERROR: ' + e + '\033[0m\n'
        + '\033[31m' + 'NODE_NAME: ' + env.NODE_NAME + "\tJOB_NAME: " + env.JOB_NAME + '\033[0m\n'
        + '\033[32m' + 'NODE_LABELS: ' + env.NODE_LABELS + '\033[0m\n'
        + 'JOB_BASE_NAME' + env.JOB_BASE_NAME + '\033[0m\n'
        + 'BUILD_TAG' + env.BUILD_TAG + '\033[0m\n'
        + 'currentBuild: ' + currentBuild + '\033[0m\n'
        + 'previousBuild: ' + currentBuild.previousBuild + '\033[0m\n'
        + 'nextBuild: ' + currentBuild.nextBuild + '\033[0m\n'
        //null + 'CHANGE_AUTHOR' + env.CHANGE_AUTHOR +  '\033[0m\n'
        // iran + 'JENKINS_HOME' + env.JENKINS_HOME + '\033[0m\n'
        //0 + 'EXECUTOR_NUMBER: ' + env.EXECUTOR_NUMBER + '\033[0m\n'
        //null + 'duration ' + env.duration + '\033[0m\n'
        //+ ': ' + env. + '\033[0m\n'
        //+ ': ' + env. + '\033[0m\n'
    
        )
        //println  '\033[32m' + (new Throwable()).getStackTrace().getClassName()
        
        //println  '\033[32m' + e.stacktrace
        //println  '\033[32m' + e.printStackTrace()
       //  println  '\033[32m' +org.codehaus.groovy.runtime.StackTraceUtils.sanitize(new Exception(e)).printStackTrace()
    }
}

def exceptionHighlight (Closure closure){
    try{
        closure()
    }catch (Exception e){
        outputException (e)
        throw e
    }
}

return this;