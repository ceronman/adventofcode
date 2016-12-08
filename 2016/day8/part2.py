import re

# input_lines = '''\
# rect 3x2
# rotate column x=1 by 1
# rotate row y=0 by 4
# rotate column x=1 by 1'''.splitlines()

input_lines = open('input.txt')

WIDTH = 50
HEIGHT = 6

screen = [[0 for _ in range(WIDTH)] for _ in range(HEIGHT)]

def print_screen():
    for row in screen:
        print(''.join('#' if cell else ' ' for cell in row))

def rect(w, h):
    for i in range(h):
        for j in range(w):
            screen[i][j] = 1

def rotate_column(x, offset):
    col = [screen[i][x] for i in range(HEIGHT)]
    for i, val in enumerate(col):
        screen[(i + offset) % HEIGHT][x] = val

def rotate_row(y, offset):
    row = [screen[y][i] for i in range(WIDTH)]
    for i, val in enumerate(row):
        screen[y][(i + offset) % WIDTH] = val

for line in input_lines:
    match = re.match(r'rect (\d+)x(\d+)', line)
    if match:
        w, h = map(int, match.groups())
        rect(w, h)
        continue

    match = re.match(r'rotate column x=(\d+) by (\d+)', line)
    if match:
        x, offset = map(int, match.groups())
        rotate_column(x, offset)
        continue

    match = re.match(r'rotate row y=(\d+) by (\d+)', line)
    if match:
        y, offset = map(int, match.groups())
        rotate_row(y, offset)
        continue

print_screen()