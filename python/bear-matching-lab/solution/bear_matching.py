#Courtesy Tianjiao Zhu

def normalize(bear_name):
  import re
  #first lower
  bear_name = bear_name.lower()
  #then remove extra spaces
  lst = re.findall('\S+',bear_name)
  delimiter = ' '
  bear_name = delimiter.join(lst)
  return bear_name

def check_criteria(bear1,bear2,bears):
  #check gender
  gender1 = bears[bear1][0]
  gender2 = bears[bear2][0]
  if gender1 == gender2:
    return False
  #check years
  year1 = bears[bear1][3]
  year2 = bears[bear2][3]
  differ = abs(year1 * 10 - year2 * 10)
  if year1 > 6 or year1 < 2 or year2 > 6 or year2 < 2 or differ > 10:
    return False
  #check offspring
  mothers = []
  fathers = []
  for key in bears:
    mothers.append(bears[key][1])
    fathers.append(bears[key][2])
  if (bear1 in mothers) or (bear1 in fathers) or (bear2 in mothers) or (bear2 in fathers):
    return False
  #check first cousin
  ancestors1 = []
  ancestors2 = []
  #bears[bear1][1] is bear1's mom, bears[mom1][1] and bears[mom1][2] are bear1's grandparents
  mom1 = bears[bear1][1]
  if mom1 != 'nil':  
    ancestors1.append(mom1)
    if mom1 in bears:
      if bears[mom1][1] != 'nil':
        ancestors1.append(bears[mom1][1])
      if bears[mom1][2] != 'nil':
        ancestors1.append(bears[mom1][2])
  #bears[bear1][2] is bear1's dad, bears[dad1][1] and bears[dad1][2] are bear1's grandparents
  dad1 = bears[bear1][2]
  if dad1 != 'nil':   
    ancestors1.append(dad1)
    if dad1 in bears:
      if bears[dad1][1] != 'nil':
        ancestors1.append(bears[dad1][1])
      if bears[dad1][2] != 'nil':
        ancestors1.append(bears[dad1][2])
  #bears[bear2][1] is bear2's mom, bears[mom2][1] and bears[mom2][2] are bear2's grandparents
  mom2 = bears[bear2][1]
  if mom2 != 'nil':  
    ancestors2.append(mom2)
    if mom2 in bears:
      if bears[mom2][1] != 'nil':
        ancestors2.append(bears[mom2][1])
      if bears[mom2][2] != 'nil':
        ancestors2.append(bears[mom2][2])
  #bears[bear2][2] is bear2's dad, bears[dad2][1] and bears[dad2][2] are bear2's grandparents
  dad2 = bears[bear2][2]
  if dad2 != 'nil':   
    ancestors2.append(dad2)
    if dad2 in bears:
      if bears[dad2][1] != 'nil':
        ancestors2.append(bears[dad2][1])
      if bears[dad2][2] != 'nil':
        ancestors2.append(bears[dad2][2])
  for an1 in ancestors1:
    if an1 in ancestors2:
      return False
  return True

def matching_bears(filename): 
  # Return a list of pairs. The list does not have to be ordered in 
  # any specific way. See the test cases for more details.
  fhand = open(filename)
  bears = {}
  result = []
  for line in fhand:
    words = line.rstrip().split(':')
    bears[normalize(words[0])] = (normalize(words[1]),normalize(words[2]),normalize(words[3]),float(words[4]))
  bear_list = bears.keys()
  bear_list.sort()
  #generate bear_list
  for i in range(len(bear_list)):
    bear1 = bear_list[i]
    for j in range(len(bear_list)):
      bear2 = bear_list[j]
      if i < j and check_criteria(bear1,bear2,bears):
        if bears[bear1][0] == 'f':
          result.append((bear1,bear2))
        else:
          result.append((bear2,bear1))    
    
  return result

