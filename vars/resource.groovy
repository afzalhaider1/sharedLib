def call(){
def content = libraryresource"bash.sh"
  writefile file: "prog.sh" , text: content
  sh ls
}
