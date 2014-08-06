def bar(n):
    print "i am bar"
    y = 1
    print y/n

def foo(i):
    print "i am foo"
    bar(i)

N = 1
foo(N)
N = 2
foo(N)
N = 0
foo(N)
N = 3
foo(N)
print "all done"
