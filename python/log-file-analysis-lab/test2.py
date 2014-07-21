import randomized_input
import compute_highest_affinity

num_lines = 10000
num_users = 1000

site_list = randomized_input.randomized_site_list(num_lines)
user_list = randomized_input.randomized_user_list(num_lines, num_users)
time_list = xrange(0,num_lines)

computed_result = compute_highest_affinity.highest_affinity(site_list, user_list, time_list)
expected_result = ("facebook", "google")

assert computed_result == expected_result
