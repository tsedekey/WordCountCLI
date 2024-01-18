# Word Count CLI

## Description
This command-line application takes a path to a file as an argument and prints a word count of its contents. The output consists of a line for each word, with the number of its occurrences in the file, sorted by the number of occurrences starting with the most frequent word by default.

## Prerequisites
Make sure you have the following installed before running the application:
- Java 17 or later: [Download Java](https://www.oracle.com/java/technologies/javase-downloads.html)

## Assumptions
- The input file is a plain text file with ASCII encoding.
- Words are separated by spaces.
- Word count is case-insensitive.
- Punctuation is ignored.

## Build
1. Clone the repository:
   ```bash
   git clone https://github.com/tsedekey/WordCountCLI.git
   
   cd WordCountCLI
   ```
2. Build the application:
   ```bash
   .\gradlew build
   ```
## Usage
1. Run the application:
   ```bash
   cd build/distributions
   
   unzip WordCountCLI.zip
   
   cd WordCountCLI/bin
   
   ./WordCountCLI <path-to-file>
   ```

2. Command-line arguments:
   ```bash
   Usage: WordCountCLI [-hV] [-o <sortOrder>] [-s <sortType>] <file-path>
   A simple word counting application.
   <file-path>               Path to the input text file
   -h, --help                Show this help message and exit.
   -o, --order <sortOrder>   Sort order (ascending=asc or descending=desc) default=desc
   -s, --sort <sortType>     Sort type (frequency=freq or word length=wl) default=freq
   -V, --version             Print version information and exit.
   ```

3. Example: 
     ```bash
     ./WordCountCLI -s wl -o asc <path-to-file>
     ```
## Test
   Run the following command in the root directory to run the unit tests:
   ```bash
   gradle test
   ```
To view the test report, open the following file in a browser:
   ```bash
   build/reports/tests/test/index.html
   ```