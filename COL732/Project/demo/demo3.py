'''
rotating donut in ascii art
'''
import math
import time
import os
import sys


screen_width = 80
screen_height = 40
screen = [[' ' for x in range(screen_width)] for y in range(screen_height)]
def draw_lines(point1, point2, screen):
    # draw line from point1 to point2 in screen
    x1, y1 = point1
    x2, y2 = point2
    if x1 == x2:
        for i in range(min(y1, y2), max(y1, y2)):
            screen[i][x1] = '|'
    elif y1 == y2:
        for i in range(min(x1, x2), max(x1, x2)):
            screen[y1][i] = '-'
    else:
        m = (y2 - y1) / (x2 - x1)
        c = y1 - m * x1
        for i in range(min(x1, x2), max(x1, x2)):
            y = int(m * i + c)
            screen[y][i] = '*'
    return screen

def draw_circle(center, radius, screen):
    # draw circle with center and radius in screen
    x, y = center
    for i in range(360):
        x1 = int(math.cos(math.radians(i)) * radius + x)
        y1 = int(math.sin(math.radians(i)) * radius + y)
        screen[y1][x1] = '.'
    return screen

def draw_donut(screen, k):
    # draw donut in screen
    for i in range(360):
        for j in range(360):
            r = 1 + math.sin(math.radians(j))
            x = int(math.cos(math.radians(i)) * r * 10 + 40)
            y = int(math.sin(math.radians(i)) * r * 10 + 20)
            z = int(math.cos(math.radians(j)) * 8 * math.sin(math.radians(i)) * r + k)
            o = int(math.sin(math.radians(j)) + 0.5)
            l = int(math.cos(math.radians(j)) * math.cos(math.radians(i)) * r)
            screen[y][x] = '.,-~:;=!*#$@'[int((z + l) * o)]
    return screen

while(True):
    os.system('clear')
    for y in range(screen_height):
        for x in range(screen_width):
            print(screen[y][x], end='')
        print()
    time.sleep(1)
    screen = [[' ' for x in range(screen_width)] for y in range(screen_height)]
    t = time.localtime()
    hour = t.tm_hour
    minute = t.tm_min
    second = t.tm_sec
    hour_angle = (hour % 12) * 30 + minute * 0.5
    minute_angle = minute * 6
    second_angle = second * 6
    hour_x = int(math.cos(math.radians(hour_angle)) * 10 + 40)
    hour_y = int(math.sin(math.radians(hour_angle)) * 10 + 20)
    minute_x = int(math.cos(math.radians(minute_angle)) * 15 + 40)
    minute_y = int(math.sin(math.radians(minute_angle)) * 15 + 20)
    second_x = int(math.cos(math.radians(second_angle)) * 20 + 40)
    second_y = int(math.sin(math.radians(second_angle)) * 20 + 20)
    screen[hour_y][hour_x] = 'H'
    screen[minute_y][minute_x] = 'M'
    screen[second_y][second_x] = 'S'
    screen = draw_donut(screen, time.time())
    sys.stdout.flush()

if __name__ == '__main__':
    pass