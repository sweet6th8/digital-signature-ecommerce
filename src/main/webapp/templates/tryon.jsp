<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Virtual Try-On</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            color: #333;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            color: #007BFF;
            margin-top: 20px;
        }

        form {
            max-width: 800px;
            margin: 30px auto;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
            color: #555;
        }

        input[type="file"] {
            display: block;
            margin-bottom: 15px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 100%;
            box-sizing: border-box;
        }

        button {
            background-color: #007BFF;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
        }

        button:hover {
            background-color: #3d70ec;
        }

        .preview-container {
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
        }

        .preview-item {
            text-align: center;
            flex: 1;
            margin: 0 10px;
        }

        .preview-item img {
            max-width: 100%;
            height: auto;
            border: 2px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<h1>AI Virtual Try-On</h1>
<form action="${pageContext.request.contextPath}/UploadServlet" method="post" enctype="multipart/form-data">
    <label for="human">Upload Human Image:</label>
    <input type="file" id="human" name="human" accept="image/*" required onchange="previewImage(event, 'humanPreview')">

    <label for="cloth">Upload Cloth Image:</label>
    <input type="file" id="cloth" name="cloth" accept="image/*" required onchange="previewImage(event, 'clothPreview')">

    <div class="preview-container">
        <div class="preview-item">
            <h3>Human Preview:</h3>
            <img id="humanPreview" alt="Human Image Preview" style="display: none;">
        </div>
        <div class="preview-item">
            <h3>Cloth Preview:</h3>
            <img id="clothPreview" alt="Cloth Image Preview" style="display: none;">
        </div>
    </div>

    <button type="submit">Try-On Now!</button>
</form>
<div class="result-container">
    <%
        String resultImage = (String) request.getAttribute("resultImage");
        String humanImagePreview = (String) request.getAttribute("uploadedHumanImage");
        String clothImagePreview = (String) request.getAttribute("uploadedClothImage");
        if (resultImage != null && humanImagePreview != null && clothImagePreview != null) {
    %>
    <h2 style="text-align: center;">Comparison:</h2>
    <div class="preview-container">
        <div class="preview-item">
            <h3>Uploaded Human:</h3>
            <img src="<%= humanImagePreview %>" alt="Uploaded Human">
        </div>
        <div class="preview-item">
            <h3>Uploaded Cloth:</h3>
            <img src="<%= clothImagePreview %>" alt="Uploaded Cloth">
        </div>
        <div class="preview-item">
            <h3>Result:</h3>
            <img src="<%= resultImage %>" alt="Virtual Try-On Result">
        </div>
    </div>
    <% } %>
</div>

<script>
    // Function to preview the selected image
    function previewImage(event, previewId) {
        const fileInput = event.target;
        const preview = document.getElementById(previewId);

        if (fileInput.files && fileInput.files[0]) {
            const reader = new FileReader();
            reader.onload = function (e) {
                preview.src = e.target.result; // Set the image source to the file's data URL
                preview.style.display = 'block'; // Show the preview image
            };
            reader.readAsDataURL(fileInput.files[0]); // Read the file as a data URL
        }
    }
</script>
</body>
</html>
