from hashlib import md5

seed = 'reyedfim'

i=0
result = [ '_' ] * 8
while '_' in result:
    h = md5()
    h.update((seed + str(i)).encode('ascii'))
    h = h.hexdigest()
    if h.startswith('00000'):
        pos, char = int(h[5], 16), h[6]
        if 0 <= pos < 8 and result[pos] == '_':
            result[pos] = char
    i += 1
print(''.join(result))