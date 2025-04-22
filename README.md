# Log Analyzer

A simple Java-based command-line tool for analyzing and processing log files. Log Analyzer provides features like filtering logs by level or time range, searching for keywords, removing duplicates, analyzing log level statistics, and converting logs to CSV format.

## Features
- **Filter by Log Level**: Extract logs for a specific level (e.g., INFO, ERROR) with case-insensitive matching.
- **Search Logs**: Find log entries containing a specific keyword.
- **Log Level Statistics**: Count occurrences of each log level (INFO, DEBUG, WARN, ERROR).
- **Time Range Filtering**: Filter logs within a specified time range.
- **Remove Duplicates**: Eliminate duplicate log entries while preserving order.
- **Convert to CSV**: Transform log files into CSV format for easier analysis.

## Prerequisites
- **Java 17** or later installed.
- **Maven** for building the project.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/jahong1r-t/log-analyzer.git
   cd log-analyzer
   ```
2. Build the project using Maven:
   ```bash
   mvn clean package
   ```
   This generates `log-analyzer.jar` in the `target` directory.

## Usage
Run the tool using the generated JAR file:
```bash
java -jar target/log-analyzer.jar [command] [options]
```

### Available Commands
| Command | Description | Usage |
|---------|-------------|-------|
| `--level <file> <LEVEL>` | Filter logs by level (e.g., INFO, DEBUG) | `java -jar log-analyzer.jar --level input.log INFO` |
| `--stats <file>` | Analyze log level counts | `java -jar log-analyzer.jar --stats input.log` |
| `--time <file> <from> <to>` | Filter logs by time range (format: `yyyy-MM-dd HH:mm:ss`) | `java -jar log-analyzer.jar --time input.log "2023-01-01 00:00:00" "2023-01-01 23:59:59"` |
| `--search <file> <keyword>` | Search logs for a keyword | `java -jar log-analyzer.jar --search input.log "error"` |
| `--deduplicate <file> <output>` | Remove duplicate log lines | `java -jar log-analyzer.jar --deduplicate input.log output.log` |
| `--to-csv <file> <output>` | Convert logs to CSV format | `java -jar log-analyzer.jar --to-csv input.log output.csv` |
| `--help` | Show help message | `java -jar log-analyzer.jar --help` |

### Example Log File
```log
2023-01-01 10:00:00 [INFO] Application started
2023-01-01 10:01:00 [ERROR] Failed to connect
2023-01-01 10:02:00 [INFO] Application started
2023-01-01 10:03:00 [DEBUG] Processing data
```

### Example Commands
1. **Filter INFO logs**:
   ```bash
   java -jar log-analyzer.jar --level input.log INFO
   ```
   Output: Creates `info.log` with:
   ```log
   2023-01-01 10:00:00 [INFO] Application started
   2023-01-01 10:02:00 [INFO] Application started
   ```

2. **Search for "error"**:
   ```bash
   java -jar log-analyzer.jar --search input.log error
   ```
   Output: Creates `search-results.txt` with matching lines and prints:
   ```
   Found 1 line(s) with keyword 'error'.
   Results saved to: search-results.txt
   ```

3. **Analyze log levels**:
   ```bash
   java -jar log-analyzer.jar --stats input.log
   ```
   Output:
   ```
   INFO: 2
   DEBUG: 1
   WARN: 0
   ERROR: 1
   ```

4. **Filter by time range**:
   ```bash
   java -jar log-analyzer.jar --time input.log "2023-01-01 10:00:00" "2023-01-01 10:01:00"
   ```
   Output: Creates `filtered-by-time.log` with logs in the specified range.

5. **Remove duplicates**:
   ```bash
   java -jar log-analyzer.jar --deduplicate input.log output.log
   ```
   Output: Creates `output.log` with unique lines.

6. **Convert to CSV**:
   ```bash
   java -jar log-analyzer.jar --to-csv input.log output.csv
   ```
   Output: Creates `output.csv` with:
   ```csv
   DateTime,Level,Message
   2023-01-01 10:00:00,INFO,Application started
   2023-01-01 10:01:00,ERROR,Failed to connect
   2023-01-01 10:02:00,INFO,Application started
   2023-01-01 10:03:00,DEBUG,Processing data
   ```
## License
This project is licensed under the [MIT License](LICENSE).

## Contact
For questions or feedback, contact [Jahongir To'rayev](mailto:jahongirtorayev1507@gmail.com).
