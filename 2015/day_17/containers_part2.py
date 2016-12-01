def combinations(target, sizes, used):
    accumulated = sum(used)
    if accumulated < target:
        for i, s in enumerate(sizes):
            yield from combinations(target, sizes[i+1:], used + [s])
    elif accumulated == target:
        yield used

sizes = [int(x) for x in open('input.txt')]
all_combinations = list(combinations(150, sizes, []))
min_size = min(len(c) for c in all_combinations)
print(len([c for c in all_combinations if len(c) == min_size]))
