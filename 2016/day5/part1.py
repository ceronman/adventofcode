from hashlib import md5

seed = 'reyedfim'

i=0
result = ''
while len(result) < 8:
    h = md5()
    h.update((seed + str(i)).encode('ascii'))
    h = h.hexdigest()
    if h.startswith('00000'):
        result += h[5]
    i += 1
print(result)