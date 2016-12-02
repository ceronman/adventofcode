# input_lines = """\
# ULL
# RRDDD
# LURDL
# UUUUD""".splitlines()

input_lines = open('input.txt')

numpad = [
    [' ', ' ', '1', ' ', ' '],
    [' ', '2', '3', '4', ' '],
    ['5', '6', '7', '8', '9'],
    [' ', 'A', 'B', 'C', ' '],
    [' ', ' ', 'D', ' ', ' '],
]

x, y = (0, 2) # 5 in numpad
result = ''
for line in input_lines:
    for char in line:
        current = (x, y)
        if char == 'L':
            x = max(x-1, 0)
        elif char == 'R':
            x = min(x+1, 4)
        elif char == 'U':
            y = max(y-1, 0)
        elif char == 'D':
            y = min(y+1, 4)
        if numpad[y][x] not in '123456789ABCD':
            x, y = current
    result += str(numpad[y][x])

print(result)