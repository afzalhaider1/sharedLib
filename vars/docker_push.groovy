def call(String project, String tag){
  echo "Pushing code to Docker Hub"
  withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPass', usernameVariable: 'dockerHubUser')]) {
  sh "docker login -u ${dockerhubuser} -p ${dockerhubpass}"
  sh "docker push ${dockerhubuser}/${project}:${tag}"
}
