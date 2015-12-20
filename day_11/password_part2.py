import re

numerals = 'abcdefghijklmnopqrstuvwxyz'

def base26(num):
    if num == 0:
        return 'a'
    return base26(num // 26).lstrip('a') + numerals[num % 26]

def valid_password(password):
    if re.search(r'[iol]', password):
        return False
    if len(re.findall(r'(.)\1', password)) < 2:
        return False
    ords = [ord(c) for c in password]
    for i in range(len(ords) - 2):
        if ords[i] == ords[i+1]-1 and ords[i] == ords[i+2]-2:
            return True
    return False

start = sum(numerals.index(c) * 26**i for i, c in enumerate(reversed('cqjxxyzz')))

while True:
    start = start + 1
    password = base26(start).rjust(8, 'a')
    if len(password) > 8:
        start = 0
    if valid_password(password):
        print(password)
        break

