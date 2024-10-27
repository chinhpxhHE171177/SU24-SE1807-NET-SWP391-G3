<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.List" %>
<%@page import="model.Permission" %>
<%@page import="model.Users" %>

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
  <title>MSS QRCODE - Short Stop Info</title>
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
      font-size: 18px;
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
<%
    // Check if the user is logged in
    Users user = (Users) session.getAttribute("users");
    List<Permission> permissions = (List<Permission>) session.getAttribute("permissions");

    if (user == null || permissions == null) {
        response.sendRedirect("login");
        return;
    }

    // Check if the user has access to this page
    boolean hasAccess = false;
    for (Permission p : permissions) {
        if ("ShortStopInfo".equals(p.getPageName()) && p.isCanAccess()) {
            hasAccess = true;
            break;
        }
    }

    if (!hasAccess) {
        response.sendRedirect("error.jsp");
        return;
    }
%>
<%@include file="components/navigation.jsp"%>
<body>
<header>
  <div class="container">
    <h1><a href="ShortStopInfo" style="text-decoration: none; color:#D0D0D0; font-weight: bolder ">Device Management</a></h1>
    <div class="search-bar">
      <form action="ShortStopInfo" method="POST"> <!-- Search form with GET method -->
        <input type="hidden" name="action" value="search"> <!-- Hidden field for search action -->
        <!-- Search fields -->
        <input type="text" name="deviceCode" placeholder="Device Code" class="search-input"
               value="${not empty deviceCode ? deviceCode : ''}">
        <select name="line" class="search-input">
          <option value="">-- Select Line --</option>
          <c:forEach items="${requestScope.listl}" var="l">
            <option value="${l.id}" ${l.id == line ? 'selected' : ''}>${l.name}</option>
          </c:forEach>
        </select>
        <select name="stage" class="search-input">
          <option value="">-- Select Stage --</option>
          <c:forEach items="${requestScope.listst}" var="p">
            <option value="${p.id}" ${p.id == stage ? 'selected' : ''}>${p.name}</option>
          </c:forEach>
        </select>
        <input type="text" name="deviceName" placeholder="Device Name" class="search-input"
               value="${not empty deviceName ? deviceName : ''}">

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

<% 
   boolean canAdd = permissions.stream()
       .anyMatch(p -> "ShortStopInfo".equals(p.getPageName()) && p.isCanAdd());
