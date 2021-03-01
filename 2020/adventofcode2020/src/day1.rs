use std::collections::HashSet;
use std::fs;

fn read_data(filename: &str) ->  Vec<isize> {
    fs::read_to_string(filename)
        .expect("Unable to read file")
        .lines()
        .map(|line| line.parse::<isize>().unwrap())
        .collect()
}

fn find_sum_of_n(numbers: &HashSet<isize>, total: isize, n: usize) -> Option<Vec<isize>> {
    if n == 1 {
        return if numbers.contains(&total) { Some(vec![total]) } else { None }
    }

    for &element in numbers {
        if let Some(complements) = find_sum_of_n(numbers, total - element, n - 1) {
            let mut a = vec![element];
            a.extend(&complements);
            return Some(a)
        }
    }
    None
}

pub fn part1() -> isize {
    // let test_data = vec![1721, 979, 366, 299, 675, 1456];
    let test_data = read_data("data/day1/data.txt");
    let numbers: HashSet<isize> = test_data.into_iter().collect();

    if let Some(result) = find_sum_of_n(&numbers, 2020, 2) {
        return result.into_iter().fold(1, |acc, e| acc * e)
    }
    panic!("Cound not find value");
}

pub fn part2() -> isize {
    // let test_data = vec![1721, 979, 366, 299, 675, 1456];
    let test_data = read_data("data/day1/data.txt");

    let numbers: HashSet<isize> = test_data.into_iter().collect();
    if let Some(result) = find_sum_of_n(&numbers, 2020, 3) {
        return result.into_iter().fold(1, |acc, e| acc * e)
    }
    panic!("Cound not find value");
}