def call(String dockerUser, String projectName, String tag){
  echo "Building the code"
  sh 'docker build -t ${dockerUser}/${projectName}:${tag} .'
}
