from collections import Counter

# input_lines = '''\
# eedadn
# drvtee
# eandsr
# raavrd
# atevrs
# tsrnev
# sdttsa
# rasrtv
# nssdts
# ntnada
# svetve
# tesnvt
# vntsnd
# vrdear
# dvrsen
# enarar'''.splitlines()

input_lines = open('input.txt')

counters = []
for line in input_lines:
    for i, char in enumerate(line):
        if i >= len(counters):
            counters.append(Counter())
        counters[i].update([char])

print(''.join(c.most_common(1)[0][0] for c in counters))