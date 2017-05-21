#!/usr/bin/env groovy

//特定のdelimiterを基準のpathを分割して格納・参照するHashMap
//TODO:未完成(必要とした機能しか入っていない)
class SplitHashMap implements Serializable {
  //groovyだとインデクサを変えられないので
  private def _nested_map = [:]
  private def _delimiter = "/"
  private def _param = "/param"

  SplitHashMap(String delimiter = "/") {
    _delimiter = delimiter
  }

  //keyを_delimiterで分割してmapに格納し、parameter格納先mapを取得する
  //TODO: 名前直す
  //XXX: 名前と機能が乖離しているやばいやつ
  def put(String key) {
    def map = _nested_map

    def split_keys = key.split(_delimiter)
    for ( obj in split_keys) {
      if (!map.containsKey(obj)) {
        map[obj] = [:]
      }
      map = map[obj]
    }
    if(!map.containsKey(_param)) {
      map[_param] = [:]
    }
    return map[_param]
  }

  //分割されたkeyが通るすべてのparameterを取得する(rootから)
  //TODO: 名前直す
  def getMatchedParameter(String key) {
    def matched_param = []
    def split_keys = key.split(_delimiter)
    def map = _nested_map
    for ( obj in split_keys) {
      map = map[obj]
      if( map == null) {
        break
      }
      if(map[_param] != null) {
          println "add param" + map[_param]
          matched_param.add(map[_param])
      }
    }
    return matched_param
  }

  //TODO:debug用(消す)
  def getRawMap() {
    return _nested_map
  }
}

//groovyで別ファイルのクラスをnewでインスタンス化させれなかったのでひとまずこれ使っている
//TODO:解決ほうがあれば消してnew SplitHashMapで呼び出せるようにする
def createSplitHashMap(String delimiter = "/") {
  return new SplitHashMap(delimiter)
}

return this;
