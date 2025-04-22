package com.github.jahong1r_t;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--help")) {
            printHelp();
            return;
        }

        LogService service = new LogService();

        switch (args[0]) {
            case "--level":
                if (args.length >= 3)
                    service.filterByLevel(args[1], args[2]);
                else
                    System.out.println("Missing arguments. Usage: --level <file> <LEVEL>");
                break;

            case "--stats":
                if (args.length >= 2) {
                    Map<String, Integer> stats = service.analyzeLogLevels(args[1]);
                    stats.forEach((k, v) -> System.out.println(k + ": " + v));
                } else {
                    System.out.println("Missing arguments. Usage: --stats <file>");
                }
                break;

            case "--time":
                if (args.length >= 4) {
                    LocalDateTime from = parseDate(args[2]);
                    LocalDateTime to = parseDate(args[3]);
                    service.filterLogsByTimeRange(args[1], from, to);
                } else {
                    System.out.println("Missing arguments. Usage: --time <file> <from> <to>");
                }
                break;

            case "--search":
                if (args.length >= 3)
                    service.searchLogs(args[1], args[2]);
                else
                    System.out.println("Missing arguments. Usage: --search <file> <keyword>");
                break;

            case "--deduplicate":
                if (args.length >= 3)
                    service.removeDuplicateLogs(args[1], args[2]);
                else
                    System.out.println("Missing arguments. Usage: --deduplicate <file> <output>");
                break;

            case "--to-csv":
                if (args.length >= 3)
                    service.convertLogsToCsv(args[1], args[2]);
                else
                    System.out.println("Missing arguments. Usage: --to-csv <file> <output>");
                break;

            default:
                System.out.println("Unknown command. Use --help to see available options.");
        }
    }

    private static void printHelp() {
        System.out.println("""
                Usage: java -jar log-analyzer-1.0.0.jar [command] [options]
                
                Commands:
                  --level <file> <LEVEL>         Filter logs by level (INFO, DEBUG, WARN, ERROR)
                  --stats <file>                 Analyze log levels and show counts
                  --time <file> <from> <to>      Filter logs by time range (format: yyyy-MM-dd HH:mm:ss)
                  --search <file> <keyword>      Search for keyword in logs
                  --deduplicate <file> <output>  Remove duplicate log lines
                  --to-csv <file> <output>       Convert logs to CSV format
                  --help                         Show this help message
                """);
    }

    private static LocalDateTime parseDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(input, formatter);
    }
}
