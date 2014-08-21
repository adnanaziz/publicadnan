def printInfo(name, age, gender):
  print 'Name: ', name, 'Age: ', age, 'Gender: ', gender

printInfo('Sam Sheperd', 36, 'Male')

# named arguments
printInfo(name='Sherlock Holmes', age=46, gender='Male')
printInfo(age=66, gender='Female', name='Jane Marple')

# mix positional and named args, positionals come first:
printInfo('Hercule Poirot', gender='Male', age=56)

# default param values
def incr(x,y=1):
  return x + y
 
print incr(2,3)

print incr(10)

# variable length arg list, denoted by *
# less useful in Python (easy lists, typeless)

def many(name, *values):
  print name, values


# remaining args are passed as tuple

many("zero")
many("zero", "one")
many("zero", "one", 2)
many("zero", "one", 2, 3.14)

# last argument preceded by ** => remaining args are placed into dictionary, passed via that arg

def echo(**args):
  for e in args.keys(): print e, ":", args[e]

echo(a=3)
echo(a=3, b='abc')
echo(a=3, b='abc', c = 2.71)

# can combine:
def dumb(name, number, *vargs, **kargs):
  print name, ':', number
  print vargs
  print kargs

dumb('one', 32)
dumb('one', 32, 55, 77, x=2106, y = 'yankee clipper')

# apply(): takes function and list of args, calls function on those args

def sum(x,y): return x + y

print apply(sum, [7,3])

# can use with positional and named arguments
print apply(sum, [7], {'y':3})
