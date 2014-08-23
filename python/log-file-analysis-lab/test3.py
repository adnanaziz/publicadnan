import compute_highest_affinity

a = "a"
b = "b"
c = "c"
d = "d"
e = "e"
f = "f"
g = "g"
h = "h"

A = "A"
B = "B"
C = "C"
D = "D"
E = "E"
F = "F"
G = "G"
H = "H"


user_list = [A, C, H, A, C, D, E, F, G, C, D, E, F, G, H, C, B, D, E, F, G, C, G, A, D, E, H, A, C, E, H]
site_list = [a, a, a, b, b, b, b, b, b, c, c, c, c, c, c, d, e, e, e, e, e, f, f, g, g, g, g, h, h, h, h]

time_list = range(0,30)

computed_result = compute_highest_affinity.highest_affinity(site_list, user_list, time_list)
expected_result = (b, c)

assert computed_result == expected_result
