#!/usr/bin/env groovy

config_filename = "work/build_config.json"

splitmap_filename = "work/SplitHashMap.groovy"
splitmap_script = null

//変更範囲だけJenkinsにビルドさせるScripts
node {
  stage('git checkout') {
    //TODO:消す(Sample用GitClone)
    git url: 'https://github.com/tnejime/JenkinsTest.git'
    sh "git fetch"
    sh "git checkout testdiff"
    sh "git pull origin testdiff"

    splitmap_script = load(splitmap_filename)
  }

  stage('build on push to pull request') {
    //Gitの変更点抽出してArrayListに格納
    def files_changed = sh(script: "git diff --stat --name-only `git show-branch --merge-base master HEAD` HEAD", returnStdout: true).split(/\n/) as ArrayList
    def buildTargets = searchBuildTargets(files_changed)
    println buildTargets

    def parallel_builds = []
  }
}

def searchBuildTargets(def files_changed = []) {
  //パスを階層ごとに分割してmap keyとしてAttributeを格納する
  def config_text = readFile(config_filename)
  def build_config = new groovy.json.JsonSlurperClassic().parseText(config_text)
  def build_attribute_map = splitmap_script.createSplitHashMap("/")
  // def build_attribute_map = new SplitHashMap()
  for (jobconfig in build_config) {
    def jobname = jobconfig["job"]
    for (attribute in ["include","exclude"]) {
      for (path in jobconfig[attribute]) {
        //Todo build_attribute_map.putの名前直す
        def path_attributes = build_attribute_map.put(path)
        if(path_attributes.containsKey(attribute)){
          path_attributes[attribute] += jobname
        } else {
          path_attributes[attribute] = jobname
        }
      }
    }
  }

  //Git変更点からビルド対象となるJobを探す
  def buildTargets = []
  for (changed_filepath in files_changed) {
    def path_attributes = build_attribute_map.getMatchedParameter(changed_filepath)
    def buildtarget_cache = []
    for (attribute in path_attributes){
      if(attribute.containsKey("include")) {
          buildtarget_cache += attribute["include"]
      }
    }
    buildtarget_cache.unique()
    for (attribute in path_attributes) {
      if(attribute.containsKey("exclude")) {
          buildtarget_cache -= attribute["exclude"]
      }
    }
    buildTargets += buildtarget_cache
  }
  buildTargets.unique()

  return buildTargets
}
