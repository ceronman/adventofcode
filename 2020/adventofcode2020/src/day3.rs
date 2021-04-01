use std::fs;

struct Navigation {
    map: Vec<Vec<char>>,
    x: usize,
    y: usize,
}

impl Navigation {
    fn new(input: &str) -> Self {
        let m: Vec<Vec<char>> = input
            .lines()
            .map(|s| s.chars().collect::<Vec<char>>())
            .collect();
        Self { x: 0, y: 0, map: m }
    }

    fn move_right(&mut self, offset: usize) {
        let limit = self.map[self.y].len();
        let next = self.x + offset;
        self.x = if next >= limit { next - limit } else { next }
    }

    fn move_down(&mut self, offset: usize) {
        self.y += offset
    }

    fn find_trees(&mut self, right: usize, down: usize) -> usize {
        self.x = 0;
        self.y = 0;
        let mut count = 0;
        loop {
            self.move_right(right);
            self.move_down(down);
            if self.y >= self.map.len() {
                break;
            }
            if self.map[self.y][self.x] == '#' {
                count += 1;
            }
        }

        count
    }
}

#[allow(dead_code)]
const TEST_DATA: &str = "..##.......\n\
                         #...#...#..\n\
                         .#....#..#.\n\
                         ..#.#...#.#\n\
                         .#...##..#.\n\
                         ..#.##.....\n\
                         .#.#.#....#\n\
                         .#........#\n\
                         #.##...#...\n\
                         #...##....#\n\
                         .#..#...#.#";

pub fn part1() -> usize {
    //    let input = TEST_DATA;
    let input = fs::read_to_string("data/day3/input.txt").expect("Unable to read file");

    let mut navigation = Navigation::new(&input);

    navigation.find_trees(3, 1)
}

pub fn part2() -> usize {
    //    let input = TEST_DATA;
    let input = fs::read_to_string("data/day3/input.txt").expect("Unable to read file");

    let mut navigation = Navigation::new(&input);

    let options = vec![
        navigation.find_trees(1, 1),
        navigation.find_trees(3, 1),
        navigation.find_trees(5, 1),
        navigation.find_trees(7, 1),
        navigation.find_trees(1, 2),
    ];

    options.iter().fold(1, |a, b| a * b)
}
