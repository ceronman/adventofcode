use std::fs;

struct Entry {
    min: usize,
    max: usize,
    c: char,
    password: String
}

impl Entry {
    fn parse(line: &str) -> Entry {
        let parts = line.split(": ").collect::<Vec<&str>>();
        let policy = parts[0];
        let password = String::from(parts[1]);

        let parts = policy.split(" ").collect::<Vec<&str>>();
        let ranges = parts[0];
        let c = parts[1].chars().next().unwrap();

        let parts = ranges.split("-").collect::<Vec<&str>>();
        let min = parts[0].parse::<usize>().unwrap();
        let max = parts[1].parse::<usize>().unwrap();

        Entry {
            min,
            max,
            c,
            password
        }
    }

    fn validate(&self) -> bool {
        let count = self.password.chars().filter(|c| *c == self.c).count();
        count >= self.min && count <=self.max
    }

    fn validate_fixed(&self) -> bool {
        let c1 = self.password.chars().nth(self.min - 1).unwrap();
        let c2 = self.password.chars().nth(self.max - 1).unwrap();
        (c1 == self.c && c2 != self.c) || (c1 != self.c && c2 == self.c)
    }
}

fn parse_input(input: &str) -> Vec<Entry> {
    input.lines().map(Entry::parse).collect()
}

pub fn part1() -> usize {
//     let input = "1-3 a: abcde\n\
//                  1-3 b: cdefg\n\
//                  2-9 c: ccccccccc";
    let input = fs::read_to_string("data/day2/input.txt")
        .expect("Unable to read file");
    
    parse_input(&input)
        .into_iter()
        .filter(Entry::validate)
        .count()
}

pub fn part2() -> usize {
//    let input = "1-3 a: abcde\n\
//                 1-3 b: cdefg\n\
//                 2-9 c: ccccccccc";
    let input = fs::read_to_string("data/day2/input.txt")
        .expect("Unable to read file");
    
    parse_input(&input)
        .into_iter()
        .filter(Entry::validate_fixed)
        .count()
}