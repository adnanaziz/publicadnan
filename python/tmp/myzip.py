def myzip(L1, L2):
    result = []
    length = min(len(L1), len(L2))
    for i in range(length):
        result.append(L1[i])
        result.append(L2[i])
    if ( len(L1) > len(L2) ):
        # result.extend(L1[len(L2):])
        for j in range(len(L1) - len(L2)):
            result.append(L1[len(L2) + j])
    else:
        result.extend(L2[len(L1):])
    return result

A1 = [0, 1,2,3,4]
B1 = [10, 11, 12, 13, 14]

print myzip(A1, B1)
        
A2 = [1,2,3]
B2 = [11, 12, 13, 14, 15]

print myzip(A2, B2)
print myzip(B2, A2)

