import sys

def largest(list):
  max=sys.maxint # initialize max to the largest possible value
  for index in range(len(list)-1):
   if (list[index] > max):
     max = list[index]
  return max
