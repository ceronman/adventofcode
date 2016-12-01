gates = { wire: gate for gate, wire in
          (line.strip().split(' -> ') for line in open('input.txt')) }
memo = {}
def solve_wire_memoized(wire):
    if wire not in memo:
        memo[wire] = solve_wire(wire)
    return memo[wire]

def solve_wire(wire):
    if wire.isdigit():
        return int(wire)

    gate = gates[wire]

    if gate.isalpha() or gate.isdigit():
        return solve_wire_memoized(gate)

    if 'NOT' in gate:
        _, a = gate.split()
        return (~ solve_wire_memoized(a)) & 0xFFFF

    a, op, b = gate.split()

    if op == 'AND':
        return (solve_wire_memoized(a) & solve_wire_memoized(b)) & 0xFFFF
    if op == 'OR':
        return (solve_wire_memoized(a) | solve_wire_memoized(b)) & 0xFFFF
    if op == 'RSHIFT':
        return (solve_wire_memoized(a) >> solve_wire_memoized(b)) & 0xFFFF
    if op == 'LSHIFT':
        return (solve_wire_memoized(a) << solve_wire_memoized(b)) & 0xFFFF
    raise

print(solve_wire('a'))
