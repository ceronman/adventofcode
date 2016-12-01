import re

replacements = []
medicine = None
for line in (l.strip() for l in open('input.txt')):
    if '=>' in line:
        replacements.append(line.split(' => '))
    elif line:
        medicine = line

combinations = set()
for old, new in replacements:
    for m in re.finditer(old, medicine):
        i = m.start()
        combinations.add(medicine[:i] + medicine[i:].replace(old, new, 1))

print(len(combinations))