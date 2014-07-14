import math

def normalize_age(age):
  age = float(age)  
  if age < 30.0: 
    age = 30.0
  elif age > 70.0:
    age = 1.0
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
  
  print 'prob', prob
  return abs(actual-prob)

# age = raw_input('enter weight: ')
# bp = raw_input('enter bp: ')

# normal_age = normalize_age(age)
# normal_bp = normalize_bp(bp)

min_delta = None
for a0 in [-0.2, -0.4, -0.6, -0.8, -1.0, 0.0, 0.2, 0.4, 0.6, 0.8, 1.0]:
  for a1 in  [-0.2, -0.4, -0.6, -0.8, -1.0, 0.0, 0.2, 0.4, 0.6, 0.8, 1.0]:
    print 'a0, a1, ', a0, a1
    total_delta = 0
    total_delta += compute_delta(a0, a1, 60, 180, 1.0)
    total_delta += compute_delta(a0, a1, 55, 150, 1.0)
    total_delta += compute_delta(a0, a1, 60, 159, 1.0)
    total_delta += compute_delta(a0, a1, 80, 179, 1.0)
    total_delta += compute_delta(a0, a1, 85, 159, 1.0)
    total_delta += compute_delta(a0, a1, 95, 159, 1.0)
    total_delta += compute_delta(a0, a1, 99, 139, 1.0)
    total_delta += compute_delta(a0, a1, 30, 60, -1.0)
    total_delta += compute_delta(a0, a1, 35, 60, -1.0)
    total_delta += compute_delta(a0, a1, 20, 50, -1.0)
    total_delta += compute_delta(a0, a1, 25, 40, -1.0)
    total_delta += compute_delta(a0, a1, 30, 65, -1.0)
    total_delta += compute_delta(a0, a1, 35, 58, -1.0)
    total_delta += compute_delta(a0, a1, 31, 48, -1.0)
    print 'total_delta =', total_delta
    if min_delta == None or total_delta < min_delta:
      min_delta = total_delta
      best_a0 = a0
      best_a1 = a1

print 'best_a0, best_a1 =', best_a0, best_a1
