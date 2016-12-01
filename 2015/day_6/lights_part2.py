grid = [[0] * 1000 for _ in range(1000)]

for line in open('input.txt'):
    command, start, _, end = line.rsplit(' ', 3)
    start_x, start_y = [int(c) for c in start.split(',')]
    end_x, end_y = [int(c) for c in end.split(',')]

    for y in range(start_y, end_y+1):
        for x in range(start_x, end_x+1):
            if command == 'turn on':
                grid[y][x] += 1
            elif command == 'turn off':
                grid[y][x] = max(0, grid[y][x] - 1)
            elif command == 'toggle':
                grid[y][x] += 2

print(sum(sum(row) for row in grid))
