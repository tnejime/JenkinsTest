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
[WorkflowScript.run(WorkflowScript:41), ___cps.transform___(Native Method), sun.reflect.GeneratedConstructorAccessor209.newInstance(Unknown Source), sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45), java.lang.reflect.Constructor.newInstance(Constructor.java:526), org.codehaus.groovy.reflection.CachedConstructor.invoke(CachedConstructor.java:83), org.codehaus.groovy.runtime.callsite.ConstructorSite$ConstructorSiteNoUnwrapNoCoerce.callConstructor(ConstructorSite.java:105), org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallConstructor(CallSiteArray.java:60), org.codehaus.groovy.runtime.callsite.AbstractCallSite.callConstructor(AbstractCallSite.java:235), org.kohsuke.groovy.sandbox.impl.Checker$3.call(Checker.java:194), org.kohsuke.groovy.sandbox.GroovyInterceptor.onNewInstance(GroovyInterceptor.java:40), org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SandboxInterceptor.onNewInstance(SandboxInterceptor.java:128), org.kohsuke.groovy.sandbox.impl.Checker$3.call(Checker.java:191), org.kohsuke.groovy.sandbox.impl.Checker.checkedConstructor(Checker.java:188), com.cloudbees.groovy.cps.sandbox.SandboxInvoker.constructorCall(SandboxInvoker.java:20), com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.dispatchOrArg(FunctionCallBlock.java:96), com.cloudbees.groovy.cps.impl.FunctionCallBlock$ContinuationImpl.fixName(FunctionCallBlock.java:77), sun.reflect.GeneratedMethodAccessor320.invoke(Unknown Source), sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43), java.lang.reflect.Method.invoke(Method.java:606), com.cloudbees.groovy.cps.impl.ContinuationPtr$ContinuationImpl.receive(ContinuationPtr.java:72), com.cloudbees.groovy.cps.impl.ConstantBlock.eval(ConstantBlock.java:21), com.cloudbees.groovy.cps.Next.step(Next.java:58), com.cloudbees.groovy.cps.Continuable.run0(Continuable.java:154), org.jenkinsci.plugins.workflow.cps.SandboxContinuable.access$001(SandboxContinuable.java:18), org.jenkinsci.plugins.workflow.cps.SandboxContinuable$1.call(SandboxContinuable.java:33), org.jenkinsci.plugins.workflow.cps.SandboxContinuable$1.call(SandboxContinuable.java:30), org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.GroovySandbox.runInSandbox(GroovySandbox.java:108), org.jenkinsci.plugins.workflow.cps.SandboxContinuable.run0(SandboxContinuable.java:30), org.jenkinsci.plugins.workflow.cps.CpsThread.runNextChunk(CpsThread.java:163), org.jenkinsci.plugins.workflow.cps.CpsThreadGroup.run(CpsThreadGroup.java:328), org.jenkinsci.plugins.workflow.cps.CpsThreadGroup.access$100(CpsThreadGroup.java:80), org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:240), org.jenkinsci.plugins.workflow.cps.CpsThreadGroup$2.call(CpsThreadGroup.java:228), org.jenkinsci.plugins.workflow.cps.CpsVmExecutorService$2.call(CpsVmExecutorService.java:63), java.util.concurrent.FutureTask.run(FutureTask.java:262), hudson.remoting.SingleLaneExecutorService$1.run(SingleLaneExecutorService.java:112), jenkins.util.ContextResettingExecutorService$1.run(ContextResettingExecutorService.java:28), java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:471), java.util.concurrent.FutureTask.run(FutureTask.java:262), java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145), java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615), java.lang.Thread.run(Thread.java:745)]