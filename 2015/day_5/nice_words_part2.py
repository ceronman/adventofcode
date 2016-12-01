nice = 0

# Don't use regexes they said, it'd be fun they said...

for word in open('input.txt'):
    pairs_pos = {}
    double_pair = 0
    repeats = 0
    for i in range(len(word)):
        if i < len(word)-2 and word[i] == word[i+2]:
            repeats += 1

        pair = word[i:i+2]
        if len(pair) == 2 and pairs_pos.get(pair, i) != i:
            double_pair += 1
        pairs_pos[pair] = i + 1

    if double_pair >= 1 and repeats >= 1:
        nice += 1

print(nice)
