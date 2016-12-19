# NUM_ELVES = 5
NUM_ELVES = 3018458

class Elve:
    def __init__(self, i, next):
        self.i = i
        self.next = next

head = e = Elve(1, None)
while e.i < NUM_ELVES:
    e.next = Elve(e.i+1, None)
    e = e.next
e.next = head

while head.next != head:
    head.next = head.next.next
    head = head.next

print(head.i)
