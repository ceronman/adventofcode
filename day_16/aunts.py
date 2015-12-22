import re

tape = {
    'children': 3,
    'cats': 7,
    'samoyeds': 2,
    'pomeranians': 3,
    'akitas': 0,
    'vizslas': 0,
    'goldfish': 5,
    'trees': 3,
    'cars': 2,
    'perfumes': 1,
}

scores = {}
for aunt, line in enumerate(open('input.txt'), 1):
    props = {name: int(num) for name, num in re.findall(r'(\w+): (\d+)', line)}
    for key, value in tape.items():
        if key in props and value == props[key]:
            scores[aunt] = scores.get(aunt, 0) + 1

print(max(scores, key=lambda k: scores[k]))