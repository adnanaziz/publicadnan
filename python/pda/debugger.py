# Return the largest element in an array
#
# @param list An array of integers, which could be 0, positive, or negative
# @return The largest number in the given array
# @except Throws IndexError exception on input which is the empty list.

import sys
def largest(list):

  # Someone mistakenly commented out this logic:
  # if len(list) == 0:
  #   raise IndexError("bad list length")

  max=0 # initialize max to the smallest possible value
  for index in range(len(list)-1):
   if (list[index] > max):
     max = list[index]
  return max

assert 2 == largest([1,2,0])
assert 2 == largest([-1,2,0])
assert 2 == largest([2,-1,2,0])
#assert 3 == largest([2,-1,2,3])
#assert -1 == largest([-4,-1,-2,-3,-2])

try:
    largest([])
    assert False # Should not come here
except IndexError, e:
    print "Test of exceptional input passed:" + str(e)
