package controller.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

@WebServlet("/aiprocess")
public class AIProcessServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AIProcessServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve uploaded image name
        String imageName = request.getParameter("human");
        if (imageName == null || imageName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Image name is missing.");
            return;
        }
        // Path to the virtual environment's Python executable
        String venvPython = "D:/FashionWebProject/VIRTUAL-TRYON/.venv/Scripts/python.exe";
        try {
            // HUMAN-PARSE: Run inference.py
            String humanParseCommand = String.format(
                    "%s exp/inference/inference.py --loadmodel ./models/inference.pth --img_path ../VITON-HD/datasets/test/image/%s --output_path ../VITON-HD/datasets/test/image-parse --output_name /%s",
                    venvPython,
                    imageName,
                    imageName.split("\\.")[0] // Get file name without extension (e.g., person1)
            );
            executeCommand(humanParseCommand, new File("D:/FashionWebProject/VIRTUAL-TRYON/HUMAN-PARSE"));

            // OPENPOSE: Run OpenPoseDemo.exe (doesn't use Python)
            String openPoseCommand = String.format(
                    "bin\\OpenPoseDemo.exe --image_dir ../VITON-HD/datasets/test/image --hand --write_json ../VITON-HD/datasets/test/openpose-json --write_images ../VITON-HD/datasets/test"
            );
            executeCommand(openPoseCommand, new File("D:/FashionWebProject/VIRTUAL-TRYON/OPENPOSE"));

            // VITON-HD: Run test.py
            String vitonCommand = String.format(
                    "%s test.py",
                    venvPython
            );
            executeCommand(vitonCommand, new File("D:/FashionWebProject/VIRTUAL-TRYON/VITON-HD"));

            // Success Response
            response.setContentType("text/plain");
            response.getWriter().write("AI process completed successfully.");
        } catch (Exception e) {
            logger.severe("Error during AI process execution: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error during AI processing.");
        }
    }

    private void executeCommand(String command, File directory) throws IOException, InterruptedException {
        logger.info("Executing command: " + command + " in directory: " + directory);

        // ProcessBuilder setup
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", command); // Support Windows environment
        processBuilder.directory(directory); // Set working directory
        processBuilder.redirectErrorStream(true); // Merge stdout and stderr

        // Start the process
        Process process = processBuilder.start();

        // Capture process output for logging/debugging
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null)  logger.info(line);
        }

        // Wait for process to complete and check exit code
        int exitCode = process.waitFor();
        if (exitCode != 0)  throw new RuntimeException("Command failed with exit code: " + exitCode);
    }
}

