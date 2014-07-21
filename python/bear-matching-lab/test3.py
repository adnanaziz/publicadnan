import bear_matching

computed_result = bear_matching.matching_bears("medium-2.dat")
expected_result = [("child2", "child1")]

computed_result.sort()
expected_result.sort()

assert computed_result == expected_result
