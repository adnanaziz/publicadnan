import sys

def largest(list):
  max = 0
  for index in range(len(list)):
   if (list[index] > max):
     max = list[index]
  return max
