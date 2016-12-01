from itertools import count, chain

for n in count(1):
    factors = chain(*[[i, n//i] for i in range(1, int(n**0.5)+1) if not n % i])
    if sum(set(factors)) * 10 >= 29000000:
        print(n)
        break
