import re

replacements = []
medicine = None
for line in (l.strip() for l in open('input.txt')):
    if '=>' in line:
        replacements.append(line.split(' => '))
    elif line:
        medicine = line

replacements.sort(key=lambda x: x[1])
stack = [(medicine, 0)]
while stack:
    seed, step = stack.pop()
    if seed == 'e':
        print(step)
        break
    for old, new in replacements:
        for m in re.finditer(new, seed):
            i = m.start()
            prev = seed[:i] + seed[i:].replace(new, old, 1)
            stack.append((prev, step+1))
