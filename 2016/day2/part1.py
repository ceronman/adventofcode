# input_lines = """\
# ULL
# RRDDD
# LURDL
# UUUUD""".splitlines()

input_lines = open('input.txt')

numpad = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9],
]

x, y = (1, 1) # 5 in numpad
result = ''
for line in input_lines:
    for char in line:
        if char == 'L':
            x = max(x-1, 0)
        elif char == 'R':
            x = min(x+1, 2)
        elif char == 'U':
            y = max(y-1, 0)
        elif char == 'D':
            y = min(y+1, 2)
    result += str(numpad[y][x])

print(result)