%>
<main class="container" style="max-width: 1450px; font-size: 14px">
  <div class="button-group">
    <button class="add-device-btn" id="openModalAddBtn" <%= canAdd ? "" : "disabled" %>><i class="fas fa-plus"></i> Add New</button>
    <div class="file-export_box">
      <a href="/mssqrcode/export" style="padding: 10px 20px"><i class="fas fa-file-export"></i> Export to Excel</a>
    </div>
  </div>
  <form style="width: 40%" action="import" method="post" enctype="multipart/form-data">
    <input type="file" name="file" accept=".xlsx, .xls">
    <input type="submit" value="Upload Excel File">
  </form>
  <p class="total-btn">Total: ${requestScope.totalLogs} ShortPause</p>
  <h5 style="color: red">${errorMessage}</h5>
  <h5 style="color: green">${sucessMessage}</h5>
  <%
    String successMessage = request.getParameter("successMessage");
    String errorMessage = request.getParameter("errorMessage");
  %>

  <% if (successMessage != null && !successMessage.isEmpty()) { %>
  <div id="successMessage" class="alert alert-success">
    <%= successMessage %>
  </div>
  <% } %>

  <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
  <div id="errorMessage" class="alert alert-danger">
    <%= errorMessage %>
  </div>
  <% } %>
  <table>
    <thead>
    <tr>
      <th>#</th>
      <th>Device Code</th>
      <th>Device Name</th>
      <th>Line</th>
      <th>Stage</th>
      <th>Error Description</th>
      <th>Start Date</th>
      <th>End Date</th>
      <th>TotalTime(m)</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody id="deviceTableBody">
    <c:forEach items="${lists}" var="i" varStatus="loop">
      <tr>
        <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
        <td>${i.equipmentCode}</td>
        <td>${i.equipmentName}</td>
        <td>${i.lineName}</td>
        <td>${i.stageName}</td>
        <td>${i.content}</td>
        <td><fmt:formatDate value="${i.startDate}" pattern="yyyy-MM-dd HH:mm"/></td>
        <td><fmt:formatDate value="${i.endDate}" pattern="yyyy-MM-dd HH:mm"/></td>
        <td style="text-align: center">${i.duration}</td>
        <td>
          <% 
               boolean canEdit = permissions.stream()
                   .anyMatch(p -> "ShortStopInfo".equals(p.getPageName()) && p.isCanEdit());
               boolean canDelete = permissions.stream()
                   .anyMatch(p -> "ShortStopInfo".equals(p.getPageName()) && p.isCanDelete());
           %>
          <button class="btn" onclick="showEditModelForm('${i.id}', '${i.equipmentId}', '${i.equipmentCode}', '${i.content}', '${i.startDate}', '${i.endDate}', '${i.stageId}')" <%= canEdit ? "" : "disabled" %>><i class="fas fa-edit"></i></button>
          <button onclick="doDeleteDevice('${i.id}')"><i class="fas fa-trash"  <%= canEdit ? "" : "disabled" %>></i></button>
          <button onclick="viewDetailModalForm('${i.equipmentCode}', '${i.equipmentName}')"><i class="fas fa-eye"></i></button>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <c:if test="${empty requestScope.lists}">
    <p style="text-align: center; color: #666666; margin: 10px 20px">No short stop pause info found matching your criteria.</p>
  </c:if>

  <nav aria-label="Page navigation">
    <ul class="pagination">
      <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
        <a class="page-link" href="ShortStopInfo?action=search&pageIndex=${currentPage - 1}&deviceCode=${deviceCode}&line=${line}&stage=${stage}&deviceName=${deviceName}&sortByDate=${sortByDate}" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <c:forEach begin="1" end="${totalPage}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
          <a class="page-link" href="ShortStopInfo?action=search&pageIndex=${i}&deviceCode=${deviceCode}&line=${line}&stage=${stage}&deviceName=${deviceName}&sortByDate=${sortByDate}">${i}</a>
        </li>
      </c:forEach>
      <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
        <a class="page-link" href="ShortStopInfo?action=search&pageIndex=${currentPage + 1}&deviceCode=${deviceCode}&line=${line}&stage=${stage}&deviceName=${deviceName}&sortByDate=${sortByDate}" aria-label="Next">
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
    <form id="deviceNewForm" action="ShortStopInfo?action=add" method="POST"> <!-- Add form with POST method -->
      <input type="hidden" name="action" value="add"> <!-- Hidden field for add action -->

      <!-- Select Device -->
      <select class="mb-3" name="deviceId" id="deviceId" required>
        <option value="">-- Select Device --</option>
        <c:forEach items="${requestScope.liste}" var="l">
          <option value="${l.id}">${l.name}</option>
        </c:forEach>
      </select>

      <!-- Select Error (Populated dynamically based on selected Device) -->
      <select class="mb-3" name="errorDescription" id="errorDescription" required>
        <option value="">-- Select Error --</option>
      </select>

      <input class="mb-3" type="datetime-local" id="startTime" name="startTime" placeholder="Start Time" required>
      <input class="mb-3" type="datetime-local" id="endTime" name="endTime" placeholder="End Time" required>
      <select class="mb-3" name="stageId">
        <option value="">-- Select Stage --</option>
        <c:forEach items="${requestScope.listst}" var="p">
          <option value="${p.id}">${p.name}</option>
        </c:forEach>
      </select>

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

<!-- Edit Device Modal -->
<div class="modal fade" id="editDeviceModal">
  <div class="modal-dialog modal-lg"> <!-- Increased modal size -->
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Edit Error History</h5>
      </div>
      <div class="modal-body">
        <form id="editDeviceForm" action="ShortStopInfo" method="POST">
          <input type="hidden" name="action" value="update">

          <input class="form-control mb-3" type="hidden" id="errorId" name="errorId" placeholder="Device Id">
          <input class="form-control mb-3" type="hidden" id="editDeviceId" name="deviceId" placeholder="Device Id" readonly>
          <input class="form-control mb-3" type="text" id="editDeviceCode" name="deviceCode" placeholder="Device Code" readonly>
            <!-- Select Error (Populated dynamically based on selected Device) -->
<%--            <select class="mb-3" name="errorDescription" id="content" required>--%>
<%--              <option value="">-- Select Error --</option>--%>
<%--            </select>--%>
          <input class="mb-3" type="text" id="content" name="errorDescription" placeholder="Error Description">

          <input class="mb-3" type="datetime-local" id="startDate" name="startTime" placeholder="Start Time" required>
          <input class="mb-3" type="datetime-local" id="endDate" name="endTime" placeholder="End Time" required>

          <select class="mb-3" id="stageId" name="stageId">
            <option value="">-- Select Stage --</option>
            <c:forEach items="${requestScope.listst}" var="p">
              <option value="${p.id}">${p.name}</option>
            </c:forEach>
          </select>

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
    document.querySelector('input[name="deviceCode"]').value = '';
    document.querySelector('select[name="line"]').selectedIndex = 0;
    document.querySelector('select[name="stage"]').selectedIndex = 0;
    document.querySelector('input[name="deviceName"]').value = '';
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
  function viewDetailModalForm(codeimage, eName) {
    $('#viewQrCodeDetail').attr('src', codeimage); // Sets the QR code image
    $('#viewErrorName').text(eName); // Corrected to use .text() for displaying the error name in the <p> tag
    $('#viewDetailModalForm').modal('show'); // Show the modal
  }

  // Function to close the modal using Bootstrap's modal method
  function closeModal(modalId) {
    $('#' + modalId).modal('hide');
  }

  function doDeleteDevice(errorId) {
    if (confirm("Are you sure you want to delete this short stop pause log?")) {
      window.location.href = 'ShortStopInfo?action=delete&id=' + errorId;
    }
  }
