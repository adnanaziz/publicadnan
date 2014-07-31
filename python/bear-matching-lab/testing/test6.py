import bear_matching

computed_result = bear_matching.matching_bears("back_slash.dat")
expected_result = [('pat', 'jack the great'), ('sety', 'art'), ('sety', 'jack the great')]
computed_result.sort()
expected_result.sort()

assert computed_result == expected_result
print "Successfully passed test6"
