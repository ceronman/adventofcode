import re
import itertools


point_diff = {'Manuel': {}}
for line in open('input.txt'):
    m = re.match(r'(\w+) would (gain|lose) (\d+) happiness '
                 r'units by sitting next to (\w+)', line)
    person1, sign, points, person2 = m.groups()
    sign = '-' if sign == 'lose' else ''
    point_diff.setdefault(person1, {})[person2] = int(sign + points)

totals = []
for comb in itertools.permutations(point_diff.keys()):
    total = 0
    for i, person in enumerate(comb):
        total += point_diff[person].get(comb[i-1], 0)
        total += point_diff[person].get(comb[i+1 if i < len(comb)-1 else 0], 0)
    totals.append(total)
print(max(totals))
