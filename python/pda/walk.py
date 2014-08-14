import numpy as np

trialLength = 10
numTrials = 4

unifProbs = np.random.rand(numTrials,trialLength)
 
stepMatrix = np.where(unifProbs > 0.5, 1, -1)

for i in range(trialLength):
    stepMatrix[:,i] = stepMatrix[:,i]*(i+1)

state = np.zeros(numTrials) 
 
for j in range(trialLength):
   newState = state + stepMatrix[:,j]
   state = newState

Threshold = 20
Succesful = (abs(state) > Threshold)
print sum(Succesful)
