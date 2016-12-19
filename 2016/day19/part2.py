# NUM_ELVES = 5
NUM_ELVES = 3018458

class Elve:
    def __init__(self, i, p, n):
        self.i = i
        self.prev = p
        self.next = n

head = e = Elve(1, None, None)
target = None
while e.i < NUM_ELVES:
    e.next = Elve(e.i+1, e, None)
    e = e.next
    if e.i == (NUM_ELVES // 2) + 1:
        target = e
head.prev = e
e.next = head

size = NUM_ELVES
while head.next != head:
    target.prev.next = target.next
    target.next.prev = target.prev
    if size % 2 == 0:
        target = target.next
    else:
        target = target.next.next
    head = head.next
    size -= 1

print(head.i)
