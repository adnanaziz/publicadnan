import bear_matching

computed_result = bear_matching.matching_bears("medium-1.dat")
expected_result = [("pat", "jack the great"), ("deb", "jack the great")]

computed_result.sort()
expected_result.sort()

assert computed_result == expected_result
