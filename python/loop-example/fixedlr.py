import math

age = raw_input('enter weight: ')
bp = raw_input('enter cholestrol: ')

age = float(age)
bp = float(bp)

linear_term = 0.5 * age + 0.5 *bp
exp_term = math.exp(-linear_term);
prob = 1.0/(1.0+exp_term)

print 'prob of heart attach = ', prob
