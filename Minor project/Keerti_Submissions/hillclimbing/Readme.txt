import random 

def initial_config(matrix): #here tsp is the list of cities 

  traverse = list(range(len(matrix)))
  result = []   # used to store thelist of all cities in a order
  c = len(matrix)

  for i in range(c):
    l = len(traverse)-1
    a = traverse[random.randint(0,l)] #choosing one city out of the list 
    
    result.append(a) # adding city to solution array
    
    traverse.remove(a)   #remvoing the city from city identifier list
    
  #print('random arrangement: ', solution)
  
  return result

def distance(matrix,result): #used for finding the length of route

  distance = 0             
  
  for i in range(len(result)):

    distance = distance + matrix[result[i-1]][result[i]]

  return distance

def findnext(result): #finding neighbour that only sightly different from result

  closest = []
  d =len(result)
  for i in range(d):#loop thoroughout the city 

    for j in range(i + 1,d):#cities the firstloop hasnt looped over yet in this for loop

      neighbour  = result.copy() #copying the current solution

      neighbour[i] = result[j]  #swap two cities and this way creating a

      neighbour[j] = result[i]  #different solution and exploring neighbours

      closest.append(neighbour)

  return closest #add to the neighbours list

def best_next(matrix,closest):

  bestdistance = distance(matrix,closest[0]) #starting from 1st place

  bestclosest =closest[0]

  for neighbour in closest:

    current_distance = distance(matrix,neighbour)

    if current_distance < bestdistance:#checking current and bestdist

      bestdistance = current_distance #if satisfies replace

      bestclosest = neighbour

  return bestclosest , bestdistance

def hillclimbing(matrix):
  currentsolution = initial_config(matrix)  #make a random solution  

  currentdistance = distance(matrix,currentsolution) # calculate routelength/distance

  closest = findnext(currentsolution)  #create neighbouring soln

  bestclosest,bestneighbourdistance  = best_next(matrix,closest) #take best out of neighbour

  print('first solution: ', bestclosest , ', ', bestneighbourdistance)

  while bestneighbourdistance < currentdistance: #repeat the process until bestneighbour is better
                                                #better than current one
    currentsolution = bestclosest

    currentdistance = bestneighbourdistance

    closest =findnext(currentsolution)

    bestclosest, bestneighbourdistance = best_next(matrix,closest)
  
  return currentsolution,currentdistance
  print('current solution: ', bestclosest , ', ', bestneighbourdistance)



def main():
    matrix = [
        [0, 200, 500, 650, 600, 150],
        [200, 0, 250, 700, 750, 300],
        [500, 250, 0, 150, 400, 500],
        [650, 700, 150, 0, 100, 200],
        [600, 750, 400, 100, 0, 150],
        [150, 300, 500, 200, 150, 0]
        # [0,10,20],
        # [10,0,20],
        # [20,30,0]
    ]
    # tsp = problemGenerator(6)#give the no.of vertices count here


    print('----------------------------------------')
    print('\n final solution', hillclimbing(matrix))

if __name__ == "__main__":
    main()



