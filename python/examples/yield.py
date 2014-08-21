def genfib(n):
  a = 0
  yield a
  b = 1
  yield b
  while b < n:
    a,b = b,a+b
    yield b

for e in genfib(20): print e

x = genfib(3)
print x.next()
print x.next()
print x.next()
print x.next()
print x.next()
print x.next()
