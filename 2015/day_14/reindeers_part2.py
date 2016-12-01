from itertools import cycle
import re


distances = {}
for line in open('input.txt'):
    m = re.match(r'(\w+) can fly (\d+) km/s for (\d+) seconds, '
                 r'but then must rest for (\d+) seconds.', line)
    reindeer, speed, time, rest = m.groups()
    distances[reindeer] = cycle([int(speed)] * int(time) + [0] * int(rest))

positions = {r: 0 for r in distances}
points = {r: 0 for r in distances}
for i in range(2503):
    for reinder, seconds in distances.items():
        positions[reinder] += next(seconds)
    lead_position = max(positions.values())
    for reinder, seconds in distances.items():
        if positions[reinder] == lead_position:
            points[reinder] += 1

print(max(points.values()))
