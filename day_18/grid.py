matrix = [[dot == '#' for dot in line.strip()] for line in open('input.txt')]
diffs = [(x, y) for x in (-1, 0, 1) for y in (-1, 0, 1) if (x, y) != (0, 0)]

def neighbors(matrix, x, y):
    for i, j in diffs:
        if 0 <= i+x < len(matrix) and 0 <= y+j < len(matrix[0]):
            yield matrix[x+i][y+j]
        else:
            yield False

for i in range(100):
    new_matrix = [[False] * len(row) for row in matrix]
    for x in range(len(matrix)):
        for y in range(len(matrix[0])):
            neighbor_count = sum(neighbors(matrix, x, y))
            if matrix[x][y]:
                new_matrix[x][y] = neighbor_count in (2, 3)
            else:
                new_matrix[x][y] = neighbor_count == 3
    matrix = new_matrix

print(sum(sum(row) for row in matrix))
