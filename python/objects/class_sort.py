class MyClass:
    'Example of a class'
    first = ""
    second = 0

    def __init__(self, first, second):
        self.first = first
        self.second = second

    def __str__(self):
        return "(" + self.first + ',' + str(self.second) + ")"

    def __cmp__(self, other):
        if self.second != other.second:
            return self.second - other.second;
        else:
            return cmp(self.first, other.first)

A = [MyClass("aa", 100), MyClass("A", 100), MyClass("adnan",10), MyClass("aa", 1000), MyClass("b",5)]
A.sort()
result = ""
for mc in A:
  result += "%s "  % mc
print result
