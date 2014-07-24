from math import *
# Cumulative normal distribution
def CND(X):
    (a1,a2,a3,a4,a5) = (0.31938153, -0.356563782, 1.781477937, -1.821255978, 1.330274429)
    L = abs(X)
    K = 1.0 / (1.0 + 0.2316419 * L)
    w = 1.0 - 1.0 / sqrt(2*pi)*exp(-L*L/2.) * (a1*K + a2*K*K + a3*pow(K,3) +
    a4*pow(K,4) + a5*pow(K,5))
    if X<0:
        w = 1.0-w
    return w

# Black Sholes Function
# S is current price, X is string, T is time to expiry (in years), r is risk-free interest rate, v is volatility
# on an annual basis, with 1.0 being 100% volatility.
def BlackSholes(CallPutFlag,S,X,T,r,v):
    d1 = (log(S/X)+(r+v*v/2.)*T)/(v*sqrt(T))
    d2 = d1-v*sqrt(T)
    if CallPutFlag=='c':
        return S*CND(d1)-X*exp(-r*T)*CND(d2)
    else:
        return X*exp(-r*T)*CND(-d2)-S*CND(-d1)

# brute-force, just try all volatilities in steps 
def ImpliedVolatility(CallPutFlag,S,X,T,r,p):
    bestV = None
    bestP = None
    NUM_STEPS = 1000
    MAX_VOLATILITY = 3.0
    for i in range(1,NUM_STEPS):
      v = float(i)/float(NUM_STEPS) * MAX_VOLATILITY
      pForV = BlackSholes(CallPutFlag,S,X,T,r,v)
      if bestP == None or abs(pForV - p) < abs(bestP - p):
          bestV = v
          bestP = pForV
    return bestV
      
p = BlackSholes('c', 100.0, 110.0, 1.0, 0.0, 0.20)
print ImpliedVolatility('c', 100.0, 110.0, 1.0, 0.0, p)

p = BlackSholes('c', 100.0, 110.0, 1.0, 0.0, 0.10)
print ImpliedVolatility('c', 100.0, 110.0, 1.0, 0.0, p)

p = BlackSholes('c', 110.0, 110.0, 1.0, 0.0, 0.10)
print ImpliedVolatility('c', 110.0, 110.0, 1.0, 0.0, p)

p = BlackSholes('c', 110.0, 100.0, 1.0, 0.0, 0.10)
print ImpliedVolatility('c', 110.0, 110.0, 1.0, 0.0, p)

p = BlackSholes('c', 100.0, 150.0, 1.0, 0.0, 1.00)
print ImpliedVolatility('c', 100.0, 150.0, 1.0, 0.0, p)
