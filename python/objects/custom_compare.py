#cmp specifies a custom comparison function of two arguments (list items) which should 
#return a negative, zero or positive number depending on whether the first argument is
#considered smaller than, equal to, or larger than the second argument:
#cmp=lambda x,y: cmp(x.lower(), y.lower()). The default value is None.

# if disagree on second field, use it to establish sort
# order. this field is an int, and to get increasing
# order on it, we return negative if x[1] < y[1], 
# positive if x[1] is greater than y[1].
#
# if second field is same, use first field (string-valued) to 
# seperate. to get decreasing order on the first field,
# use build in cmp with y[0] as first argument, x[0] as second.

def custom_compare(x,y):
    print "in custom_compare with ", x, " ", y
    if (x[1] != y[1]): 
        result = x[1] - y[1]
    else:
        return cmp(y[0], x[0]) 
  
A = [("b", 100), ("A", 100), ("adnan",10), ("aa", 1000), ("aa",100)]
A.sort(custom_compare)
print A


# another example
def custom_compare_2(x,y):
    if (x[1] != y[1]): 
        return x[1] - y[1]
    else:
        return y[0] - x[0]


A = [ (0,1,0), (1,0,1), (2,0,2), (1,-1,3), (-1,4,4)]
print A
A.sort(cmp=custom_compare)
print A


B = [ (0,1,0), (1,0,1), (2,0,2), (1,-1,3), (-1,4,4)]
# alternate calling syntax, don't need the cmp=
B.sort(custom_compare_2)
print A == B
