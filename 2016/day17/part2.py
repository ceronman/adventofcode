from hashlib import md5

# passcode = 'ihgpwlah'
# passcode = 'kglvqrro'
# passcode = 'ulqzkmiv'
passcode = 'pgflpeqp'

def open_doors(code):
    h = md5()
    h.update(code.encode('ascii'))
    md5hash = h.hexdigest()
    return (c for i, c in enumerate('UDLR') if md5hash[i] in 'bcdef')

def position(path):
    x, y = 0, 0
    for c in path:
        if c == 'U':
            y -= 1
        elif c == 'D':
            y += 1
        elif c == 'L':
            x -= 1
        elif c == 'R':
            x += 1
    return (x, y)

queue = ['']
solutions = set()
while queue:
    path = queue.pop(0)
    x, y = position(path)
    if not 0 <= x < 4 or not 0 <= y < 4:
        continue
    if x == 3 and y == 3:
        solutions.add(len(path))
        continue

    queue.extend(path + d for d in open_doors(passcode + path))

print(max(solutions))