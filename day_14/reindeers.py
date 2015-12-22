from itertools import cycle, islice
import re


distances = {}
for line in open('input.txt'):
    m = re.match(r'(\w+) can fly (\d+) km/s for (\d+) seconds, '
                 r'but then must rest for (\d+) seconds.', line)
    reindeer, speed, time, rest = m.groups()
    distances[reindeer] = cycle([int(speed)] * int(time) + [0] * int(rest))

print(max(sum(islice(seconds, 0, 2503)) for seconds in distances.values()))
