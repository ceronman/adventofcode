# first_row = '.^^.^.^^^^'
first_row = open('input.txt').read()

def next_row(row):
    new = ''
    for i in range(len(row)):
        l = row[i-1] if i > 0 else '.'
        c = row[i]
        r = row[i + 1] if i < len(row)-1 else '.'
        prev = l + c + r
        if prev in ('^^.', '.^^', '^..', '..^'):
            new += '^'
        else:
            new += '.'
    return new

rows = [first_row]
for i in range(1, 400000):
    rows.append(next_row(rows[i-1]))

print(sum(sum(1 for c in row if c == '.') for row in rows))