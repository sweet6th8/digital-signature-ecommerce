package controller.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@MultipartConfig
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Define the upload directory
        String uploadPath = getServletContext().getRealPath("/") + "uploads" + File.separator;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs(); // Create directories if they don't exist

        // Create the directory if it doesn't exist
        File resultDir = new File(uploadPath + "result" + File.separator);
        if (!resultDir.exists()) resultDir.mkdirs();

        // Retrieve uploaded files (human and cloth)
        Part humanPart = request.getPart("human");
        Part clothPart = request.getPart("cloth");

        // Get original filenames
        String humanFilename = getFileName(humanPart);
        String clothFilename = getFileName(clothPart);


        // Add paths for uploaded image previews to the request
        request.setAttribute("uploadedHumanImage", "uploads/" + humanFilename); // Adjust path as needed
        request.setAttribute("uploadedClothImage", "uploads/" + clothFilename); // Adjust path as needed
        // Save uploaded files to the upload directory
        humanPart.write(uploadPath + humanFilename);
        clothPart.write(uploadPath + clothFilename);

        // Run the AI pipeline
        String resultImagePath = runAI(uploadPath, humanFilename, clothFilename);

        // Check the result and forward to JSP for display
        if (resultImagePath != null) {
            // Copy the result image to the result directory for easy access by JSP
            File resultFile = new File(resultImagePath);
            File destinationFile = new File(resultDir, humanFilename + "_" + clothFilename);
            Files.copy(resultFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Set relative path for JSP to access the result image
            request.setAttribute("resultImage", "uploads/result/" + humanFilename + "_" + clothFilename);

            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("templates/tryon.jsp");
            dispatcher.forward(request, response);
        } else {
            response.getWriter().println("AI processing failed. Please check the server logs.");
        }
    }

    // Helper method to extract the filename from the Part header
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "unknown"; // Default in case no filename is found
    }

    private String runAI(String uploadPath, String humanFilename, String clothFilename) throws IOException {
        // Define paths for AI processing
        String aiRootPath = "D:\\FashionWebProject\\VIRTUAL-TRYON";
        String vitonDatasetImagePath = "VITON-HD/datasets/test/image";
        String resultImageRelativePath = String.format("VITON-HD/results/try-on/%s_%s", humanFilename, clothFilename);


        // Path to Python in the Conda environment
        String pythonPath = "C:\\MyConda\\envs\\myenv\\python.exe";

        ProcessBuilder builder = new ProcessBuilder();
        builder.redirectErrorStream(true); // Combine standard and error streams for easier debugging

        try {
            System.out.println("Starting AI Virtual Try-On processing...");

            // Step 1: HUMAN-PARSE
            System.out.println("Running HUMAN-PARSE...");
            builder.directory(new File(aiRootPath + "\\HUMAN-PARSE"));
            String commandHumanParse = String.format(
                    "%s exp/inference/inference.py --loadmodel ./models/inference.pth" +
                            " --img_path ../%s/%s --output_path ../VITON-HD/datasets/test/image-parse --output_name %s",
                    pythonPath, vitonDatasetImagePath, humanFilename, humanFilename.replace(".jpg", "")
            );
            builder.command("cmd.exe", "/c", commandHumanParse);
            Process humanParseProcess = builder.start();
            printProcessOutput(humanParseProcess);
            humanParseProcess.waitFor();

            // Step 2: OPENPOSE
            System.out.println("Running OPENPOSE...");
            builder.directory(new File(aiRootPath + "\\OPENPOSE"));
            String commandOpenPose = String.format(
                    "bin\\OpenPoseDemo.exe --image_dir ../VITON-HD/datasets/test/image --hand" +
                            " --write_json ../VITON-HD/datasets/test/openpose-json --write_images ../VITON-HD/datasets/test/openpose-img" +
                            " --display 0 --disable_blending"
            );
            builder.command("cmd.exe", "/c", commandOpenPose);
            Process openPoseProcess = builder.start();
            printProcessOutput(openPoseProcess);
            openPoseProcess.waitFor();

            // Step 3: VITON-HD
            System.out.println("Running VITON-HD...");
            builder.directory(new File(aiRootPath + "\\VITON-HD"));
            builder.command("cmd.exe", "/c", pythonPath + " test.py");
            Process vitonHdProcess = builder.start();
            printProcessOutput(vitonHdProcess);
            vitonHdProcess.waitFor();

            // Verify the AI output file
            File resultImageFile = new File(aiRootPath, resultImageRelativePath);
            if (resultImageFile.exists()) {
                System.out.println("AI Virtual Try-On completed successfully.");
                return resultImageFile.getAbsolutePath();
            } else {
                System.err.println("Result image not found at: " + resultImageFile.getAbsolutePath());
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error during AI Virtual Try-On processing: " + e.getMessage());
            return null;
        }
    }

    private void printProcessOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("AI Command Output: " + line);
        }
    }
}
