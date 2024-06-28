<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Registration</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <style>
            body {
                box-sizing: border-box;
                font-family: 'Poppins', 'sans-serif';
                background-color: #e9ecef;
                margin: 0;
                padding: 0;
            }

            .container {
                width: 80%;
                margin: 40px auto;
                padding: 20px;
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            h1 {
                margin-bottom: 20px;
                font-size: 2em;
                color: #333;
            }

            .form {
                background: #f8f9fa;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
                text-align: center; /* Đặt nút ở giữa */
            }

            label {
                margin: 0px 0 5px;
                font-weight: 500;
                color: #555;
                display: block;
                text-align: left;
            }

            input[type="text"],
            input[type="date"],
            select,
            textarea {
                padding: 10px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                width: 100%; /* Chiều rộng trừ đi 20px từ hai bên */
                box-sizing: border-box;
                font-size: 16px; /* Tăng cỡ chữ */

            }

            input[type="file"] {
                padding: 5px;
            }

            textarea {
                resize: vertical;
                height: 100px;
            }

            .submit-container {
                text-align: center;
                margin-top: 20px;
                
            }

            submit {
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 20px; /* Tăng cỡ chữ */
                background-color: #28a745; /* Màu nền xanh */
                color: white; /* Màu chữ trắng */
                transition: background-color 0.3s;
            }

            submit[type="submit"]:hover {
                background-color: #218838;
            }

            submit[type="submit"] {
                background-color: #6c757d;
                color: white;
                margin-left: 10px;
            }

            submit[type="submit"]:hover {
                background-color: #5a6268;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <h1 style="text-align: center;">Add Registration</h1> 

            <script>
                window.onload = function () {
                    document.getElementById("status").value = "1";
                };
                function updateTotalCost() {
                    var packageID = document.getElementById("packageID").value;
                    var totalCostInput = document.getElementById("totalCost");

                    switch (packageID) {
                        case "1":
                            totalCostInput.value = "0";
                            break;
                        case "2":
                            totalCostInput.value = "200";
                            break;
                        case "3":
                            totalCostInput.value = "350";
                            break;
                        case "4":
                            totalCostInput.value = "800";
                            break;
                        default:
                            totalCostInput.value = "1"; // Nếu không khớp với bất kỳ trường hợp nào, đặt giá trị rỗng
                            break;
                    }
                }
            </script>
            <form action="AddRegis" method="post">
                UserID: <input type="text" name="userID"><br>
                SubjectID: <select name="subjectID" required>
                    <option value="1">Physics</option>
                    <option value="2">Calculus</option>
                    <option value="3">History VN</option>
                    <option value="4">Literature VN</option> 
                    <option value="5">Geography VN</option>
                    <option value="6">MAE101</option>
                    <option value="7">PRF192</option>
                    <option value="8">CEA201</option> 
                    <option value="9">CSI104</option>
                    <option value="10">SSL101c</option>
                </select><br>
                PackageID:
                <select name="packageID" id="packageID" required onchange="updateTotalCost()">
                    <option value="1">Free</option>
                    <option value="2">Basic Package</option>
                    <option value="3">Medium Package</option>
                    <option value="4">Premium Package</option>
                </select><br>
                Total Cost: <input type="text" name="totalCost" id="totalCost" readonly><br>
                Status:
                <select name="status" id="status" required>           
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                </select><br>
                Valid From: <input type="date" name="validFrom"><br>
                <div class="submit-container">
                    <button type="submit" style="background-color: #28a745">Add</button>
                </div>
            </form>   
        </div>
    </body>
</html>