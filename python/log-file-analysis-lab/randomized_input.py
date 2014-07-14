import sys
import random

def randomized_site_list(n):
  common = ["google", "google", "google", "yahoo", "aol", "facebook", "facebook", "cnn", "bbc"]
  result = []
  for i in xrange(0,n):
    choose_common = random.randint(0,1)
    if (choose_common == 1):
      common_index = random.randint(0,len(common)-1)
      result.append(common[common_index])
    else:
      result.append("rare_" + str(random.random()))
  return result

def randomized_user_list(n,k):
  result = []
  for i in xrange(0,n):
    result.append("user_" + str(random.randint(0,k-1)))
  return result
  

if (len(sys.argv) > 1):
  num_lines = int(sys.argv[1])
else:
  num_lines = 100

if (len(sys.argv) > 2):
  num_users = int(sys.argv[2])
else:
  num_users = 10

site_list = randomized_site_list(num_lines)
print site_list

user_list = randomized_user_list(num_lines, num_users)
print user_list
