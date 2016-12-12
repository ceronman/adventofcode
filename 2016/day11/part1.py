import  re
from itertools import combinations, product

def print_state(state):
    e, floors = state
    for i in reversed(range(4)):
        chips, gens = floors[i]
        print(i, end=' ')
        print('E' if i==e else '.', end=' ')
        print(' '.join(str(c) + 'C' for c in chips), end=' ')
        print(' '.join(str(g) + 'G' for g in gens))
    print()

def is_valid(state):
    e, floors = state
    for chips, gens in floors:
        if gens and any(chip not in gens for chip in chips):
            return False
    return True

def move(state, dest, chips, gens):
    e, floors = state
    new_floors = [None] * 4
    for i, (floor_chips, floor_gens) in enumerate(floors):
        if i == e:
            new_floors[i] = (tuple(set(floor_chips) - set(chips)),
                             tuple(set(floor_gens) - set(gens)))
        elif i == dest:
            new_floors[i] = (tuple(set(chips + floor_chips)),
                             tuple(set(gens + floor_gens)))
        else:
            new_floors[i] = (floor_chips, floor_gens)
    return (dest, tuple(new_floors))

def possible_moves(state, dest):
    e, floors = state
    if 0 <= dest <= 3:
        src_chips, src_gens  = floors[e]
        chips = [()] + list(combinations(src_chips, 1))
        gens  = [()] + list(combinations(src_gens, 1))
        if dest > e:
            chips.extend(combinations(src_chips, 2))
            gens.extend(combinations(src_gens, 2))
        moves = [move(state, dest, c, g) for c, g in product(chips, gens) if 0 < len(c) + len(g) <= 2]
        return [m for m in moves if is_valid(m)]
    else:
        return []

def score(state):
    e, floors = state
    distances = []
    for i, (chips, gens) in enumerate(floors):
        for _ in range(len(chips) + len(gens)):
            distances.append(3 - i)
    return sum(distances)

def key(state):
    e, floors = state
    return (e, tuple((len(c), len(g)) for c, g in floors))

def is_solved(state):
    e, floors = state
    return all(len(c) + len(g) == 0 for c, g in floors[:-1])

def solve(state):
    seen = set()
    queue = [(0, state)]
    while queue:
        i, state = queue.pop(0)
        if is_solved(state):
            return i
        seen.add(key(state))
        e, _ = state
        for s in possible_moves(state, e + 1) + possible_moves(state, e - 1):
            if key(s) not in seen:
                queue.append((i+1, s))
                seen.add(key(s))

input_lines = '''\
The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
The second floor contains a hydrogen generator.
The third floor contains a lithium generator.
The fourth floor contains nothing relevant.'''.splitlines()

input_lines = open('input.txt')

floor_re = re.compile(r'^The (\w+) floor contains')
chip_re = re.compile(r'a (\w+)-compatible microchip')
generator_re = re.compile(r'a (\w+) generator')

floors = [None] * 4
floor_num = {'first': 0, 'second': 1, 'third': 2, 'fourth': 3}
material_num = {}
material_count = 0
for line in input_lines:
    floor_name, = floor_re.findall(line)
    chips = chip_re.findall(line)
    gens = generator_re.findall(line)
    floor = floor_num[floor_name]

    for item in chips + gens:
        if item not in material_num:
            material_num[item] = material_count
            material_count += 1

    floors[floor] = (tuple(material_num[c] for c in chips),
                     tuple(material_num[g] for g in gens))

state = (0, tuple(floors))
print(solve(state))
