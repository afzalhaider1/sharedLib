def call(){
def content = libraryResource"bash.sh"
  writeFile file: "prog.sh" , text: content
  sh "ls -a ./prog.sh"
  sh "bash ./prog.sh"
}
