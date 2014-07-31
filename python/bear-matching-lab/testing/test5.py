import bear_matching

computed_result = bear_matching.matching_bears("exhaustive.dat")


expected_result = [('\\"\\n"', 'child3'), ('\\"\\n"', 'child5'), ('\\"\\n"', 'child7'), ('child2', 'abcdefghijklmnopqrstuvwxyz!@#$%^&*()_+1234567890-=?><"}{|,./;\'[]\\'), ('child2', 'child1'), ('child2', 'child7'), ('child4', 'child3'), ('child4', 'child7'), ('child6', 'abcdefghijklmnopqrstuvwxyz!@#$%^&*()_+1234567890-=?><"}{|,./;\'[]\\'), ('child6', 'child1')]
computed_result.sort()
#print computed_result
expected_result.sort()

assert computed_result == expected_result
print "Successfully passed test5!"
