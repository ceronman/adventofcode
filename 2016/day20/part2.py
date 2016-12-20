input_lines = '''\
5-8
0-2
4-7
'''.splitlines()

input_lines = open('input.txt')

ranges = sorted(tuple(map(int, line.split('-'))) for line in input_lines)

current_start, current_end = ranges[0]
nr_allowed = current_start
for start, end in ranges[1:]:
    if start - current_end > 1:
        nr_allowed += start - current_end - 1
        current_start = start
        current_end = end
    elif end > current_end:
        current_end = end
nr_allowed += 4294967295 - current_end

print(nr_allowed)