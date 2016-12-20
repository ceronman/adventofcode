# input_lines = '''\
# 5-8
# 0-2
# 4-7
# '''.splitlines()

input_lines = open('input.txt')

ranges = sorted(tuple(map(int, line.split('-'))) for line in input_lines)

merged = []

current_start, current_end = ranges[0]
for start, end in ranges[1:]:
    if start - current_end > 1:
        merged.append((current_start, current_end))
        current_start = start
        current_end = end
    elif end > current_end:
        current_end = end
merged.append((current_start, current_end))

print(merged[0][1] + 1)