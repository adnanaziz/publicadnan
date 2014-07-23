B = [("aa", 100), ("A", 100), ("adnan",10), ("aa", 1000), ("b",5)]

# can pass lambda expression where a function is expected
B.sort(lambda x,y: (x[1] - y[1]) if ( x[1] != y[1] ) else cmp(x[0], y[0]))

print B
