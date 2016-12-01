from itertools import product
from functools import reduce
import re


properties = {}
for line in open('input.txt'):
    m = re.match(
        r'(\w+): capacity ([\-\d]+), durability ([\-\d]+), flavor ([\-\d]+), '
        r'texture ([\-\d]+)', line)
    ingredient, *props = m.groups()
    properties[ingredient] = [int(p) for p in props]

combinations = [c for c in product(range(100+1), repeat=len(properties))
                if sum(c) == 100]
total_scores = []
for combination in combinations:
    score = [0] * 4
    for teaspons, ingredient in zip(combination, sorted(properties)):
        for i, prop in enumerate(properties[ingredient]):
            score[i] += teaspons * prop
    total_scores.append(reduce(lambda a, b: max(a, 0) * max(b, 0), score))

print(max(total_scores))
