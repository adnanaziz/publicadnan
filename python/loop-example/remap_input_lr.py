import math

chol = raw_input('enter chol: ')
bp = raw_input('enter bp: ')

chol = float(chol)
if chol < 50.0: 
  chol = 50.0
elif chol > 300.0:
  chol = 300.0

bp = float(bp)
if bp < 60.0: 
  bp = 60.0
elif bp > 180.0:
  bp = 180.0

bp = (bp - 60.0)/150.0

print age, bp

linear_term = 0.5 * age + 0.5 *bp
exp_term = math.exp(-linear_term);
prob = exp_term/(1.0+exp_term)

print 'prob of heart attach = ', prob
