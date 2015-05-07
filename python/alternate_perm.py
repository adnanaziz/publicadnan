def checkPermute(A,P):

  # make copies 
  Adup = A[:]
  Pdup = P[:]
  
  print "initial A = \t\t" + str(A)
  print "initial P = \t\t" + str(P)
  
  temp = 0
  for i in range(0,len(A)):
      temp = A[i]
      A[i] = A[P[i]]
      A[P[i]] = temp
  
      temp = P[i]
      P[P[i]] = P[i]
      P[i] = temp
  
  print "alternate A permuted = \t" + str(A)
  print "alternate P after = \t" + str(P)
 
  # definition of applying a permutation - each 
  # A[i] goes to A[P[i]] 
  Agold = Adup[:]
  for i in range(0,len(Adup)):
      Agold[Pdup[i]] = Adup[i]
  
  print "golden permuted A = \t" + str(Agold)
  print "---"

A = ["a", "b", "c", "d"]
P = [2,3,1,0]

checkPermute(A,P)

A = ["a", "b", "c", "d", "e"]
P = [3,2,4,1,0]

checkPermute(A,P)

