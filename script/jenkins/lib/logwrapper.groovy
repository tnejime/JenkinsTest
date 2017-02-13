#!/usr/bin/env groovy

def consoleHighlight (String branch = '' , String revision = '' , Closure closure){
    ansiColor('xterm'){
        def result = "SUCCESS"
        def color = '\033[32m'
        def errorMessage = ''
    try{
        closure()
    }catch (Exception e){
        color = '\033[31m'
        result = "FAILURE"
        errorMessage = 'ERROR: ' + e.class + '\n' + e.message + '\n'
        throw e
    }finally{
        println( 
            color
            + result + '\n'
            + errorMessage
            + 'JOB_NAME: ' + env.JOB_NAME + '\n'
            + 'NODE_NAME: ' + env.NODE_NAME + '\n'
            + 'NODE_LABELS: ' + env.NODE_LABELS + '\n'
            + 'JOB_BASE_NAME' + env.JOB_BASE_NAME + '\n'
            + 'BUILD_TAG' + env.BUILD_TAG + '\n'
            + '\033[0m' 
            )
        }
        println ('\033[34m' + "git Test")
    }
}

return this;