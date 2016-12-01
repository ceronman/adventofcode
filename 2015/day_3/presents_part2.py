position = position_santa = [0, 0]
position_robo = [0, 0]
visited = {(0, 0)}

for direction in open('input.txt').read():
    if direction == '>':
        position[0] += 1
    elif direction == '<':
        position[0] -= 1
    elif direction == '^':
        position[1] += 1
    elif direction == 'v':
        position[1] -= 1
    visited.add(tuple(position))
    position = position_robo if position is position_santa else position_santa

print(len(visited))
