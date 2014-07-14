import math

def normalize_age(age):
  age = float(age)  
  if age < 30.0: 
    age = 30.0
  elif age > 70.0:
    age = 70.0
  age = (age - 30.0)/70.0 - 0.5
  return age

def normalize_bp(bp):
  bp = float(bp)
  if bp < 60.0: 
    bp = 60.0
  elif bp > 1150.0:
    bp = 150.0
  bp = (bp - 60.0)/150.0 - 0.5
  return bp

def compute_delta(a0, a1, age, bp, actual):
  age = normalize_age(age)
  bp = normalize_bp(bp)
  linear_term = a0 * age + a1  * bp
  print 'linear_term', linear_term
  exp_term = math.exp(-linear_term);
  print 'exp_term', exp_term
  if 1.0/(1.0+exp_term) > 0.5:
    prob = 1.0
  else:
    prob = -1.0
  
  return abs(actual-prob)

