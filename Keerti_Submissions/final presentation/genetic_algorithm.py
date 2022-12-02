import matplotlib.pyplot as plt 

import numpy as np, random, operator
import pandas as pd


def pick(N):

    i=random.randint(0,N)    
    return i

def dist_bw_nodes(dist_bw_x,dist_bw_y):

    return np.sqrt((dist_bw_x[0]-dist_bw_y[0])**2 + (dist_bw_x[1]-dist_bw_y[1])**2)

def createpopulation(num,List_of_cities):

    GA_population = []
    
    for i in range(0,num):
        GA_population.append(new_node_created(List_of_cities))
        
    return GA_population


def pop_pathranks(GA_population, LIstof_city):  

    pathrank = []
  
    for i in GA_population:
       
        pathrank.append(fitness_factor(i, LIstof_city))
       
    return pathrank

def fitness_factor(pathd,LIstof_city):
 
    fit=0
    m = len(pathd)
    for i in range(1,m):
        a=int(pathd[i-1])
        b=int(pathd[i])

        fit = fit + dist_bw_nodes(LIstof_city[a],LIstof_city[b])
    return fit

def new_node_created(List_of_cities):
    '''
    creating new member of the GA_population
    '''
    new_pop=set(np.arange(List_of_cities,dtype=int))
    pathd=list(random.sample(new_pop,List_of_cities))
            
    return pathd

def crossover_bw_fit(a,b):
    '''
    cross over 
    a=route1
    b=route2
    return heredity
    '''
    heredity=[]
    heredityA=[]
    heredityB=[]
    
    
    geneticA=int(random.random()* len(a))
    geneticB=int(random.random()* len(a))
    
    initial_gene=min(geneticA,geneticB)
    ending_gene=max(geneticA,geneticB)
    
    for i in range(initial_gene,ending_gene):
        heredityA.append(a[i])
        
    heredityB=[item for item in a if item not in heredityA]
    heredity = heredityA+heredityB
     
    return heredity

def mutation(pathd,mut_prob):

    pathd=np.array(pathd)
    for j in range(len(pathd)):
        if(random.random() < mut_prob):
            swap = np.random.randint(0,len(pathd))
            
            temp1=pathd[j]
            
            temp2=pathd[swap]
            pathd[swap]=temp1
            pathd[j]=temp2
    
    return pathd

def selection(best_fit, elit_size):
    select_res=[]
    results=[]
    for i in best_fit:
        results.append(i[0])
    for i in range(0,elit_size):
        select_res.append(results[i])
    
    return select_res

def rankpaths(GA_population,Cities_List):
    fitfactor_result = {}
    h =len(GA_population)
    for i in range(0,h):
        fitfactor_result[i] = fitness_factor(GA_population[i],Cities_List)
        b = operator.itemgetter(1)
    return sorted(fitfactor_result.items(), key = b, reverse = False)

def pop_breed(mate_pool):
    offspring=[]
    n = len(mate_pool)
    for i in range(n-1):
            offspring.append(crossover_bw_fit(mate_pool[i],mate_pool[i+1]))
    return offspring

def pop_mutation(offspring,mut_rate):
    gen_new=[]
    for i in offspring:
        mutated_offspring=mutation(i,mut_rate)
        gen_new.append(mutated_offspring)
    return gen_new

def matingPool(GA_population, select_res):
    rand1 = []
    p = len(select_res)
    for i in range(0, p):
        w = select_res[i]
        rand1.append(GA_population[w])
    return rand1

def next_gen(Cities_List,curr_pop,mut_rate,elite_size):
    population_rank=rankpaths(curr_pop,Cities_List)

    
    selection_result=selection(population_rank,elite_size)
   
    
    mate_pool=matingPool(curr_pop,selection_result)

    
    offspring=pop_breed(mate_pool)
    
    next_gen=pop_mutation(offspring,mut_rate)
    
    return next_gen

def gene_algo(Cities_List,size_population=1000,elite_size=75,mut_rate=0.01,generation=200):
 
    new_pop=[] 
    progress = []
    
    Number_of_cities=len(Cities_List)
    
    GA_population=createpopulation(size_population,Number_of_cities)
    
    progress.append(rankpaths(GA_population,Cities_List)[0][1])
    
    print(f"initial path distance between the cities  {progress[0]}")
    print(f"initial path is  {GA_population[0]}")
    progress = []
    for i in range(0,generation):
        new_pop = next_gen(Cities_List,GA_population,mut_rate,elite_size)
        progress.append(rankpaths(new_pop,Cities_List)[0][1])
    plt.title(f"(GENETIC ALGORITHM) Relation in generation and best result ")
    plt.xlabel("generation")
    plt.ylabel("progress")
    x = [i for i in range(0, generation)]
    y= progress

    rank_=rankpaths(new_pop,Cities_List)[0]
    print(f"best pathd dist_bw_nodes {rank_[1]}")
    print(f"Best path of all cities are  :{new_pop[rank_[0]]} ")
    
    plt.plot(x,y)
    plt.show()
    
    return rank_, new_pop


LIstof_city = []

for i in range(0,15):   # here give the range of to  check/verify

    v = random.random() * 200
    rand1=int(v)
    rand2=int(v)
    LIstof_city.append((rand1,rand2))

rank_,new_pop=gene_algo(Cities_List=LIstof_city)

coordinate_x=[]
coordinate_y=[]
for i in LIstof_city:
    coordinate_x.append(i[0])
    coordinate_y.append(i[1])
