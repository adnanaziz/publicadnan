import operator

A = [(1,2), (2,0), (3,1)]
B = A[:]

A.sort(reverse=True, key=operator.itemgetter(1))
print A

B.sort(key=lambda e: e[1])
print B
