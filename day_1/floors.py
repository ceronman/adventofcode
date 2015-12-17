floor = 0

for char in open('input.txt').read():
    if char == '(':
        floor += 1
    elif char == ')':
        floor -= 1

print(floor)