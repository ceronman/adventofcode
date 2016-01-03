
def missile(p1, p2):
    p2['hp'] -= 4

def drain(p1, p2):
    p1['hp'] += 2
    p2['hp'] -= 2

def shield(p1, p2):
    p1['armor'] = 7

def poison(p1, p2):
    p2['hp'] -= 3

def recharge(p1, p2):
    p1['mana'] += 101

spells = [
    {'mana': 53, 'action': missile},
    {'mana': 73, 'action': drain},
    {'mana': 113, 'duration': 6, 'action': shield},
    {'mana': 173, 'duration': 6, 'action': poison},
    {'mana': 229, 'duration': 5, 'action': recharge},
]

boss = {'hp': 71, 'damage': 10}
player = {'hp': 50, 'mana': 500, 'armor': 0, 'used_mana': 0}

queue = [(player.copy(), boss.copy(), spell, [0] * 5) for spell in range(5)]
used_mana = []
while queue:
    p1, p2, spell, timers = queue.pop()

    p1['hp'] -= 1
    if p1['hp'] <= 0:
        continue

    for i in range(5):
        if timers[i] > 0:
            spells[i]['action'](p1, p2)
            timers[i] -= 1

    if 'duration' in spells[spell] and timers[spell] <= 0:
        timers[spell] = spells[spell]['duration']
    else:
        spells[spell]['action'](p1, p2)
    p1['used_mana'] += spells[spell]['mana']
    p1['mana'] -= spells[spell]['mana']

    if p2['hp'] <= 0:
        used_mana.append(p1['used_mana'])
        continue

    p1['armor'] = 0
    for i in range(5):
        if timers[i] > 0:
            spells[i]['action'](p1, p2)
            timers[i] -= 1

    if p2['hp'] <= 0:
        used_mana.append(p1['used_mana'])
        continue

    p1['hp'] -= max(1, p2['damage'] - p1['armor'])
    if p1['hp'] <= 0:
        continue

    for spell in range(5):
        if spells[spell]['mana'] < p1['mana']:
            if not used_mana or p1['used_mana'] < min(used_mana):
                queue.append((p1.copy(), p2.copy(), spell, timers[:]))

print(min(used_mana))
