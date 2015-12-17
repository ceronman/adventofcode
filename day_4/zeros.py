import hashlib

key = b'iwrupvqb'
i = 0
while not hashlib.md5(key + str(i).encode()).hexdigest().startswith('00000'):
    i += 1

print(i)