# input_text = 'R5, L5, R5, R3'
input_text = open('input1.txt').read()

dir_x, dir_y = (0, 1) # North
dest_x, dest_y = (0, 0)

for step in input_text.split(', '):
    side = step[0]
    distance = int(step[1:])
    if side == 'R':
        dir_x, dir_y = dir_y, -dir_x
    else:
        dir_x, dir_y = -dir_y, dir_x
    dest_x += dir_x * distance
    dest_y += dir_y * distance

x, y = (0, 0)
distance = 0
while True:
    if (dest_x - x) > 0:
        x += 1
    elif (dest_x - x) < 0:
        x -= 1
    elif (dest_y - y) > 0:
        y += 1
    elif (dest_y - y) < 0:
        y -= 1
    else:
        break
    distance += 1

print(distance)