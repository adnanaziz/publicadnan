import bear_matching

computed_result = bear_matching.matching_bears("space.dat")
expected_result = [("child 2","child1"),("child2","chil d2"),("child2", "child1")]

computed_result.sort()
#print computed_result
expected_result.sort()

assert computed_result == expected_result
print "Successfully passed test4!"
