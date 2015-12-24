from itertools import count, chain

def factors(n):
    return sum(chain(*[[i, n//i] for i in range(1, int(n**0.5) + 1) if n % i == 0]))

for i in count(1):
    print(i)
    if factors(i) * 10 >= 29000000:
        break
