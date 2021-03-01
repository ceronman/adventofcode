use std::collections::HashSet;
use std::fs;
use std::iter::FromIterator;

fn find_complement(numbers: Vec<usize>, total: usize) -> Option<(usize, usize)> {
    let complements: HashSet<usize> = HashSet::from_iter(numbers.clone());
    
    for &n in numbers.iter() {
        if complements.contains(&(total - n)) {
            return Some((n, total - n))
        }
    }
    None
}

fn read_data(filename: &str) ->  Vec<usize> {
    fs::read_to_string(filename)
        .expect("Unable to read file")
        .lines()
        .map(|line| line.parse::<usize>().unwrap())
        .collect()
}

pub fn day1() -> u8 {
    const TOTAL: usize = 2020;
    // let test_data = vec![1721, 979, 366, 299, 675, 1456];
    let test_data = read_data("data/day1/data.txt");
    let (a, b) = find_complement(test_data, TOTAL).unwrap();
    println!("{}", a * b);
    1
}