import bear_matching

computed_result = bear_matching.matching_bears("nil.dat")
expected_result = [('child2', 'child1'), ('child3', 'child1')]
computed_result.sort()
#print computed_result
expected_result.sort()

assert computed_result == expected_result
print "Successfully passed test7"
