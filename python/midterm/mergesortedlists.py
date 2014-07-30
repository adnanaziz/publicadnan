def mergesortedlists(L1, L2):
    lenL1 = len(L1)
    lenL2 = len(L2)

    i1 = 0
    i2 = 0

    result = list()

    while True:
        if (i1 == lenL1 or i2 == lenL2):
            break
        elif (L1[i1] < L2[i2]):
            result.append(L1[i1])
            i1 = i1 + 1
        else:
            result.append(L2[i2])
            i2 = i2 + 1

    while ( i1 < lenL1 ):
        result.append(L1[i1])
        i1 = i1 + 1

    while ( i2 < lenL2 ):
        result.append(L2[i2])
        i2 = i2 + 1

    return result

print mergesortedlists([1,9], [2,3,4,10])
            
