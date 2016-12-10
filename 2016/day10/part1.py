import re

# input_lines = '''\
# value 5 goes to bot 2
# bot 2 gives low to bot 1 and high to bot 0
# value 3 goes to bot 1
# bot 1 gives low to output 1 and high to bot 0
# bot 0 gives low to output 2 and high to output 0
# value 2 goes to bot 2'''.splitlines()

input_lines = open('input.txt')

holders = {
    'bot': {},
    'output': {}
}

give_commands = {}


for line in input_lines:
    match = re.match((r'value (\d+) goes to bot (\d+)'), line)
    if match:
        value, bot_nr = match.groups()
        holders['bot'].setdefault(bot_nr, []).append(value)
        continue

    match = re.match(r'bot (\d+) gives low to (bot|output) '
                     r'(\d+) and high to (bot|output) (\d+)', line)
    if match:
        source, low_kind, low_nr, high_kind, high_nr = match.groups()
        give_commands[source] = (low_kind, low_nr, high_kind, high_nr)


ready = [h for h in holders['bot'] if len(holders['bot'][h]) == 2 ]
while ready:
    for bot_nr in ready:
        low_value, high_value = sorted(holders['bot'][bot_nr], key=int)

        if low_value == '17' and high_value == '61':
            print(bot_nr)

        low_kind, low_nr, high_kind, high_nr = give_commands[bot_nr]
        holders[low_kind].setdefault(low_nr, []).append(low_value)
        holders[high_kind].setdefault(high_nr, []).append(high_value)
        del holders['bot'][bot_nr]
    ready = [h for h in holders['bot'] if len(holders['bot'][h]) == 2 ]