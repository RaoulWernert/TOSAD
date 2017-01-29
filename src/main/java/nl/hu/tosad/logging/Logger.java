package nl.hu.tosad.logging;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger logger;

    public static Logger getInstance() {
        if(logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    private Logger() {}

    public void Log(Throwable e) {
        for(StackTraceElement element : e.getStackTrace()) {
            Log(element.toString());
        }
    }

    public void Log(String msg) {
        ZonedDateTime now = ZonedDateTime.now();
        String path = String.format("%s.txt", now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        String datetime = now.format(DateTimeFormatter.ISO_LOCAL_TIME);

        if(Files.notExists(Paths.get(path))) {
            try {
                Files.createFile(Paths.get(path));
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardOpenOption.APPEND)) {
            writer.append(datetime).append(" - ").append(msg).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(datetime + " - " + msg);
    }
}
