import numpy as np
import scipy as sp
import matplotlib.pyplot as plt


x,y=np.ogrid[-2:1:1000j,-1.5:1.5:1000j]

print('')
print('Grid set')
print('')

c=x + 1j*y
z=0

for g in range(1000):
        print('Iteration number: ',g)
        z=z**2 + c

threshold = 1 # can use larger/smaller values too
mask=np.abs(z) < threshold

print('')
print('Plotting using imshow()')
plt.imshow(mask.T,extent=[-2,1,-1.5,1.5])

print('')
print('plotting done')
print('')

plt.gray()

print('')
print('Preparing to render')
print('')

plt.show()
