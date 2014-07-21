B = [("aa", 100), ("A", 100), ("adnan",10), ("aa", 1000), ("b",5)]

mycmp =  lambda x,y: (x[1] - y[1]) if ( x[1] != y[1] ) else cmp(x[0], y[0])

B.sort( mycmp )
print B
