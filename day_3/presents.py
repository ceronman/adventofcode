x, y = 0, 0
visited = {(x, y)}

for direction in open('input.txt').read():
    if direction == '>':
        x += 1
    elif direction == '<':
        x -= 1
    elif direction == '^':
        y += 1
    elif direction == 'v':
        y -= 1
    visited.add((x, y))

print(len(visited))
