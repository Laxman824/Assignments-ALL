import tkinter

class Graph:
    def __init__(self, root, height, width, count):
        self.root = root
        self.count = count
        self.width = width
        self.height = height
        self.x_end = 0
        self.x_start = 0
        self.y_end = 0
        self.y_start = 0
        self.plot_list = []
        self.route_list = []
        self.connect_list = []
        self.x_labels = []
        self.y_labels = []
        self.canvas =  tkinter.Canvas(root, bg="white", height=self.height, width=self.width)

    def getCanvas(self):
        return self.canvas
    
    def checkered(self):
        count = self.count + 3
        part_size_x = int((self.width)/(count))
        part_size_y = int((self.height)/(count))
        for x in range(2*part_size_x, count * part_size_x, part_size_x):
            self.canvas.create_line(x, part_size_x, x, part_size_y * count, fill="#476042")
        for y in range(2*part_size_y, count * part_size_y, part_size_y):
            self.canvas.create_line(part_size_y, y, part_size_x * count, y, fill="#476042")

    def displayText(self):
        count = self.count + 3
        part_size_x = int((self.width)/(count))
        part_size_y = int((self.width)/(count))
        n = 0
        for x in range(2 * part_size_x, count * part_size_x, part_size_x):
            self.canvas.create_text(x, int(part_size_y/2), text=str(self.x_labels[n]))
            n += 1
        n = 0
        for y in range(2 * part_size_y, count * part_size_y, part_size_y):
            self.canvas.create_text(int(part_size_x/2), y, text=str(self.y_labels[n]))
            n += 1

    def create_circle(self, x, y, r):
        x0 = x - r
        y0 = y - r
        x1 = x + r
        y1 = y + r
        return self.canvas.create_oval(x0, y0, x1, y1, fill="red")

    def getXInterval(self):
        return abs(int((self.x_end - self.x_start)/self.count))
    
    def getYInterval(self):
        return abs(int((self.y_end - self.y_start)/self.count))

    def setXLabels(self, start, end):
        temp_end = end
        temp_start = start
        if start > end:
            temp_end *= -1
            temp_start *= -1
        self.x_end = end
        self.x_start = start
        self.x_labels = [abs(n) for n in range(temp_start, temp_end + self.getXInterval(), self.getXInterval())]

    def setYLabels(self, start, end):
        temp_end = end
        temp_start = start
        if start > end:
            temp_end *= -1
            temp_start *= -1
        self.y_end = end
        self.y_start = start
        self.y_labels = [abs(n) for n in range(temp_start, temp_end + self.getYInterval(), self.getYInterval())]


    def plot(self, x, y, label=None):
        self.plot_list.append((x, y, label))

    def _plot(self, x, y, label=None):
        count = self.count + 3
        part_size_x = int((self.width)/(count))
        part_size_y = int((self.width)/(count))
        if self.x_start > self.x_end:
            x = self.x_start - x
        if self.y_start > self.y_end:
            y = self.y_start - y
        X = (x / self.getXInterval()) + 2
        Y = (y / self.getYInterval()) + 2
        self.create_circle(part_size_x * X, part_size_y * Y, 5)
        if label != None:
            self.canvas.create_text(part_size_x * X, part_size_y * Y - 15, text=label)

    def convert(self, x, y):
        count = self.count + 3
        part_size_x = int((self.width)/(count))
        part_size_y = int((self.width)/(count))
        if self.x_start > self.x_end:
            x = self.x_start - x
        if self.y_start > self.y_end:
            y = self.y_start - y
        X = (x / self.getXInterval()) + 2
        Y = (y / self.getYInterval()) + 2
        return (part_size_x * X), (part_size_y * Y)

    def clear(self):
        self.plot_list = []
        self.route_list = []
        self.connect_list = []
        self.canvas.delete("all")

    def connect(self, pointA, pointB, color=None):
        self.connect_list.append((pointA, pointB, color))

    def route(self, pointA, pointB, color="green"):
        self.route_list.append((pointA, pointB, color))

    def _drawLine(self, pointA, pointB, color=None):
        x0, y0 = self.convert(pointA[0], pointA[1])
        x1, y1 = self.convert(pointB[0], pointB[1])
        fill = color if color is not None else "black"
        self.canvas.create_line(x0, y0, x1, y1, width=3, fill=fill)

    def draw(self):
        self.checkered()
        self.displayText()
        for n in self.plot_list:
            self._plot(n[0], n[1], n[2])
        for n in self.connect_list:
            self._drawLine(n[0], n[1], n[2])
        for n in self.route_list:
            self._drawLine(n[0], n[1], color=n[2])