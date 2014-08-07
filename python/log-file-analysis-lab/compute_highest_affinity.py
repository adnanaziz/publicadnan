# No need to process files and manipulate strings - we will
# pass in 3 lists (of equal length) that correspond to 
# sites views. The first list is the site visited, the second is
# the user who visited the site and the third is the time
# they visited the site.

# See the test cases for more details.

# Note: you cannot use dictionaries for this lab.

def dotProd(A, B):
  # assert len(A) == len(B)
  result = 0
  for i in A:
    if i in B:
      result += 1
  return result

def highest_affinity(site_list, user_list, time_list):
  # Strings pair should be ordered by dictionary order
  # I.e., if the highest affinity pair is "foo" and "bar"
  # return ("bar", "foo"). 

  siteId = 0
  siteNameToId = dict()
  siteIdToName = dict()
  for site in site_list:
    if not site in siteNameToId:
      siteNameToId[site] = siteId
      siteIdToName[siteId] = site
      siteId += 1

  userId = 0
  userNameToId = dict()
  for user in user_list:
    if not user in userNameToId:
      userNameToId[user] = userId
      userId += 1

  siteAndUser = []
  for i in range(siteId):
    siteAndUser.append( dict() )

  print "built empty matrix"

  for index in range(len(site_list)):
    i = siteNameToId[site_list[index]]
    j = userNameToId[user_list[index]]
    siteAndUser[i][j] = 1

  bestPair = (0,0)
  maxValue = 0
  for s0 in range(len(siteNameToId)):
    print "s0 = ", s0
    for s1 in range(s0+1,len(siteNameToId)):
      prod = dotProd(siteAndUser[s0], siteAndUser[s1])
      if prod > maxValue:
        maxValue = prod
        bestPair = (s0,s1)

  s0Name = siteIdToName[bestPair[0]]
  s1Name = siteIdToName[bestPair[1]]

  if (s1Name < s0Name):
    return (s1Name,s0Name)
  else:
    return (s0Name,s1Name)
    

    



  return ('abc', 'def')
