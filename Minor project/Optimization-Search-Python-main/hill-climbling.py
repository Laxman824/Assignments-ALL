import copy
import math
import graph
import random
import tkinter
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


def hill_climbing(matrix, home, initial_state, max_iterations, mutation_rate=0.01):
    best_state = initial_state
    iterator = 0
    while True:
        neighbor = mutate(matrix, home, best_state, mutation_rate)
        if(neighbor.distance >= best_state.distance):
            iterator += 1
            if (iterator > max_iterations):
                break
        if(neighbor.distance < best_state.distance):
            best_state = neighbor
    return best_state


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
    state = hill_climbing(matrix, home, state, 1000, 0.1)

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
    root.title("Hill Climbing")
    root.geometry("{}x{}".format(WINDOW_WIDTH, WINDOW_HEIGHT))
    root.mainloop()
