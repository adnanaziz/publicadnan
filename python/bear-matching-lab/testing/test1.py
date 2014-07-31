import bear_matching

computed_result = bear_matching.matching_bears("small.dat")
expected_result = [('anna', 'bob')]

computed_result.sort()
expected_result.sort()
assert computed_result == expected_result
print "Successfully passed test1!"
