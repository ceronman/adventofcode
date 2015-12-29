from itertools import count

for n in count(1):
    factors = set()
    for i in range(1, int(n**0.5)+1):
        if n % i == 0:
            if n/i <= 50:
                factors.add(i)
            if i <= 50:
                factors.add(n//i)
    if sum(factors) * 11 >= 29000000:
        print(n)
        break
