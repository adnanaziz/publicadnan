
def highest_affinity(site_list, user_list, time_list):
  # Strings pair should be ordered by dictionary order
  # I.e., if the highest affinity pair is "foo" and "bar"
  # return ("bar", "foo"). 
#  return ('abc', 'def')

	unique_site_list = []
	for site in site_list:
		if site not in unique_site_list:
			unique_site_list.append(site)

	unique_user_list = []
	for user in user_list:
		if user not in unique_user_list:
			unique_user_list.append(user)

	#mapping = [[0 for user in range(len(unique_user_list))] for site in range(len(unique_site_list))]

	# 2-D Array used to indicate if for every pair of site-user, they are connected
	mapping = []
	for i in range(len(unique_site_list)):
		row = []
		for j in range(len(unique_user_list)):
			row.append(j)
		mapping.append(row)


	for i in range(len(site_list)):
		mapping[unique_site_list.index(site_list[i])][unique_user_list.index(user_list[i])] = 1

	countmax=0
	site1max = ""
	site2max = ""
	for i in range(len(unique_site_list)):
		for j in range(i+1,len(unique_site_list)) :
			count = 0
			for k in range(len(unique_user_list)):
				if mapping[i][k] == 1 and mapping[j][k] == 1 :
					count += 1
			if count > countmax:
				countmax = count
				site1max = unique_site_list[i]
				site2max = unique_site_list[j]


	if site1max < site2max:
		#print "countmax is %d" % countmax
		return (site1max, site2max)
	else:	return (site2max, site1max)
	


