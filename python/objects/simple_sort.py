# basic sort, note use of reverse and itemgetter to select ordering
# and key to sort on

import operator

A = [(1,2), (2,0), (3,1)]
B = A[:]

A.sort(reverse=True, key=operator.itemgetter(1))
print A

B.sort(key=lambda e: e[1])
print B
