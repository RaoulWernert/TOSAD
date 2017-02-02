package nl.hu.tosad.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Logger {
    private static Logger logger;

    public synchronized static Logger getInstance() {
        if(logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    private String osname;

    private Logger() {
        osname = System.getProperty("os.name", "").toLowerCase();
    }

    public void Log(Throwable e) {
        String error = e.getMessage() + "\n";
        error += Stream.of(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));

        Log(error.substring(0, error.length()-2));
    }

    public void Log(String msg) {
        ZonedDateTime now = ZonedDateTime.now();
        String path;
        if(osname.startsWith("linux")) {
            path = String.format("/home/huuser/IdeaProjects/%s.txt", now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        } else {
            path = String.format("%s.txt", now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }

        String datetime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        msg = msg.replace("\n", "\n          "); //TODO: nice formatting
        System.out.println(datetime + " - " + msg);

        synchronized (Logger.class) {
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
                output.append(datetime).append(": ").append(msg).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
