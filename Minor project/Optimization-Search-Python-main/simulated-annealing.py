import sys
import copy
import math
import graph
import random
import tkinter
import numpy as np

from state import State

WINDOW_WIDTH = 400
WINDOW_HEIGHT = 400
CANVAS_WIDTH = 400
CANVAS_HEIGHT = 400

def distance(a, b):
    return math.sqrt(abs((a[0] - b[0]) ** 2 - (a[1] - b[1]) ** 2))

def createDistanceMatrix(points):
    matrix = []
    for a in points:
        matrix.append([])
        for b in points:
            matrix[-1].append(distance(a, b))
    return matrix


class Point:
    def __init__(self, index, distance=0):
        self.index = index
        self.distance = distance

    def __lt__(self, other):
        return self.distance < other.distance


def get_random_solution(matrix, home, indexes, size):
    points = indexes.copy()
    points.pop(home)
    population = []
    for i in range(size):
        random.shuffle(points)
        state = State(points[:])
        state.update_distance(matrix, home)
        population.append(state)
    population.sort()
    return population[0]


def mutate(matrix, home, state, mutation_rate=0.01):
    mutated_state = state.deepcopy()
    for i in range(len(mutated_state.route)):
        if(random.random() < mutation_rate):
            j = int(random.random() * len(state.route))
            point_1 = mutated_state.route[i]
            point_2 = mutated_state.route[j]
            mutated_state.route[i] = point_2
            mutated_state.route[j] = point_1
    mutated_state.update_distance(matrix, home)
    return mutated_state

def probability(p):
    return p > random.uniform(0.0, 1.0)

def exp_schedule(k=20, lam=0.002, limit=5000):
    return lambda t: (k * np.exp(-lam * t) if t < limit else 0)

def simulated_annealing(matrix, home, initial_state, mutation_rate, schedule=exp_schedule()):
    best_state = initial_state
    start = True
    for t in range(sys.maxsize):
        T = 5000 if start else schedule(t)
        start = False
        if T == 0:
            return best_state
        neighbor = mutate(matrix, home, best_state, mutation_rate)
        delta_e = best_state.distance - neighbor.distance
        if delta_e > 0 or probability(np.exp(delta_e/ T)):
            best_state = neighbor



if __name__ == "__main__":
    # Create a window
    root = tkinter.Tk()

    Graph = graph.Graph(root, CANVAS_HEIGHT, CANVAS_WIDTH, 10)
    Graph.setXLabels(000, 200)
    Graph.setYLabels(200, 000)
    Graph.getCanvas().grid(row=1, columnspan=6)

    indexes = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17]

    points = [
        (0,     200),  # 1
        (180,   200),  # 2
        (80,    180),  # 3
        (140,   180),  # 4
        (100,   160),  # 5
        (200,   160),  # 6
        (140,   140),  # 7
        (40,    120),  # 8
        (80,    120),  # 9
        (180,   100),  # 10
        (60,     80),  # 11
        (100,    80),  # 12
        (160,    80),  # 13
        (20,     40),  # 14
        (100,    40),  # 15
        (200,    40),  # 16
        (60,     20),  # 17
        (120,    20),  # 18
    ]

    home = 0
    matrix = createDistanceMatrix(points) # A lazy optimization
    state = get_random_solution(matrix, home, indexes, 18)
    state = simulated_annealing(matrix, home, state, 0.05)

    print(state)

    for n in points:
        Graph.plot(n[0], n[1])

    solution = copy.deepcopy(state.route)

    curr = home
    while len(solution) != 0:
        nxt = solution.pop()
        Graph.connect(points[curr], points[nxt])
        curr = nxt

    Graph.connect(points[curr], points[home])
    # Render the graph to the screen
    Graph.draw()

    # Configure and Start the window
    root.title("Simulated annealing")
    root.geometry("{}x{}".format(WINDOW_WIDTH, WINDOW_HEIGHT))
    root.mainloop()
