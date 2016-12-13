# K = 10
# dest = (7,4)
K = 1358
dest = (31,39)

def is_empty(pos):
    x, y = pos
    if x < 0 or y < 0:
        return False
    num = x*x + 3*x + 2*x*y + y + y*y + K
    return sum(map(int, format(num, 'b'))) % 2 == 0

queue = [(0, (1, 1))]
seen = {(1, 1)}
while queue:
    i, (x, y) = queue.pop(0)
    if (x, y) == dest:
        print(i)
        break
    for pos in [(x+1, y), (x-1, y), (x, y+1), (x, y-1)]:
        if is_empty(pos) and pos not in seen:
            queue.append((i+1, pos))
            seen.add(pos)