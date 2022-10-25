import numpy as np

def baselSeries(n):
	s = np.empty(n)
	a = np.empty(n)
	s[0] = np.single(1)
	for i in range(1, n):
		s[i] = np.single(np.single(s[i-1])+(np.single(1)/np.single((i+1)**2)))

	a[0] = np.single(1)
	for i in range(1, n):
		a[i] = np.single(np.single((i+1)*s[i]) - (np.single(i)*np.single(s[i-1])))
	return (s, a)
