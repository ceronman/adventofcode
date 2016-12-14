import re
from hashlib import md5

# salt = 'abc'
salt = 'jlmsuwbz'

repeated3 = re.compile(r'([0-9a-f])\1\1')
repeated5 = re.compile(r'([0-9a-f])\1\1\1\1')

def genhash(i):
    base = salt + str(i)
    for _ in range(2017):
        h = md5()
        h.update(base.encode('ascii'))
        base = h.hexdigest()
    return base

i = 0
keys = []
hashes = []
while len(keys) < 64:
    h = genhash(i)
    found = {}
    match = repeated3.search(h)
    if match:
        char, = match.groups()
        found[char] = found.get(char, 0) + 1

    for char in repeated5.findall(h):
        found[char] = found.get(char, 0) + 1
    hashes.append(found)
    if len(hashes) > 1000:
        for char in hashes.pop(0).keys():
            for f in hashes:
                if f.get(char, 0) == 2:
                    keys.append(i - 1000)
                    break
    i+=1

print(keys[-1])