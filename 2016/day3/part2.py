input_lines = open('input.txt')

good = 0
col1, col2, col3 = [], [], []
for line in input_lines:
    s1, s2, s3 = map(int, line.split())
    col1.append(s1)
    col2.append(s2)
    col3.append(s3)

    if len(col1) == 3:
        for col in (col1, col2, col3):
            a, b, c = col[:3]
            del col[:3]
            if a+b > c and a+c > b and b+c > a:
                good += 1

print(good)