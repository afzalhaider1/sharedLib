def add(int a, int b) {
    def sum = a + b
    echo "Sum of ${a} and ${b} is ${sum}"
    return sum
}
def sub(int a, int b) {
    def sub = a - b
    echo "Subtraction of ${a} and ${b} is ${sub}"
    return sub
}
