# Word Count Application

## Description
This command-line application takes a path to a file as an argument and prints a word count of its contents. The output consists of a line for each word, with the number of its occurrences in the file, sorted by the number of occurrences starting with the most frequent word.

## Prerequisites
Make sure you have the following installed before running the application:
- Java 17 or later: [Download Java](https://www.oracle.com/java/technologies/javase-downloads.html)
- Maven: [Download Maven](https://maven.apache.org/download.cgi)

## Assumptions
- The input file is a plain text file with ASCII encoding.
- Words are separated by spaces.
- Users can enter "exit" to end the session.
- Word count is case-insensitive.
- Punctuation is ignored.

## Build
1. Clone the repository:
   ```bash
   git clone https://github.com/tsedekey/word-count-cli.git
   ```
   cd your-repository-name
2. Build the application:
   ```bash
   mvn clean install
   ```
## Usage
1. Run the application using the generated JAR file:
```bash
java -jar target/word-count-cli-1.0-SNAPSHOT.jar <path-to-file>
```
2. Enter "exit" to end the session.

## Configuration
The application is configurable for the following aspects:

- Input type
- Output formatter
- Output type
- Delimiter type
- Sort order (ascending or descending)
- Sort type (frequency or word length)

You can customize these configurations by modifying the Configuration interface and providing your implementations.