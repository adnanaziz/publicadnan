def highest_affinity(site_list, user_list, time_list):

  # First, we associate each unique site with an integer, and
  # each unique user with an integer. The integer is the index
  # into the unique_site_list for sites, similarly for users.
	unique_site_list = []
	for site in site_list:
		if site not in unique_site_list:
			unique_site_list.append(site)

	unique_user_list = []
	for user in user_list:
		if user not in unique_user_list:
			unique_user_list.append(user)


  # Now we create a 2-D list which we use to record for site-user pairs, 
  # whether that site was viewed by the corresponding user
	pageUserView = []
	for i in range(len(unique_site_list)):
		row = []
		for j in range(len(unique_user_list)):
			row.append(False)
		pageUserView.append(row)

  # FYI: more elegant way of defining the 2-D list pageUserView, uses list comprehension:
	# pageUserView = [[False for user in range(len(unique_user_list))] for site in range(len(unique_site_list))]

	for i in range(len(site_list)):
		pageUserView[unique_site_list.index(site_list[i])][unique_user_list.index(user_list[i])] = True

	countmax = 0
	site1max = ""
	site2max = ""
  # Finally, look for the pair of rows (which corresponds to sites) that have the highest 
  # dot product, i.e., the most 1s in common.
	for i in range(len(unique_site_list)):
		for j in range(i+1,len(unique_site_list)) :
			count = 0
			for k in range(len(unique_user_list)):
				if pageUserView[i][k] and pageUserView[j][k]:
          # both i and j were viewed by k
					count += 1
			if count > countmax:
				countmax = count
				site1max = unique_site_list[i]
				site2max = unique_site_list[j]

  # Spec said returned string pair should be ordered by dictionary order
  # I.e., if the highest affinity pair is "foo" and "bar"
  # return ("bar", "foo"). 
  # return ('abc', 'def')
	if (site1max < site2max):
		return (site1max, site2max)
	else:
		return (site2max, site1max)
