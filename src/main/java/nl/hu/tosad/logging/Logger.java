package nl.hu.tosad.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        String error = e.getMessage() + "\n";

        for(StackTraceElement element : e.getStackTrace()) {
            error += element.toString() + "\n";
        }
        Log(error);
    }

    public void Log(String msg) {
        ZonedDateTime now = ZonedDateTime.now();
        String path = String.format("/home/huuser/IdeaProjects/%s.txt", now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        String datetime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        msg = msg.replace("\n", "\n           ");
        File file = new File(path);

        if(file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try (BufferedWriter output = new BufferedWriter(new FileWriter(file, true))) {
            output.append(datetime).append(" - ").append(msg).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(datetime + " - " + msg);
    }
}
