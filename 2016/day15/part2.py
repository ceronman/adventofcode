import re

input_lines = '''\
Disc #1 has 5 positions; at time=0, it is at position 4.
Disc #2 has 2 positions; at time=0, it is at position 1.'''.splitlines()

input_lines = open('input.txt')

input_re = re.compile(r'Disc #\d+ has (\d+) positions; at time=0, it is at position (\d+)')

discs = []
for line in input_lines:
    match = input_re.match(line)
    if match:
        discs.append(list(map(int, match.groups())))

discs.append([11, 0])

i=0
done = False
while not done:
    for n, (size, pos) in enumerate(discs, 1):
        if (pos + i + n) % size != 0:
            i += 1
            break
    else:
        done = True
print(i)