</script>
<script>
  document.getElementById('deviceId').addEventListener('change', function() {
    var deviceId = this.value;
    var errorDescriptionSelect = document.getElementById('errorDescription');

    // Clear the current options
    errorDescriptionSelect.innerHTML = '<option value="">-- Select Error --</option>';

    if (deviceId) {
      // Make AJAX request to get error descriptions for the selected device
      var xhr = new XMLHttpRequest();
      xhr.open('GET', 'ShortStopInfo?deviceId=' + deviceId, true);
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
          var errors = JSON.parse(xhr.responseText);
          console.log(errors);  // In ra để kiểm tra dữ liệu

          // Sử dụng Set để lưu trữ các mô tả lỗi không trùng lặp
          var uniqueErrors = new Set();

          // Kiểm tra nếu danh sách lỗi có giá trị hợp lệ
          if (errors.length > 0) {
            errors.forEach(function(error) {
              uniqueErrors.add(error.content); // Thêm mô tả lỗi vào Set
            });

            // Thêm các mô tả lỗi duy nhất vào dropdown
            uniqueErrors.forEach(function(content) {
              var option = document.createElement('option');
              option.value = content; // Giá trị là mô tả lỗi
              option.text = content;   // Hiển thị là mô tả lỗi
              errorDescriptionSelect.appendChild(option);
            });

            // Thêm tùy chọn "Other" vào cuối danh sách
            var otherOption = document.createElement('option');
            otherOption.value = 'other'; // Giá trị cho tùy chọn khác
            otherOption.text = 'Other';   // Hiển thị văn bản "Other"
            errorDescriptionSelect.appendChild(otherOption);
          } else {
            console.log("No errors found for this device.");
          }
        }
      };
      xhr.send();
    }
  });

  // Thêm sự kiện cho dropdown errorDescription
  document.getElementById('errorDescription').addEventListener('change', function() {
    var selectedValue = this.value;

    // Nếu chọn "Other", thêm một trường nhập liệu
    if (selectedValue === 'other') {
      var customError = prompt("Please enter the error description:");
      if (customError) {
        // Chỉ thêm nếu mô tả lỗi không rỗng
        if (customError.trim() !== '') {
          var option = document.createElement('option');
          option.value = customError; // Giá trị là mô tả lỗi mới
          option.text = customError;   // Hiển thị là mô tả lỗi mới
          this.appendChild(option); // Thêm vào dropdown
          this.value = customError; // Chọn giá trị mới
        }
      } else {
        this.value = ''; // Reset nếu không có nhập
      }
    }
  });
</script>
<script>
  // Function to show the Edit Modal with pre-filled values
  function showEditModelForm(errorId, equipmentId, equipmentCode, errorDescription, startTime, endTime, stageId) {
    // Format the startTime and endTime for input type datetime-local
    // const formattedStartTime = new Date(startTime).toISOString().slice(0, 16);
    // const formattedEndTime = new Date(endTime).toISOString().slice(0, 16);

    // Set the values in the modal inputs
    $('#errorId').val(errorId);
    $('#editDeviceId').val(equipmentId);
    $('#editDeviceCode').val(equipmentCode);  // Device Code
    $('#content').val(errorDescription);  // Error Description
    $('#startDate').val(startTime);  // Start Time
    $('#endDate').val(endTime);  // End Time
    $('#stageId').val(stageId);  // Stage

    // Show the edit modal
    $('#editDeviceModal').modal('show');
  }
</script>
<script>
  // Set a timer to hide the success and error messages after 3 minutes (180000 milliseconds)
  setTimeout(function() {
    var successElement = document.getElementById('successMessage');
    var errorElement = document.getElementById('errorMessage');

    if (successElement) {
      successElement.style.display = 'none';
    }
    if (errorElement) {
      errorElement.style.display = 'none';
    }
  }, 3000);
</script>
</body>
</html>