def max_subsum(A): # your code goes here
    max = 0

    for i in A:
        max = max + i

    return max

    
A1 = [1,2,3]
res1 = max_subsum(A1)
print "res2 = ", res1
assert(res1 == 6)

A2 = [1,2,3, -14, 6, 1, -2]
res2 = max_subsum(A2)
print "res2 = ", res2
assert(res2 == 7)
