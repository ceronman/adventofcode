import re

# input_lines = '''\
# aaaaa-bbb-z-y-x-123[abxyz]
# a-b-c-d-e-f-g-h-987[abcde]
# not-a-real-room-404[oarel]
# totally-real-room-200[decoy]'''.splitlines()

input_lines=open('input.txt')

good =[]
for line in input_lines:
    match = re.match(r'^([a-z-]+)([0-9]+)\[([a-z]+)\]', line)
    name, sector, checksum = match.groups()
    count = {}
    for letter in name:
        if not letter.isalpha():
            continue
        count[letter] = count.get(letter, 0) + 1
    decoded = ''.join(sorted(count, key=lambda x: (-count[x], x)))
    if decoded.startswith(checksum):
        good.append((name, int(sector)))

# good = [('qzmt-zixmtkozy-ivhz-', 343)]

def rotate(word, n):
    result = ''
    for char in word:
        pos = ord(char) - ord('a') + n
        result += chr(pos % 26 + ord('a'))
    return result

for name, sector in good:
     print(sector, ' '.join(rotate(p, sector) for p in name.split('-') if p))