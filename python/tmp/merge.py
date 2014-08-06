def mergeTwoSortedLists(L1, L2):
    L = [] # for result
    i1 = 0
    i2 = 0

    lenL1 = len(L1)
    lenL2 = len(L2)

    while True:
        if ( i1 == lenL1 or i2 == lenL2 ):
            break
        if ( L1[i1] < L2[i2] ):
            L.append(L1[i1])
            i1 = i1 + 1
        else:
            L.append(L2[i2])
            i2 = i2 + 1

    while (i1 < lenL1):
        L.append(L1[i1])
        i1 = i1 + 1

    while (i2 < lenL2):
        L.append(L2[i2])
        i2 = i2 + 1

    return L

print mergeTwoSortedLists([1,9], [2,3,4,10,11])

