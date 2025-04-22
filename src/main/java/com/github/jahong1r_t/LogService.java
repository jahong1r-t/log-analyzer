package com.github.jahong1r_t;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LogService {
    public void filterByLevel(String file, String level) {
        File logFile = new File(file);
        File outputFile = new File(level.toLowerCase() + ".log");

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toUpperCase().contains(level.toUpperCase())) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            System.out.println(level + " logs written to: " + outputFile.getName());

        } catch (IOException e) {
            System.err.println("Error while processing the file: " + e.getMessage());
        }
    }


    public void searchLogs(String file, String keyword) {
        File logFile = new File(file);
        File outputFile = new File("search-results.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    writer.write(line);
                    writer.newLine();
                    count++;
                }
            }

            System.out.println("Found " + count + " line(s) with keyword '" + keyword + "'.");
            System.out.println("Results saved to: " + outputFile.getName());

        } catch (IOException e) {
            System.err.println("Error while searching logs: " + e.getMessage());
        }
    }


    public Map<String, Integer> analyzeLogLevels(String filePath) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("INFO", 0);
        stats.put("DEBUG", 0);
        stats.put("WARN", 0);
        stats.put("ERROR", 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String level : stats.keySet()) {
                    if (line.toUpperCase().contains(level)) {
                        stats.put(level, stats.get(level) + 1);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }

        return stats;
    }


    public void filterLogsByTimeRange(String filePath, LocalDateTime from, LocalDateTime to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        File outputFile = new File("filtered-by-time.log");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() < 19) continue;

                String timestamp = line.substring(0, 19);
                try {
                    LocalDateTime logTime = LocalDateTime.parse(timestamp, formatter);
                    if (!logTime.isBefore(from) && !logTime.isAfter(to)) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (DateTimeParseException ignored) {
                }
            }

            System.out.println("Filtered logs saved to: " + outputFile.getName());

        } catch (IOException e) {
            System.err.println("Error during time filtering: " + e.getMessage());
        }
    }

    public void removeDuplicateLogs(String filePath, String outputPath) {
        if (filePath == null || filePath.trim().isEmpty() || outputPath == null || outputPath.trim().isEmpty()) {
            System.err.println("Error: File path or output path is null or empty.");
            return;
        }

        File outputFile = new File(outputPath);
        if (outputFile.isDirectory()) {
            System.err.println("Error: Output path '" + outputPath + "' is a directory, not a file.");
            return;
        }

        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            try {
                parentDir.mkdirs();
            } catch (SecurityException e) {
                System.err.println("Error: Cannot create parent directory '" + parentDir.getPath() + "': " + e.getMessage());
                return;
            }
        }

        if (parentDir != null && !parentDir.canWrite()) {
            System.err.println("Error: No write permission for directory '" + parentDir.getPath() + "'.");
            return;
        }

        Set<String> uniqueLines = new LinkedHashSet<>();
        File inputFile = new File(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                uniqueLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file '" + filePath + "' not found.");
            return;
        } catch (IOException e) {
            System.err.println("Error reading file '" + filePath + "': " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : uniqueLines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Duplicates removed. Output written to: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error writing to output file '" + outputPath + "': " + e.getMessage());
        }
    }

    public void convertLogsToCsv(String filePath, String outputPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)); BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write("DateTime,Level,Message");
            writer.newLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() < 22) continue;

                String dateTime = line.substring(0, 19);
                String level = extractLevel(line);
                String message = line.substring(line.indexOf("]") + 1).trim();

                writer.write(String.format("%s,%s,%s", dateTime, level, message));
                writer.newLine();
            }

            System.out.println("CSV file created: " + outputPath);

        } catch (IOException e) {
            System.err.println("Error converting to CSV: " + e.getMessage());
        }
    }

    private String extractLevel(String line) {
        int start = line.indexOf("[");
        int end = line.indexOf("]");
        if (start >= 0 && end > start) {
            return line.substring(start + 1, end);
        }
        return "UNKNOWN";
    }
}

