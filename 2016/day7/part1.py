import re

# input_lines='''\
# abba[mnop]qrst
# abcd[bddb]xyyx
# aaaa[qwer]tyui
# ioxxoj[asdfgh]zxcvbn'''.splitlines()

input_lines = open('input.txt')

count = 0
for line in input_lines:
    hypernet = re.search(r'\[\w*(\w)(\w)(\2)(\1)\w*\]', line)
    regular = re.search(r'(\w)(\w)(\2)(\1)', line)
    if (not hypernet and regular and regular.groups()[0] != regular.groups()[1]):
        count += 1

print(count)