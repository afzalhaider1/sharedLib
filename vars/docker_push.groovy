def call(String project, String tag){
  echo "Pushing code to Docker Hub"
  withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPass', usernameVariable: 'dockerHubUser')]) {
  sh "echo ${dockerHubPass} | docker login -u ${dockerHubUser} --password-stdin"
  sh "docker push ${dockerHubUser}/${project}:${tag}"
  }
}
