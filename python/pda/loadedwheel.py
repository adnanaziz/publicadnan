import numpy as np
import scipy as sp

trialLength = 100
startingStake = 20
maxBet = startingStake

for numTrials in [10,100,1000,10000]:

  # cleaner output:  
  np.set_printoptions(precision=1)
  
  unifProbs = np.random.rand(numTrials,maxBet,trialLength)
  # win 1-18/37 of the time, i.e., 19/37 is win prob
  winProbs = unifProbs > (18.0/37.0)
 
  # outcomes for numTrials x maxBet values, each trial is trialLength plays
  delta = np.zeros((numTrials,maxBet,trialLength))
  for i in range(0,maxBet):
      delta[:,i,:] = np.where(winProbs[:,i,:], (i+1), -(i+1))

  state = np.zeros((numTrials,maxBet)) 
  state[:] = startingStake # initial values
 
  # outer loop is unavoidable 
  for j in range(trialLength):
      newState = np.zeros(state.shape)
      # can you think of a way of avoiding this loop?
      for i in range(0,maxBet):
          newState[:,i] = state[:,i] + (state[:, i] >= (i+1))*delta[:,i, j]
      state = newState
  
  print "fixed bet mean:", state.mean(0)

  # kelly bet: 1/37 of current bankroll
  unifProbsK = np.random.rand(numTrials,trialLength)
  winProbsK = unifProbsK > (18.0/37.0)
  deltaK = np.where(winProbsK, 1, -1)
  
  stateK = np.zeros(numTrials)
  stateK[:] = startingStake
  for j in range(trialLength):
    newStateK = np.zeros(stateK.shape)
    newStateK = stateK + (stateK*(1.0/37.0) * deltaK[:,j])
    stateK = newStateK
  
  print "kelly bet mean:", stateK.mean()
