#!/usr/bin/env groovy

def ansicolor

node("linux || mac") {
  try {
  println env.GITHUB_API_TOKEN
  println BRANCH_NAME
  
   // sh("curl -LSfs -u 'meshiya-devbot/token:${env.GITHUB_API_TOKEN}' https://raw.githubusercontent.com/aiming/meshiya/${BRANCH_NAME}/scripts/jenkins/common/setup.sh | bash -x")
    ansicolor = load("scripts/jenkins/lib/ansicolor.groovy")
  }catch(Exception e) {
    println 'Error: ansicolor script load error'
  }
}

try {
  stage('test_ansicolor'){
    println 'test0 FunctionCall'
    ansicolor.functionTest()
    println 'test1 ColorMessage'
    ansicolor.outputMessage ('\033[36m' , 'this Is Green')
    sh ('exit 1')
  }
  currentBuild.result = "SUCCESS"
}catch(Exception e) {
  currentBuild.result = "FAILURE"
  println 'test2 Assertion'
  ansicolor.outputException(e)
  throw e
}
