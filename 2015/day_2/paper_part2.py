total = 0

for line in open('input.txt'):
    l, w, h = [int(side) for side in line.split('x')]
    sides = [2*(l+w), 2*(w+h), 2*(h+l)]
    total += min(sides) + l*w*h

print(total)
