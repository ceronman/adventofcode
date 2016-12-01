boss = {'hp': 100, 'damage': 8, 'armor': 2}
player = {'hp': 100, 'damage': 0, 'armor': 0}

weapons = [
    {'cost': 8, 'damage': 4, 'armor': 0},
    {'cost': 10, 'damage': 5, 'armor': 0},
    {'cost': 25, 'damage': 6, 'armor': 0},
    {'cost': 40, 'damage': 7, 'armor': 0},
    {'cost': 74, 'damage': 8, 'armor': 0},
]

armors = [
    {'cost': 0, 'damage': 0, 'armor':0}, # no armor
    {'cost': 13, 'damage': 0, 'armor':1},
    {'cost': 31, 'damage': 0, 'armor':2},
    {'cost': 53, 'damage': 0, 'armor':3},
    {'cost': 75, 'damage': 0, 'armor':4},
    {'cost': 102, 'damage': 0, 'armor':5},
]

rings = [
    {'cost': 0, 'damage': 0, 'armor': 0}, # no ring 1
    {'cost': 0, 'damage': 0, 'armor': 0}, # no ring 2
    {'cost': 25, 'damage': 1, 'armor': 0},
    {'cost': 50, 'damage': 2, 'armor': 0},
    {'cost': 100, 'damage': 3, 'armor': 0},
    {'cost': 20, 'damage': 0, 'armor': 1},
    {'cost': 40, 'damage': 0, 'armor': 2},
    {'cost': 80, 'damage': 0, 'armor': 3},
]

def play(player1, player2):
    attacker = p1 = player1.copy()
    defender = p2 = player2.copy()

    while p1['hp'] > 0 and p2['hp'] > 0:
        defender['hp'] -= max(1, attacker['damage'] - defender['armor'])
        attacker, defender = defender, attacker
    return 1 if p2['hp'] <= 0 else 2

buyings = []
for weapon in weapons:
    for armor in armors:
        for ring1 in rings:
            for ring2 in rings:
                buyings.append([weapon, armor, ring1, ring2])
buyings.sort(key=lambda items: sum(item['cost'] for item in items))

for items in buyings:
    cost = sum(item['cost'] for item in items)
    player['damage'] = sum(item['damage'] for item in items)
    player['armor'] = sum(item['armor'] for item in items)
    winner = play(player, boss)
    if winner == 1:
        print(cost)
        break
