package ui.exams;


import java.io.IOException;

public class ExamRunner {

    ProcessBuilder processBuilder;
    Process process;

    public void runServer() throws IOException, InterruptedException {

        String command = "python -m http.server";

        // Start the local server
        processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.redirectErrorStream(true);
        process = processBuilder.start();

        // Wait for the server to start
        Thread.sleep(500);

        // Open the webpage in the default browser
        String url = "http://localhost:8000/examsite/"; // Adjust the URL according to your local server setup
        openWebPage(url);
    }

    private void openWebPage(String url) throws IOException {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
    }

    public void destroyServer() {
        process.destroy();
    }
}



