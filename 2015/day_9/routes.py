import re
import itertools

distances = {}
places = set()

for line in open('input.txt'):
    a, b, distance = re.match(r'(\w+) to (\w+) = (\d+)', line).groups()
    distances[(a, b)] = int(distance)
    distances[(b, a)] = int(distance)
    places.update((a, b))

print(min(sum(distances[(route[i], route[i+1])] for i in range(len(route)-1))
          for route in itertools.permutations(places)))
