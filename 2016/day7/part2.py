import re

# input_lines='''\
# aba[bab]xyz
# xyx[xyx]xyx
# aaa[kek]eke
# zazbz[bzb]cdb'''.splitlines()

input_lines = open('input.txt')

count = 0
for line in input_lines:
    supernet = re.split(r'\[\w+\]', line)
    hypernet = re.compile(r'\[(\w+)\]').findall(line)
    for part in supernet:
        matches = re.compile(r'(?=(\w)(\w)\1)').findall(part)
        found = False
        for match in matches:
            a, b = match
            bab = b + a + b
            if a != b and any(bab in h for h in hypernet):
                found = True
                break
        if found:
            count += 1
print(count)