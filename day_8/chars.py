import ast

print sum(len(s.strip()) - len(ast.literal_eval(s)) for s in open('input.txt'))