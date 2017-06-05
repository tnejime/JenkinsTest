#!/usr/bin/env groovy

config_filename = "work/build_config.json"

//変更範囲だけJenkinsにビルドさせるScripts
node {
  stage('git checkout') {
    //TODO:消す(Sample用GitClone)
    git url: 'https://github.com/tnejime/JenkinsTest.git'
    sh "git fetch"
    sh "git checkout testdiff"
    sh "git pull origin testdiff"
  }

  stage('build on push to pull request') {
    //Gitの変更点抽出してArrayListに格納
    def buildTargets = searchBuildTargets()
    println buildTargets

    def parallel_builds = []
  }
}


def searchBuildTargets() {
  //configファイルからpathごとのビルド設定を格納したmapを作成する
  def build_attribute_map = [:]
  def config_text = readFile(config_filename)
  def build_config = new groovy.json.JsonSlurperClassic().parseText(config_text)
  for (jobconfig in build_config) {
    def jobname = jobconfig["job"]
    for (attribute in ["include","exclude"]) {
      for (path in jobconfig[attribute]) {
        def path_attributes = build_attribute_map[path]
        if(path_attributes == null) {
          build_attribute_map[path] = [:]
          path_attributes = build_attribute_map[path]
        }
        if(path_attributes.containsKey(attribute)){
          path_attributes[attribute] += jobname
        } else {
          path_attributes[attribute] = jobname
        }
      }
    }
  }

  //Git変更点からビルド対象となるJobを探す
  def files_changed = sh(script: "git diff --stat --name-only `git show-branch --merge-base master HEAD` HEAD", returnStdout: true).split(/\n/) as ArrayList
  def buildTargets = []
  for (changed_filepath in files_changed) {
    def path_attributes = getAllValueSearchThePathRecursively(build_attribute_map, changed_filepath)
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

def getAllValueSearchThePathRecursively(def map, String fullpath, String delimiter = "/") {
  def matched_param = []
  def split_paths = fullpath.split(delimiter)
  def current_path = ""
  for (int i = 0; i < split_paths.size()-1; ++i) {
    current_path += "${split_paths[i]}/"
    if(map.containsKey(current_path)){
      matched_param.add(map[current_path])
    }
  }
  if(map.containsKey(fullpath)){
    matched_param.add(map[fullpath])
  }
  return matched_param
}
