# input_lines = '''\
# ADVENT
# A(1x5)BC
# (3x3)XYZ
# A(2x2)BCD(2x2)EFG
# (6x1)(1x3)A
# X(8x2)(3x3)ABCY'''.splitlines()

input_lines = open('input.txt')

NORMAL, MARKER, REPEAT = list(range(3))

for line in input_lines:
    result = ''
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
                    result += char
            elif state == MARKER:
                if char == 'x':
                    state = REPEAT
                else:
                    size += char
            elif state == REPEAT:
                if char == ')':
                    pattern = ''.join(next(chars) for _ in range(int(size)))
                    result += pattern * int(repeat)
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
        print(len(result))
