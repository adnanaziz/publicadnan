# low-tech insertion sort of array A, assumed to be of floats
def insertionsort(A):
    for i in range(len(A)):
        for j in range(i+1,len(A)):
            if A[i] > A[j]:
                (A[i], A[j]) = (A[j], A[i])


B =  []
import random
for i in range(0,100):
    B.append(random.random())

Bsorted = sorted(B)
insertionsort(B)
assert B == Bsorted
