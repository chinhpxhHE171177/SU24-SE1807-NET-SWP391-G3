<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Registrations</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;500&display=swap" rel="stylesheet">
    <style>
        body {
            box-sizing: border-box;
            font-family: 'Open Sans', 'sans-serif';
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        header, footer {
            width: 100%;
            background-color: #f8f9fa; /* Màu sữa nhẹ */
            padding: 10px 0;
            text-align: center;
        }
        .page {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-grow: 1;
            background-color: #f4f4f4;
        }
        .content {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 800px;
        }
        .content h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 10px;
        }
        form label {
            display: flex;
            align-items: center;
        }
        form input,
        form select,
        form textarea {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        form textarea {
            resize: vertical;
        }
        form button {
            grid-column: span 2;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            background-color: #28a745;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        form button[type="button"] {
            background-color: #dc3545;
        }
        form button:hover {
            background-color: #218838;
        }
        form button[type="button"]:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <header>
        <h2>Update Registrations</h2>
    </header>
    <div class="page">
        <div class="content">
            <h1>Edit Registrations</h1>
            <div class="projects">
                <form action="update-registration" method="post">
                    <label for="registerID">Register ID:</label>
                    <input type="text" id="registerID" name="registerID" required>

                    <label for="userID">User ID:</label>
                    <input type="text" id="userID" name="userID" required>

                    <label for="subjectID">Subject ID:</label>
                    <input type="text" id="subjectID" name="subjectID" required>

                    <label for="packageID">Package ID:</label>
                    <input type="text" id="packageID" name="packageID" required>

                    <label for="totalCost">Total Cost:</label>
                    <input type="number" id="totalCost" name="totalCost" required>

                    <label for="status">Status:</label>
                    <input type="text" id="status" name="status" required>

                    <button type="submit">Update</button>
                </form>
            </div>
        </div>
    </div>
    <footer>
        <p>&copy; 2024 Update Registrations</p>
    </footer>
</body>
</html>