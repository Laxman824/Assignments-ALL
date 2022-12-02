import matplotlib.pyplot as plt
import numpy as np
def TSP_with_annealing(matrix):                              
   soln = list(range(len(matrix)))
   w = value(matrix, soln)
   ntrial = 1                             #starting with trail =1
   T = 30                                 #setting the temparature 
   alpha = 0.99                           #rate of which temparature should decrease  
   x = []
   y =[]
   while ntrial <= 1000:                  #number of iterations till 1000
      e = np.random.randint(0, len(matrix))    #initially taking  a random b\w
      while True:                         #m,n are the initial randomly choosen positions 
         f = np.random.randint(0, len(matrix))
         if e != f:                       #takig other m which is not equal to previously taken vertex
            break
      s1 = exchange(soln, f, e)                  #swap two vertexes to get explore 
      w1 = value(matrix, s1) 
      delta=np.exp(-(w1 - w)/T)                   #finding the cost of the

      if(ntrial%50 == 0):
        x.append(ntrial)
        y.append(w1)
        print(s1)
        print(w1)
      if w1 < w:                          # comparing the cost of c1(current cost) with previous 
         soln, w = s1, w1                    # if it is better replace the best with current
      else:
         if delta > np.random.rand():  #allow but if it satisfies acceptance probability
            soln, c = s1, w1                 #exp()
      T = alpha*T                         #if it doesnt satisfy just decrease the temp with alpha and increase the iteration &repeat
      ntrial += 1
  #  print("The Tsp path computed with simulated annealing ")
   print("The best solution found is :")
   print(soln)
   plt.title(f"Relation blw iterations and solution of each iteration")
   plt.xlabel("iteration")
   plt.ylabel("best soln")
   plt.plot(x,y)
   plt.show()
   print(w1)
   
   return ntrial
def exchange(soln, u, v):
   a, b = min(u, v), max(u, v)
   p = soln.copy()
   while a < b:
      p[a], p[b] = p[b], p[a]
      a = a+1
      b = b-1
   return p
  
def value(mat, soln):
   a = 0
   for i in range(len(soln)-1):
      a = a+ mat[soln[i]][soln[i+1]]
   a = a+ mat[soln[len(soln)-1]][soln[0]] 
   return a


def main():
  print("TSP computation of path starts here ")
  G = [
        [0, 200, 500, 650, 600, 150],
        [200, 0, 250, 700, 750, 300],
        [500, 250, 0, 150, 400, 500],
        [650, 700, 150, 0, 100, 200],
        [600, 750, 400, 100, 0, 150],
        [150, 300, 500, 200, 150, 0]]

  TSP_with_annealing(G)


if __name__ == "__main__":
  main()

