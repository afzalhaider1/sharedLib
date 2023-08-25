def add(int a, int b) {
    def sum = a + b
    sh 'echo "Sum of ${a} and ${b} is ${sum}"'
    return sum
}
def sub(int a, int b) {
    def sub = a - b
    sh 'echo "Subtraction of ${a} and ${b} is ${sub}"'
    return sub
}
