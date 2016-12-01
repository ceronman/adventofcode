nice = 0

for word in open('input.txt'):
    vowels = 0
    bad = 0
    doubled = 0
    for i in range(len(word)):
        if word[i:i+2] in ('ab', 'cd', 'pq', 'xy'):
            bad += 1
        if word[i] in 'aeiou':
            vowels += 1
        if i < len(word)-1 and word[i] == word[i+1]:
            doubled += 1
    if vowels >= 3 and bad == 0 and doubled >= 1:
        nice += 1

print(nice)
