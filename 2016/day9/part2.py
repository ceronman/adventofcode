# input_lines = '''\
# (3x3)XYZ
# X(8x2)(3x3)ABCY
# (27x12)(20x12)(13x14)(7x10)(1x12)A
# (25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN'''.splitlines()

input_lines = open('input.txt')

NORMAL, MARKER, REPEAT = list(range(3))

def count_decompress(line):
    result = 0
    chars = iter(line)
    state = NORMAL
    try:
        while True:
            char = next(chars)
            if char == ' ':
                continue

            if state == NORMAL:
                if char == '(':
                    size = ''
                    repeat = ''
                    state = MARKER
                else:
                    result += 1
            elif state == MARKER:
                if char == 'x':
                    state = REPEAT
                else:
                    size += char
            elif state == REPEAT:
                if char == ')':
                    pattern = ''.join(next(chars) for _ in range(int(size)))
                    result += count_decompress(pattern) * int(repeat)
                    state = NORMAL
                else:
                    repeat += char

            if char == '(':
                nr_chars = ''
                repeat = ''
                in_marker = True
                in_repeat = True
                continue
    except StopIteration:
        return result

for line in input_lines:
    print(count_decompress(line))