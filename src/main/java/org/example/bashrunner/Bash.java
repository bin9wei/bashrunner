package org.example.bashrunner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.function.Consumer;

public class Bash {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bash.class);

    public int deploy() throws IOException, InterruptedException {
        return execute("scripts/deploy.sh");
    }

    public int rollback() throws IOException, InterruptedException {
        return execute("scripts/rollback.sh");
    }

    private int execute(String shellName) throws IOException, InterruptedException {
        StringBuilder bashCommand = new StringBuilder();
        String shellLine;

        try (InputStream fileContent = this.getClass().getClassLoader().getResourceAsStream(shellName);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileContent))) {
            while ((shellLine = bufferedReader.readLine()) != null) {
                bashCommand.append(shellLine + "\n");
            }
        }

        ProcessBuilder processBuilder = new ProcessBuilder("bash", spitFileToTemp(shellName, bashCommand.toString()));
        processBuilder.redirectErrorStream(true);
        Process p = processBuilder.start();
        StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), LOGGER::info);
        StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), LOGGER::error);
        new Thread(outputGobbler).start();
        new Thread(errorGobbler).start();
        return p.waitFor();
    }

    private String spitFileToTemp(String fileName, String content) throws IOException {
        File f = File.createTempFile(fileName, "sh");
        f.deleteOnExit();
        FileWriter write = new FileWriter(f.getPath());
        write.write(content);
        write.flush();
        return f.getPath();
    }

    class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumeInputLine;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumeInputLine) {
            this.inputStream = inputStream;
            this.consumeInputLine = consumeInputLine;
        }

        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumeInputLine);
        }
    }
}
