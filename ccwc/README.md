# CCWC - Code Challenge Word Count

A Java implementation of the Unix 'wc' command line tool. This tool counts bytes, lines, words, and characters in files.

## Description

CCWC (Code Challenge Word Count) is a Java implementation of the Unix 'wc' utility. It provides functionality to count:
- Bytes in a file (-c option)
- Lines in a file (-l option)
- Words in a file (-w option)
- Characters in a file (-m option)

The tool can read from both files and standard input (stdin).

## Requirements

- Java 17 or higher
- Maven 3.6 or higher (for building)

## Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   cd ccwc
   ```

2. Build the project using Maven:
   ```
   mvn clean package
   ```

   This will create a JAR file with dependencies at `target/ccwc-1.0-SNAPSHOT-jar-with-dependencies.jar`.

3. Make the shell script executable:
   ```
   chmod +x ccwc.sh
   ```

## Usage

You can run the tool using the provided shell script:

```
./ccwc.sh [OPTION]... [FILE]
```

Or directly using Java:

```
java -jar target/ccwc-1.0-SNAPSHOT-jar-with-dependencies.jar [OPTION]... [FILE]
```

### Options

- `-c`: Print the byte count
- `-l`: Print the line count
- `-w`: Print the word count
- `-m`: Print the character count

If no options are specified, the tool will print line, word, and byte counts.

If no file is specified, the tool will read from standard input.

### Examples

Count bytes in a file:
```
./ccwc.sh -c filename.txt
```

Count lines in a file:
```
./ccwc.sh -l filename.txt
```

Count words in a file:
```
./ccwc.sh -w filename.txt
```

Count characters in a file:
```
./ccwc.sh -m filename.txt
```

Count lines, words, and bytes in a file (default behavior):
```
./ccwc.sh filename.txt
```

Count lines from standard input:
```
cat filename.txt | ./ccwc.sh -l
```

## Running Tests

To run the tests:

```
mvn test
```

## Building from Source

To build the project from source:

```
mvn clean package
```

This will:
1. Compile the Java source code
2. Run the tests
3. Create a JAR file with dependencies at `target/ccwc-1.0-SNAPSHOT-jar-with-dependencies.jar`

## License

[[Apache 2.0 License]](https://github.com/eriksimundson/JavaCodingChallenges/blob/main/LICENSE#:~:text=LICENSE)
