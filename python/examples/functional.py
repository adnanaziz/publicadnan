def even(x):
  return x % 2 == 0

A = [1,2,3,4,5,6]

print filter(even, A)

# inconvenient to define if used just once

print filter(lambda x: x % 2 == 0, A)
 
# closures: access enclosing scope

alpha = 2
print filter(lambda x: ( x + alpha ) % 2 == 0, A)

alpha = 3
print filter(lambda x: ( x + alpha ) % 2 == 0, A)

B = {1:2, 3:4, 6:6, 7:7}
print filter(lambda x: x % 2 == 0, B)

# list comprehension: general form is [ expr for var in list if expr ]

a = [1,2,3,4,5]
print [x*2 for x in a if x < 4]

def listOfSquares(a):
  return [x*x for x in a]

listOfSquares([1,2,3,4])

# range of list comprehension does not have to be a simple identifier

print [a[i] for i in range(0,len(a)) if i % 2 == 0]

# list intersection using filter and lambda

b = [2,4,5,6,7]
intersect = filter( lambda x: x in b,  a )
print intersect 

# list intersection using list comprehension
intersect = [ x for x in a if x in b]
print intersect 

# Note: many performance issues can be hidden by simple syntax

# Using reduce to sum elements in list:
a = [1,2,3,4,5,6]
print reduce( lambda x, y: x + y, a)

# Need third argument for forming the product:
print reduce( lambda x, y: x * y, a, 1 )

# Compute list length (not a serious alternative to len())
print reduce( lambda x, y: x + 1, a)
# Book has bug, did you notice it?

# Make a copy of a list (not a serious alternative to copy.copy(a), list(a), a[:])
print reduce( lambda x,y: x + [y], a, [])

# Merge two lists (not a serious alternative to +)
print reduce( lambda x,y: x + [y], b, a)

# Reverse a list (not a serious alternative to reversed())
print reduce( lambda x,y: [y] + x, a, [])





