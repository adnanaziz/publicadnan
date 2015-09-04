B = [("abc", 100), ("A", 100), ("adnan",10), ("aa", 1000), ("b",5), ("aa", 100) ]

# note that python's A if B else C syntax is the equivalent of Java's
# B ? A : C syntax

# lambda notation: don't need to define another function
mycmp =  lambda x,y: (x[1] - y[1]) if ( x[1] != y[1] ) else cmp(x[0], y[0])

B.sort( mycmp )
print B
