import sys

def largest(list):
  max = -sys.maxint # "smallest" possible int
  for index in range(len(list)):
   if (list[index] > max):
     max = list[index]
  return max
