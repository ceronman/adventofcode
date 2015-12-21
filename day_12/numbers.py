import re
json = open('input.json').read()
print(sum(int(n) for n in re.findall(r'([\-\.\d]+)', json)))
