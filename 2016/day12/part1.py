# input_lines = '''\
# cpy 41 a
# inc a
# inc a
# dec a
# jnz a 2
# dec a'''.splitlines()

input_lines = open('input.txt')

regs = {r:0 for r in 'abcd'}
pc = 0
program = list(input_lines)

while pc < len(program):
    ins, *data = program[pc].split()
    if ins == 'cpy':
        regs[data[1]] = int(regs.get(data[0], data[0]))
    elif ins == 'inc':
        regs[data[0]] += 1
    elif ins == 'dec':
        regs[data[0]] -= 1
    elif ins == 'jnz':
        if int(regs.get(data[0], data[0])):
            pc += int(data[1])
            continue
    else:
        raise Exception('bad instruction')
    pc += 1

print(regs['a'])