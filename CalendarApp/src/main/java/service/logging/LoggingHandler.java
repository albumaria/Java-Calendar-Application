package service.logging;


import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class LoggingHandler implements ILoggingHandler {
    private String filePath;

    public LoggingHandler(String file) {
        this.filePath = file;
    }

    public void createLogText(String message) {

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));

        StringBuilder s = new StringBuilder("==================== DATE: " + dayOfWeek.toString() + ", " + formattedDate +
                " ===== TIME: " + formattedTime + "\n" +
                message + "\n");

        this.addToLogFile(s.toString());

    }

    private void addToLogFile(String logText) {
        PrintWriter logFile = null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath, true)));
            logFile.println(logText);
        }
        catch (IOException e) {
            throw new LoggingException("Could not log message into " + this.filePath);
        }
        finally {
            if (logFile != null)
                logFile.close();
        }
    }

}
