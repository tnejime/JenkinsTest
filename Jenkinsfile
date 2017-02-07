
def ansicolor
def jenkinstest

node ("linux || mac") {
stage('git checkout'){
    ansicolor = load("scripts/jenkins/lib/ansicolor.groovy")
    //jenkinstest = load("scripts/jenkins/ansicolor/test_ansicolor.groovy")
}
stage ('testest'){
	ansicolor.outputMessage('\033[31m' , "hogehoge")
}

}