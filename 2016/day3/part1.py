input_lines = open('input.txt')

good = 0
for line in input_lines:
    a,b,c = map(int, line.split())
    if a+b > c and a+c > b and b+c > a:
        good += 1

print(good)