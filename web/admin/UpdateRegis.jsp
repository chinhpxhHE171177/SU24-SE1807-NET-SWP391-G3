<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            box-sizing: border-box;
            font-family: 'Poppins', 'sans-serif';
            font-size: 18px;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            display: flex;
            flex-direction: column;
            width: 85%;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        .form-label {
            margin-top: 10px;
        }

        .btn-primary {
            background-color: #007BFF;
            border: none;
            color: white;
            padding: 10px 20px;
            border-radius: 3px;
            cursor: pointer;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function updatePackageDetails() {
            const validFrom = document.getElementById("validfrom").value;
            const validTo = document.getElementById("validto");
            const packageID = document.getElementById("packageID").value;
            const totalCost = document.getElementById("totalcost");
            let daysToAdd = 0;
            let costToAdd = 0;

            switch (parseInt(packageID)) {
                case 1:
                    daysToAdd = 7;
                    costToAdd = 0;
                    break;
                case 2:
                    daysToAdd = 60;
                    costToAdd = 200;
                    break;
                case 3:
                    daysToAdd = 90;
                    costToAdd = 350;
                    break;
                case 4:
                    daysToAdd = 365;
                    costToAdd = 800;
                    break;
                default:
                    daysToAdd = 0;
                    costToAdd = 0;
            }

            if (validFrom) {
                const fromDate = new Date(validFrom);
                fromDate.setDate(fromDate.getDate() + daysToAdd);
                validTo.value = fromDate.toISOString().substring(0, 10);
            }

            totalCost.value = costToAdd;
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Update Registration</h1>
        <form action="UpdateRegis" method="post">
            <input type="hidden" name="registerID" value="${registration.registerID}" />
            <div class="mb-3">
                <label for="username" class="form-label">User Name</label>
                <input type="text" class="form-control" id="username" value="${registration.fullName}" readonly>
            </div>
            <div class="mb-3">
                <label for="subjectname" class="form-label">Subject Name</label>
                <input type="text" class="form-control" id="subjectname" value="${registration.subjectName}" readonly>
            </div>
            <div class="mb-3">
                <label for="packageID" class="form-label">Package</label>
                <select class="form-control" id="packageID" name="packageID" onchange="updatePackageDetails()">
                    <option value="1" <c:if test="${registration.packageID == 1}">selected</c:if>>Free</option>
                    <option value="2" <c:if test="${registration.packageID == 2}">selected</c:if>>Basic Package</option>
                    <option value="3" <c:if test="${registration.packageID == 3}">selected</c:if>>Medium Package</option>
                    <option value="4" <c:if test="${registration.packageID == 4}">selected</c:if>>Premium Package</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="totalcost" class="form-label">Total Cost</label>
                <input type="text" class="form-control" id="totalcost" name="totalCost" value="${registration.totalCost}" readonly>
            </div>
            <div class="mb-3">
                <label for="status" class="form-label">Status</label>
                <input type="text" class="form-control" id="status" name="status" value="${registration.status}">
            </div>
            <div class="mb-3">
                <label for="validfrom" class="form-label">Valid From</label>
                <input type="date" class="form-control" id="validfrom" name="validFrom" 
                       value="<fmt:formatDate value='${registration.validFrom}' pattern='yyyy-MM-dd'/>" onchange="updatePackageDetails()">
            </div>
            <div class="mb-3">
                <label for="validto" class="form-label">Valid To</label>
                <input type="date" class="form-control" id="validto" name="validTo" 
                       value="<fmt:formatDate value='${registration.validTo}' pattern='yyyy-MM-dd'/>" readonly>
            </div>
            <button type="submit" class="btn-primary">Update</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 