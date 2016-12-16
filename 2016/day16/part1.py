# data = [bool(int(d)) for d in '10000']
# length = 20

data = [bool(int(d)) for d in '10011111011011001']
length = 272

while len(data) < length:
    data = data + [False] + [not d for d in reversed(data)]

data = data[:length]
while len(data) % 2 == 0:
    data = [data[i] == data[i+1] for i in range(0, len(data), 2)]

print(''.join(map(str, map(int, data))))
