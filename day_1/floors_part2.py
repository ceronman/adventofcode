floor = 0

for i, char in enumerate(open('input.txt').read(), start=1):
    if char == '(':
        floor += 1
    elif char == ')':
        floor -= 1
    if floor < 0:
        print(i)
        break
