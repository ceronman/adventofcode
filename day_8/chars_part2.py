lines = (line.strip() for line in open('input.txt'))
print sum(len(l.encode('string-escape').replace('"', r'\"')) + 2 - len(l)
          for l in lines)