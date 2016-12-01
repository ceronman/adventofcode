# input_text = 'R8, R4, R4, R8'
input_text = open('input1.txt').read()

dir_x, dir_y = (0, 1) # North
dest_x, dest_y = (0, 0)

seen = set([(dest_x, dest_y)])
for step in input_text.split(', '):
    side = step[0]
    distance = int(step[1:])
    if side == 'R':
        dir_x, dir_y = dir_y, -dir_x
    else:
        dir_x, dir_y = -dir_y, dir_x

    seen_twice = False
    for i in ([1] * distance):
        dest_x += dir_x * i
        dest_y += dir_y * i
        if (dest_x, dest_y) in seen:
            seen_twice = True
            break
        else:
            seen.add((dest_x, dest_y))
    if seen_twice:
        break

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