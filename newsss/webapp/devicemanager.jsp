<%--
  Created by IntelliJ IDEA.
  User: dmvns00004
  Date: 9/19/2024
  Time: 10:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- Favicon -->
    <link href="https://hust.edu.vn/assets/sys/sinh-vien/2017/03/183641.png" rel="shortcut icon" />
    <link rel="stylesheet" href="styles.css" />
    <!-- Link Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <title>Device Management</title>
    <style>
        * {
            box-sizing: border-box;
            /*margin: 0;*/
            /*padding: 0;*/
        }

        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            color: #333;
        }

        .container {
            width: 100%;
            max-width: 1500px;
            margin: 20px 20px auto;
        }

        header {
            background-color: #35424a;
            color: white;
            padding: 20px 0;
        }

        header .container {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        header h1 {
            font-size: 24px;
        }

        .search-bar {
            display: flex;
            flex-wrap: wrap;
        }

        .search-input {
            padding: 10px;
            width: 30%;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-right: 5px;
            margin-bottom: 10px;
        }

        .search-bar button {
            padding: 10px 20px;
            background-color: #5cb85c;
            border: none;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }

        main {
            padding: 20px;
            background: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }

        /*.add-device-btn {*/
        /*    background-color: #35424a;*/
        /*    color: white;*/
        /*    padding: 10px 15px;*/
        /*    border: none;*/
        /*    border-radius: 5px;*/
        /*    cursor: pointer;*/
        /*    margin-bottom: 15px;*/
        /*}*/
        /*.add-device-btn:hover {*/
        /*    background-color: #4e636e;*/
        /*}*/

        /*!* File Export Box Styles *!*/
        /*.file-export_box {*/
        /*    display: inline-block;*/
        /*    margin-left: 20px;*/
        /*}*/

        /*.file-export_box a {*/
        /*    color: #28a745; !* Bootstrap success color *!*/
        /*    text-decoration: none;*/
        /*    font-weight: bold;*/
        /*    transition: color 0.3s;*/
        /*}*/

        /*.file-export_box a:hover {*/
        /*    color: #218838; !* Darker shade on hover *!*/
        /*}*/
        /* Button Styles */
        .add-device-btn, .file-export_box a, form input[type="submit"] {
            padding: 10px 20px;
            border-radius: 5px;
            font-weight: bold;
            text-align: center;
            cursor: pointer;
        }

        .add-device-btn {
            background-color: #35424a;
            color: white;
        }

        .add-device-btn:hover {
            background-color: #4e636e;
        }

        .file-export_box a {
            text-decoration: none;
            background-color: #28a745;
            color: white;
        }

        .file-export_box a:hover {
            background-color: #218838;
        }

        form input[type="submit"] {
            background-color: #007bff;
            color: white;
        }

        form input[type="submit"]:hover {
            background-color: #0056b3;
        }

        /* Align buttons and upload form */
        .button-group {
            display: flex;
            align-items: center;
            gap: 15px;
            margin-bottom: 20px;
        }

        form input[type="file"] {
            display: inline-block;
            margin-right: 10px;
        }

        .total-btn {
            display: flex;
            justify-content: end;
            margin-bottom: 10px;
            font-weight: bolder;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid #ccc;
        }

        th, td {
            padding: 12px;
            text-align: left;
            background-color: #f9f9f9;
        }

        th {
            background-color: #e9ecef;
        }

        td button {
            border: none;
            padding: 5px 2px;
            border-radius: 5px;
            cursor: pointer;
        }

        td button:hover {
            background-color: #0056b3;
        }

        .status-working {
            color: #28a745; /* Green */
        }

        .status-not-working {
            color: #dc3545; /* Red */
        }

        .status-working i,
        .status-not-working i {
            margin-right: 5px; /* Space between icon and text */
        }

        /* Pagination Styles */
        .pagination {
            display: flex;
            justify-content: center;
            list-style-type: none;
            padding: 20px 0;
        }

        .pagination li {
            margin: 0 5px;
        }

        .pagination a {
            text-decoration: none;
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            color: #333;
        }

        .pagination .active a {
            background-color: #35424a;
            color: white;
        }

        .pagination .disabled a {
            color: #aaa;
        }

        /*Modal */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 100%;
            max-width: 750px;
            border-radius: 5px;
        }
        /* Input Styles */
        input, select {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn-closeses {
            width: 20%;
            padding: 10px 25px;
            color: white;
            font-size: 14px;
            font-weight: bold;
            background-color: gray;
            border: 1px solid #808080;
            cursor: pointer;
            border-radius: 5px;
        }
        .btn-closeses:hover {
            background-color: #b4aeae;
        }
        .btn-save {
            /*width: 28%;*/
            padding: 10px 25px;
            color: white;
            font-size: 14px;
            font-weight: bold;
            background-color: #0066FF;
            border: 1px solid #808080;
            cursor: pointer;
            border-radius: 5px;
        }
        .btn-save:hover {
            background-color: #2476f0;
        }
    </style>

</head>
<%@include file="components/navigation.jsp"%>
<body>
<header>
    <div class="container">
        <h1><a href="DeviceManager" style="text-decoration: none; color:#D0D0D0; font-weight: bolder ">Device Management</a></h1>
        <div class="search-bar">
            <form action="DeviceManager" method="POST"> <!-- Search form with GET method -->
                <input type="hidden" name="action" value="search"> <!-- Hidden field for search action -->
                <!-- Search fields -->
                <input type="text" name="deviceId" placeholder="Device ID" class="search-input"
                       value="${not empty deviceId ? deviceId : ''}">
                <select name="lineCode" class="search-input">
                    <option value="">-- Select Line --</option>
                    <c:forEach items="${requestScope.listl}" var="l">
                        <option value="${l.linecode}" ${l.linecode == lineCode ? 'selected' : ''}>${l.linename}</option>
                    </c:forEach>
                </select>
                <select name="processId" class="search-input">
                    <option value="">-- Select Process --</option>
                    <c:forEach items="${requestScope.listp}" var="p">
                        <option value="${p.processId}" ${p.processId == processId ? 'selected' : ''}>${p.processname}</option>
                    </c:forEach>
                </select>
                <input type="text" name="deviceName" placeholder="Device Name" class="search-input"
                       value="${not empty deviceName ? deviceName : ''}">
                <input type="text" name="origin" placeholder="Origin" class="search-input"
                       value="${not empty origin ? origin : ''}">

                <!-- Sort by Date -->
                <select name="sortByDate" class="search-input">
                    <option value="">-- Sort By Date --</option>
                    <option value="asc" ${sortByDate == 'asc' ? 'selected' : ''}>Ascending</option>
                    <option value="desc" ${sortByDate == 'desc' ? 'selected' : ''}>Descending</option>
                </select>

                <!-- Buttons -->
                <button type="submit" class="btn btn-primary">Search</button>
                <button type="button" class="btn btn-secondary" onclick="resetSearch()">Reset</button>
            </form>
        </div>
    </div>
</header>


<main class="container">
    <!-- Notification Message -->
<%--    <c:if test="${not empty message}">--%>
<%--        <div class="alert ${message.contains('successfully') ? 'alert-success' : 'alert-danger'}" role="alert">--%>
<%--                ${message}--%>
<%--        </div>--%>
<%--    </c:if>--%>
    <c:if test="mess != null">
        ${mess}
    </c:if>
<%--    <button class="add-device-btn" id="openModalAddBtn"><i class="fas fa-plus"></i> Add Device</button>--%>
<%--    <div class="file-export_box ms-4">--%>
<%--        <a href="/ProjectDemo2/export" class="">--%>
<%--            <span class="">Export to excel</span>--%>
<%--            <i class="fas fa-file-export"></i>--%>
<%--        </a>--%>
<%--    </div>--%>
<%--    <form action="import" method="post" enctype="multipart/form-data">--%>
<%--        <input type="file" name="file" accept=".xlsx, .xls">--%>
<%--        <input type="submit" value="Upload Excel File">--%>
<%--    </form>--%>
    <div class="button-group">
        <button class="add-device-btn" id="openModalAddBtn"><i class="fas fa-plus"></i> Add Device</button>
        <div class="file-export_box">
            <a href="/ProjectDemo2/export" style="padding: 10px 20px"><i class="fas fa-file-export"></i> Export to Excel</a>
        </div>
    </div>
    <form style="width: 40%" action="import" method="post" enctype="multipart/form-data">
        <input type="file" name="file" accept=".xlsx, .xls">
        <input type="submit" value="Upload Excel File">
    </form>
    <p class="total-btn">Total: ${requestScope.totalDevices} Devices</p>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Device ID</th>
            <th>Line Code</th>
            <th>Device Name</th>
            <th>Process Name</th>
            <th>Date Use</th>
            <th>Origin</th>
            <th>YOM</th>
            <th>Location</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="deviceTableBody">
        <c:forEach items="${listd}" var="d" varStatus="loop">
            <tr>
                <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                <td>${d.deviceid}</td>
                <td>${d.linecode}</td>
                <td>${d.devicename}</td>
                <td>${d.processName}</td>
                <td><fmt:formatDate value="${d.dateuse}" pattern="dd/MM/yyyy"/></td>
                <td>${d.origin}</td>
                <td>${d.yom}</td>
                <td>${d.location}</td>
                <td>
                        <span class="${d.status == 'Working' ? 'status-working' : 'status-not-working'}">
                            <i style="display: flex; justify-content: center; align-items: center" class="${d.status == 'Working' ? 'fas fa-check-circle' : 'fas fa-times-circle'}"></i>
                        </span>
                </td>
                <td>
                    <!-- Button to Trigger Edit Image Modal -->
                    <button class="btn" onclick="showEditModelForm('${d.deviceid}', '${d.linecode}', '${d.devicename}', '${d.processId}', '${d.dateuse}', '${d.origin}', '${d.yom}', '${d.qrcode}', '${d.location}', '${d.status}')"><i class="fas fa-edit"></i></button>
                    <button onclick="doDeleteDevice('${d.deviceid}')"><i class="fas fa-trash"></i></button>
                    <button onclick="viewDetailModalForm('${d.qrcode}', '${d.eName}')"><i class="fas fa-eye"></i></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${empty requestScope.listd}">
        <p style="text-align: center; color: #666666; margin: 10px 20px">No devices found matching your criteria.</p>
    </c:if>

    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="DeviceManager?pageIndex=${currentPage - 1}" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:forEach begin="1" end="${totalPage}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="DeviceManager?pageIndex=${i}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
                <a class="page-link" href="DeviceManager?pageIndex=${currentPage + 1}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</main>

<!-- Modal Add New Devices -->
<div id="myModalNewDevices" class="modal">
    <div class="modal-content">
        <h2>Add New Devices</h2>
        <hr/>
        <form id="deviceNewForm" action="DeviceManager?action=add" method="POST"> <!-- Add form with POST method -->
            <input type="hidden" name="action" value="add"> <!-- Hidden field for add action -->

            <!-- Add device fields -->
            <input class="mb-3" type="text" id="deviceId" name="deviceId" placeholder="Device ID" required>
            <select class="mb-3" name="lineCode" required>
                <option value="">-- Select Line --</option>
                <c:forEach items="${requestScope.listl}" var="l">
                    <option value="${l.linecode}">${l.linename}</option>
                </c:forEach>
            </select>
            <input class="mb-3" type="text" id="deviceName" name="deviceName" placeholder="Device Name" required>
            <select class="mb-3" name="processId">
                <option value="">-- Select Process --</option>
                <c:forEach items="${requestScope.listp}" var="p">
                    <option value="${p.processId}">${p.processname}</option>
                </c:forEach>
            </select>
            <input class="mb-3" type="date" id="dateUse" name="dateUse" placeholder="Date Use" required>
            <input class="mb-3" type="text" id="origin" name="origin" placeholder="Origin">
            <input class="mb-3" type="number" id="yom" name="yom" placeholder="Year of Manufacture (YOM)" required>
            <input class="mb-3" type="text" id="location" name="location" placeholder="Location">

            <!-- Error message display -->
            <h5 style="color: red">${errorMessage}</h5>
            <hr/>

            <!-- Buttons -->
            <footer class="modal-footer">
                <button type="button" class="btn-closeses">Close</button>
                <button type="submit" class="btn-save">Add</button>
            </footer>
        </form>
    </div>
</div>

<<!-- Edit Model Modal -->
<div class="modal fade" id="editDeviceModal">
    <div class="modal-dialog modal-lg"> <!-- Increased modal size -->
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Edit Model</h5>
            </div>
            <div class="modal-body">
                <form id="editDeviceForm" action="DeviceManager" method="POST">
                    <input type="hidden" name="action" value="update">

                    <div class="row mb-3">
                        <div class="col">
                            <input class="form-control" type="text" id="editDeviceId" name="deviceId" placeholder="Device ID" readonly>
                        </div>
                        <div class="col">
                            <select class="form-control" id="editLineCode" name="lineCode" required>
                                <c:forEach items="${requestScope.listl}" var="l">
                                    <option value="${l.linecode}">${l.linename}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col">
                            <input class="form-control" type="text" id="editDeviceName" name="deviceName" placeholder="Tên Thiết Bị" required>
                        </div>
                        <div class="col">
                            <select class="form-control" id="editProcessId" name="processId" required>
                                <c:forEach items="${requestScope.listp}" var="p">
                                    <option value="${p.processId}">${p.processname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col">
                            <input class="mb-3 form-control" type="date" id="editDateUse" name="dateUse" placeholder="Ngày Sử Dụng" required>
                        </div>
                        <div class="col">
                            <input class="mb-3 form-control" type="text" id="editOrigin" name="origin" placeholder="Nguồn Gốc">
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col">
                            <input class="mb-3 form-control" type="text" id="editYom" name="yom" placeholder="Năm Sản Xuất">
                        </div>
                        <div class="col">
                            <input type="file" class="mb-3 form-control" id="editQrCodeUpload" name="qrcode" accept="image/*" onchange="previewQrCode()">
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col">
                            <input class="mb-3 form-control" type="text" id="editLocation" name="location" placeholder="Vị Trí">
                        </div>
                        <div class="col">
                            <select class="mb-3 form-control" id="editStatus" name="status" required>
                                <option value="Working" ${devices.status == 'Working' ? 'selected' : ''}>Working</option>
                                <option value="Not Working" ${devices.status == 'Not Working' ? 'selected' : ''}>Not Working</option>
                            </select>
                        </div>
                    </div>

                    <!-- QR Code Image Display -->
                    <img id="editQrCodeImage" src="" alt="QR Code" class="mb-3" style="width: 100px; height: 100px;">

                    <!-- Error Message Display -->
                    <h5 style="color: red">${errorMessage}</h5>

                    <hr />
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="closeModal('editDeviceModal')">Close</button>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--<div class="modal fade" id="viewDetailModalForm">--%>
<%--    <div class="modal-dialog modal-lg">--%>
<%--        <div class="modal-content">--%>
<%--            <div class="modal-header">--%>
<%--                <h5 class="modal-title">View Detail Devices</h5>--%>

<%--            </div>--%>
<%--            <div class="modal-body">--%>
<%--                <!-- QR Code Image Display -->--%>
<%--                <img id="viewQrCodeDetail" src="" alt="QR Code" class="mb-3" style="width: 100px; height: 100px;">--%>
<%--            </div>--%>
<%--            <div class="modal-footer">--%>
<%--                <button type="button" class="btn-closeses" onclick="closeModal('viewDetailModalForm')">Close</button>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<!-- Modal: View Detail Devices -->
<div class="modal fade" id="viewDetailModalForm">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header bg-secondary text-white">
                <h5 class="modal-title font-weight-bold">Device Details</h5>
            </div>

            <!-- Modal Body -->
            <div class="modal-body">
                <!-- QR Code Image Display -->
                <div class="text-center mb-4">
                    <img id="viewQrCodeDetail" src="" alt="QR Code" class="img-fluid rounded" style="width: 150px; height: 150px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);">
                </div>

                <!-- Device Error Information -->
                <h5 class="text-muted mb-3">Device Errors</h5>
                <p id="viewErrorName" class="errorName text-danger font-italic"></p>
            </div>

            <!-- Modal Footer -->
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-secondary" onclick="closeModal('viewDetailModalForm')">Close</button>
            </div>
        </div>
    </div>
</div>



<script>
    function resetSearch() {
        // Clear input fields in the search form
        document.querySelector('input[name="deviceId"]').value = '';
        document.querySelector('select[name="lineCode"]').selectedIndex = 0;
        document.querySelector('select[name="processId"]').selectedIndex = 0;
        document.querySelector('input[name="deviceName"]').value = '';
        document.querySelector('input[name="origin"]').value = '';
        document.querySelector('select[name="sortByDate"]').selectedIndex = 0;
    }
</script>
<script>
    document.getElementById("openModalAddBtn").onclick = function() {
        document.getElementById("myModalNewDevices").style.display = "block";
    };

    document.querySelector(".btn-closeses").onclick = function() {
        document.getElementById("myModalNewDevices").style.display = "none";
    };

    function openModal(modalId) {
        document.getElementById(modalId).style.display = "block";
    }

    function closeModal(modalId) {
        document.getElementById(modalId).style.display = "none";
    }

    // Gọi hàm openModal khi nhấn nút "Add Device"
    document.getElementById('openModalAddBtn').onclick = function() {
        openModal('myModalNewDevices');
    };

    // Hàm xem chi tiết thiết bị
    function viewDetailModalForm(codeimage) {
        document.getElementById('viewQrCodeDetail').src = codeimage;
        openModal('viewDetailModalForm');
    }
</script>
<script>
    // Function to show the edit form modal with pre-filled data
    function showEditModelForm(id, code, name, process, date, origin, yom, image, location, status) {
        $('#editDeviceId').val(id);
        $('#editLineCode').val(code);
        $('#editDeviceName').val(name);
        $('#editProcessId').val(process);
        $('#editDateUse').val(date);
        $('#editOrigin').val(origin);
        $('#editYom').val(yom);
        $('#editQrCodeImage').attr('src', image);
        // if (image) {
        //     $('#editQrCodeImage').attr('src', 'images/' + image);
        // } else {
        //     $('#editQrCodeImage').attr('src', 'images/default-placeholder.png');
        // }
        $('#editLocation').val(location);
        $('#editStatus').val(status);
        $('#editDeviceModal').modal('show');
    }

    // if (image) {
    //     $('#editQrCodeImage').attr('src', 'images/QrCode/' + image);
    // } else {
    //     $('#editQrCodeImage').attr('src', 'images/default-placeholder.png');
    // }
    function viewDetailModalForm(codeimage, eName) {
        $('#viewQrCodeDetail').attr('src', codeimage); // Sets the QR code image
        $('#viewErrorName').text(eName); // Corrected to use .text() for displaying the error name in the <p> tag
        $('#viewDetailModalForm').modal('show'); // Show the modal
    }

    // Function to close the modal using Bootstrap's modal method
    function closeModal(modalId) {
        $('#' + modalId).modal('hide');
    }

    function doDeleteDevice(deviceId) {
        if (confirm("Are you sure you want to delete this device?")) {
            window.location.href = 'DeviceManager?action=delete&id=' + deviceId;
        }
    }
</script>
</body>
</html>
