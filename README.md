markdown
# Log Analyzer

**Log Analyzer** is a Java tool for filtering and processing log files using CLI commands.

---

## Features

- Extract logs by level (`INFO`, `ERROR`, etc.)
- Filter by keyword or time range
- Remove duplicate entries
- Export logs to `.csv`
- CLI-based control

---

## Getting Started

### 1. Compile the project:
```bash
javac src/LogService.java -d out


### 2. Run the analyzer:

#### Extract only `ERROR` logs:
```bash
java -cp out LogService logs/sample.log ERROR
```

#### Search logs by keyword:
```bash
java -cp out LogService logs/sample.log search "database"
```

#### Show log statistics:
```bash
java -cp out LogService logs/sample.log stats
```

---

## Tech Stack

- Java 17
- FileReader, BufferedReader, FileWriter
- Command-line interface (CLI)

---

## License

MIT

---
