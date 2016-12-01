import re

tape = {
    'children': (3, 'exact'),
    'cats': (7, 'more'),
    'samoyeds': (2, 'exact'),
    'pomeranians': (3, 'less'),
    'akitas': (0, 'exact'),
    'vizslas': (0, 'exact'),
    'goldfish': (5, 'less'),
    'trees': (3, 'more'),
    'cars': (2, 'exact'),
    'perfumes': (1, 'exact'),
}

scores = {}
for aunt, line in enumerate(open('input.txt'), 1):
    props = {name: int(num) for name, num in re.findall(r'(\w+): (\d+)', line)}
    for key, (value, comp) in tape.items():
        if (comp == 'exact' and key in props and value == props[key]
                or comp == 'more' and key in props and value < props[key]
                or comp == 'less' and key in props and value > props[key]):
            scores[aunt] = scores.get(aunt, 0) + 1

print(max(scores, key=lambda k: scores[k]))