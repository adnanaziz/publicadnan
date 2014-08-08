# computes size of intersection of A and B, assumed to be dicts
def numCommon(A, B):
    result = 0
    for user in A:
        if user in B:
            result += 1
    return result

def highest_affinity(site_list, user_list, time_list):

    # we want to create a dict in which each site
    # is a key, and the value is a dict of users
    # who went to that site. (Set is more appropriate
    # to track users, but we're sticking to lists 
    # and dicts for this solution.)

    # initialize mapping from site to users
    siteToUsers = dict()
    for s in site_list:
        if not s in siteToUsers:
            siteToUsers[s] = dict()

    # add users 
    for i in range(len(site_list)):
        s = site_list[i]
        u = user_list[i]
        siteToUsers[s][u] = 1

    maxSoFar = -1
    bestPair = ("nil", "nil")
    # want to iterate over all distinct pairs
    # of sites
    siteList = siteToUsers.keys()
    for i in range(len(siteList)):
        # using i+1 for start of range keeps us from 
        # comparing a site with itself and also from
        # looking at the same pair twice
        for j in range(i+1,len(siteList)):
            # note the use of siteList[i], since i iterates over indices
            ijCommon = numCommon(siteToUsers[siteList[i]], siteToUsers[siteList[j]])
            if ijCommon > maxSoFar:
                maxSoFar = ijCommon
                bestPair = (siteList[i], siteList[j])

    # Strings pair should be ordered by dictionary order
    # I.e., if the highest affinity pair is "foo" and "bar"
    # return ("bar", "foo"). 
    if bestPair[0] > bestPair[1]:
        bestPair = (bestPair[1], bestPair[0])
    return bestPair
