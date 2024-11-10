def call(String project, String tag){
  echo "Pushing code to Docker Hub"
  withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPass', usernameVariable: 'dockerHubUser')]) {
  sh "docker login -u ${dockerHubUser} -p ${dockerHubPass}"
  sh "docker push ${dockerHubUser}/${project}:${tag}"
}
