import re

# input_lines = '''\
# aaaaa-bbb-z-y-x-123[abxyz]
# a-b-c-d-e-f-g-h-987[abcde]
# not-a-real-room-404[oarel]
# totally-real-room-200[decoy]'''.splitlines()

input_lines=open('input.txt')

sector_sum = 0
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
        sector_sum += int(sector)

print(sector_sum)