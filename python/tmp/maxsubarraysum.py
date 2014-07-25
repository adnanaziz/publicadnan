def maxsubsum(A):
    import sys
    maxSoFar = -100000
    prevBest = 0
    endIndex = -1
    startIndex = 0
    i = 0
    for v in A:
        if ( v > v + prevBest ):
            bestAtV = v
            startIndex = i
        else:
            bestAtV = v + prevBest
        prevBest = bestAtV
        if ( maxSoFar < bestAtV ):
            endIndex = i
        maxSoFar = max(maxSoFar, bestAtV)
        i = i + 1
    print "Start index = ", startIndex
    print "End index = ", endIndex
    return max(maxSoFar, 0)

A = [-3, 100, 200, -500, -250, 100, 150, -200, 300, -100, 5, -1000, 20, 50, 10]

print maxsubsum(A)

B = [-3, -5, -1, -8]

print maxsubsum(B)
