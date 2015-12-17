total = 0

for line in open('input.txt'):
    l, w, h = [int(side) for side in line.split('x')]
    sides = [l*w, w*h, h*l]
    total += min(sides) + 2 * sum(sides)

print(total)
