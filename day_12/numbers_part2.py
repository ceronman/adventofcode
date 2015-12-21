import json

def extract_numbers(data):
    if isinstance(data, (int, float)):
        yield data
    elif isinstance(data, list):
        for i in data:
            yield from extract_numbers(i)
    elif isinstance(data, dict):
        if 'red' not in data.values():
            for value in data.values():
                yield from extract_numbers(value)

print(sum(extract_numbers(json.load(open('input.json')))))
