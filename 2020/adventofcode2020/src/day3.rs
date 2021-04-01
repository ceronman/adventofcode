use std::fs;

struct Navigation {
    map: Vec<Vec<char>>,
    x: usize,
    y: usize,
}

impl Navigation {
    fn move_right(&mut self, offset: usize) {
        let limit = self.map[self.y].len();
        let next = self.x + offset;
        self.x = if next >= limit { next - limit } else { next }
    }

    fn move_down(&mut self, offset: usize) {
        self.y += offset
    }

    fn is_out(&self) -> bool {
        self.y >= self.map.len()
    }

    fn is_tree(&self) -> bool {
        self.map[self.y][self.x] == '#'
    }
}

fn parse_input(input: &str) -> Vec<Vec<char>> {
    input
        .lines()
        .map(|s| s.chars().collect::<Vec<char>>())
        .collect()
}

pub fn part1() -> usize {
    //    let input = "..##.......\n\
    //                 #...#...#..\n\
    //                 .#....#..#.\n\
    //                 ..#.#...#.#\n\
    //                 .#...##..#.\n\
    //                 ..#.##.....\n\
    //                 .#.#.#....#\n\
    //                 .#........#\n\
    //                 #.##...#...\n\
    //                 #...##....#\n\
    //                 .#..#...#.#";

    let input = fs::read_to_string("data/day3/input.txt").expect("Unable to read file");

    let mut navigation = Navigation {
        map: parse_input(&input),
        x: 0,
        y: 0,
    };

    let mut count = 0;
    loop {
        navigation.move_right(3);
        navigation.move_down(1);
        if navigation.is_out() {
            break;
        }
        if navigation.is_tree() {
            count += 1;
        }
    }

    count
}
