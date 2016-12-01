from itertools import groupby

digits = '1113222113'
repeat = 40

for _ in range(repeat):
    digits = ''.join(str(len(list(v))) + k for k, v in groupby(digits))

print(len(digits))