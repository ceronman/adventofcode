import re

# input_lines = '''\
# swap position 4 with position 0
# swap letter d with letter b
# reverse positions 0 through 4
# rotate left 1 step
# move position 1 to position 4
# move position 3 to position 0
# rotate based on position of letter b
# rotate based on position of letter d
# '''.splitlines()

input_lines = list(open('input.txt'))

# password = 'abcde'
password = 'abcdefgh'

swap_pos_re = re.compile(r'swap position (\d+) with position (\d+)')
swap_char_re = re.compile(r'swap letter (\w) with letter (\w)')
rotate_re = re.compile(r'rotate (left|right) (\d+) steps?')
rotate_pos_re = re.compile(r'rotate based on position of letter (\w)')
reverse_re = re.compile(r'reverse positions (\d+) through (\d+)')
move_re = re.compile(r'move position (\d+) to position (\d+)')

def swap_pos(word, x, y):
    chars = list(word)
    chars[x], chars[y] = chars[y], chars[x]
    return ''.join(chars)

def swap_char(word, a, b):
    return swap_pos(word, word.index(a), word.index(b))

def rotate(word, offset):
    return ''.join(word[i % len(word)] for i in range(-offset, len(word)-offset))

def rotate_pos(word, char):
    pos = word.index(char)
    if pos >= 4:
        pos += 1
    return rotate(word, pos + 1)

def reverse(word, x, y):
    return word[:x] + word[x:y+1][::-1] + word[y+1:]

def move(word, x, y):
    chars = list(word)
    chars.insert(y, chars.pop(x))
    return ''.join(chars)


from itertools import permutations

for test in permutations(list(password)):
    password = ''.join(test)
    for line in input_lines:
        m = swap_pos_re.match(line)
        if m:
            x, y = map(int, m.groups())
            password = swap_pos(password, x, y)
            continue

        m = swap_char_re.match(line)
        if m:
            a, b = m.groups()
            password = swap_char(password, a, b)
            continue

        m = rotate_re.match(line)
        if m:
            side, steps = m.groups()
            offset = int(steps) * (1 if side == 'right' else -1)
            password = rotate(password, offset)
            continue

        m = rotate_pos_re.match(line)
        if m:
            char, = m.groups()
            password = rotate_pos(password, char)
            continue

        m = reverse_re.match(line)
        if m:
            x, y = map(int, m.groups())
            password = reverse(password, x, y)
            continue

        m = move_re.match(line)
        if m:
            x, y = map(int, m.groups())
            password = move(password, x, y)
            continue

        raise Exception("No match: " + repr(line))

    if password == 'fbgdceah':
        print(''.join(test))
        break