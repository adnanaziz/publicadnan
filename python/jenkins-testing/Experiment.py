import sys

def largest(list):
  max=0# initialize max to 0
  for index in range(len(list)-1):
   if (list[index] > max):
     max = list[index]
  return max
