<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
  <title>MSS QRCODE - Device Management</title>
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

        .add-device-btn {
            background-color: #35424a;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-bottom: 15px;
        }
        .add-device-btn:hover {
            background-color: #4e636e;
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
        .total-btn {
            display: flex;
            justify-content: end;
            margin-bottom: 10px;
            font-weight: bolder;
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
            width: 80%;
            max-width: 500px;
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
        <h1><a href="DeviceManagement" style="text-decoration: none; color:#D0D0D0; font-weight: bolder ">Device Management</a></h1>
        <div class="search-bar">
            <form action="DeviceManagement" method="POST">
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
                <input type="text" name="origin" placeholder="Origin" class="search-input"
                       value="${not empty origin ? origin : ''}">

                <!-- Buttons -->
                <button type="submit" class="btn btn-primary">Search</button>
                <button type="button" class="btn btn-secondary" onclick="resetSearch()">Reset</button>        
            </form>
        </div>
    </div>
</header>


<main class="container" style="max-width: 96%">
    <button class="add-device-btn" id="openModalAddBtn"><i class="fas fa-plus"></i> Add New</button>
    <p class="total-btn">Total: ${requestScope.totalLogs} Devices</p>
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
            <th>Code</th>
            <th>Name</th>
            <th>Stage</th>
            <th>Line</th>
            <th>Date Use</th>
            <th>Origin</th>
            <th>YOM</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody id="deviceTableBody">
        <p:forEach items="${liste}" var="d" varStatus="loop">
            <tr>
                <td>${(currentPage - 1) * pageSize + loop.index + 1}</td>
                <td>${d.code}</td>
                <td>${d.name}</td>
                <td>${d.stageName}</td>
                <td>${d.lineName}</td>
                <td><fmt:formatDate value="${d.dateUse}" pattern="dd/MM/yyyy"/></td>
                <td>${d.origin}</td>
                <td>${d.yom}</td>
                <td>
                    <!-- Button to Trigger Edit Modal -->
                    <button class="btn" onclick="showEditModelForm('${d.id}', '${d.code}', '${d.name}', '${d.dateUse}', '${d.origin}', '${d.yom}', '${d.stageId}', '${d.issue}')"><i class="fas fa-pen"></i></button>
                    <button onclick="doDeleteDevice('${d.id}')"><i class="fas fa-trash"></i></button>
                    <button onclick="viewDetailModalForm('${d.code}', '${d.qrCode}', '${d.issue}')"><i class="fas fa-eye"></i></button>
                </td>       
            </tr>
        </p:forEach>
    </tbody>
</table>
    <p:if test="${empty requestScope.liste}">
        <p style="text-align: center; color: #666666; margin: 10px 20px">No devices found matching your criteria.</p>
    </p:if>

     <nav aria-label="Page navigation">
    <ul class="pagination">
      <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
        <a class="page-link" href="DeviceManagement?action=search&pageIndex=${currentPage - 1}&deviceCode=${deviceCode}&line=${line}&stage=${stage}&deviceName=${deviceName}&origin=${origin}" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <c:forEach begin="1" end="${totalPage}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
          <a class="page-link" href="DeviceManagement?action=search&pageIndex=${i}&deviceCode=${deviceCode}&line=${line}&stage=${stage}&deviceName=${deviceName}&origin=${origin}">${i}</a>
        </li>
      </c:forEach>
      <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
        <a class="page-link" href="DeviceManagement?action=search&pageIndex=${currentPage + 1}&deviceCode=${deviceCode}&line=${line}&stage=${stage}&deviceName=${deviceName}&origin=${origin}" aria-label="Next">
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
        <!-- Single Form Element -->
        <form id="deviceNewForm" action="DeviceManagement" method="POST">
            <input type="hidden" name="action" value="add">
            <input class="mb-3" type="text" id="deviceId" name="deviceId" placeholder="Device ID">
        
              <input class="mb-3" type="text" id="deviceName" name="deviceName" placeholder="Device Name">
           
            <select class="mb-3" name="stage">
                <option value="">-- Select Process --</option>
                <c:forEach items="${requestScope.listst}" var="p">
                    <option value="${p.id}"
                        ${p.id == stage ? 'selected' : ''}>${p.name}</option>
                </c:forEach>
            </select> 
            
            <input class="mb-3" type="text" id="issue" name="issue" placeholder="Issue">
            
           <!-- <select class="mb-3" name="line">
                <option value="">-- Select Line --</option>
                <c:forEach items="${requestScope.listl}" var="l">
                    <option value="${l.id}"
                        ${l.id == line ? 'selected' : ''}>${l.name}</option>
                </c:forEach>
            </select>  --> 
          
            <input class="mb-3" type="date" id="dateUse" name="dateUse" placeholder="Date Use">
            <input class="mb-3" type="text" id="origin" name="origin" placeholder="Origin">
            <input class="mb-3" type="number" id="yom" name="yom" placeholder="YOM / Year of manufacturing">
            
            <h5 style="color: red">${errorMessage}</h5>
            <hr/>
            <footer class="footer-btn">
              <button type="button" class="btn-closeses">Close</button>
              <button type="submit" class="btn-save">Add</button>
            </footer>
        </form>
    </div>
</div>

<div class="modal fade" id="editDeviceModal">
   <div class="modal-dialog modal-lg"> <!-- Increased modal size -->
      <div class="modal-content">
         <div class="modal-header">
            <h5 class="modal-title">Update Equipment</h5>
         </div>
         <div class="modal-body">
            <form id="editDeviceForm" action="DeviceManagement" method="POST">
               <input type="hidden" name="action" value="update">
               <input type="hidden" name="deviceId" id="editDeviceId"> <!-- Hidden field for Device ID -->
               
               <input class="mb-3" type="text" id="EditDeviceCode" name="deviceCode" placeholder="Device Code" required readonly="readonly">
               
               <input class="mb-3" type="text" id="EditDeviceName" name="deviceName" placeholder="Device Name" required>
               
               <select class="mb-3" id="EditStageId" name="stage" required>
                   <option value="">-- Select Stage --</option>
                   <c:forEach items="${requestScope.listst}" var="p">
                       <option value="${p.id}">${p.name}</option>
                   </c:forEach>
               </select>
               
               <input class="mb-3" type="text" id="EditIssue" name="issue" placeholder="Issue">
               
               <input class="mb-3" type="date" id="EditDateUse" name="dateUse" placeholder="Date of Use" required>
               
               <input class="mb-3" type="text" id="EditOrigin" name="origin" placeholder="Origin">
               
               <input class="mb-3" type="number" id="Edityom" name="yom" placeholder="Year of Manufacturing">
               
               <h5 style="color: red">${errorMessage}</h5>
               <hr />
               <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" onclick="closeEditModal('editDeviceModal')">Close</button>
                  <button type="submit" class="btn btn-primary">Save</button>
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
    document.querySelector('input[name="origin"]').value = '';
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
  function viewDetailModalForm(code) {
    document.getElementById('viewQrCodeDetail').src = codeimage;
    openModal('viewDetailModalForm');
  }
  
  //function viewDetailModalForm(code, qrcode, issue) {
	    // Ensure the QR code is a base64-encoded image with the correct prefix
	    //$('#viewQrCodeDetail').attr('src', 'data:image/png;base64,' + qrcode); 
	    
	    // Set the issue/error message
	    //$('#viewErrorName').text(issue); 
	    
	    // Show the modal with the QR code and issue details
	    //$('#viewDetailModalForm').modal('show'); 
	//}
  
function viewDetailModalForm(code, qrcode, issue) {
    let imageSrc = '';

    // Check if the provided QR code string is a base64 encoded image
    if (qrcode.startsWith('data:image/')) {
        // It's already a base64 image, use it directly
        imageSrc = qrcode;
    } else if (qrcode.endsWith('.png') || qrcode.endsWith('.jpeg') || qrcode.endsWith('.jpg') || qrcode.endsWith('.gif')) {
        // If the qrcode is a filename, prepend the /images/ directory
        imageSrc = 'images/' + qrcode;
    } else {
        // If it's base64 without a prefix, assume it's PNG for the QR code
        imageSrc = 'data:image/png;base64,' + qrcode;
    }

    // Set the image source
    $('#viewQrCodeDetail').attr('src', imageSrc);

    // Set the issue/error message
    $('#viewErrorName').text(issue);

    // Show the modal
    $('#viewDetailModalForm').modal('show');
}

	  // Function to close the modal using Bootstrap's modal method
	  function closeModal(modalId) {
	    $('#' + modalId).modal('hide');
	  }

  function doDeleteDevice(errorId) {
	    if (confirm("Are you sure you want to delete this equipment?")) {
	      window.location.href = 'DeviceManagement?action=delete&id=' + errorId;
	    }
	  }
</script>
<script>
  // Function to show the Edit Modal with pre-filled values
  function showEditModelForm(id, code, name, date, origin, yom, stageId, issue) {
    // Populate the form fields with the values
    $('#editDeviceId').val(id);  // Device ID
    $('#EditDeviceCode').val(code);  // Device Code
    $('#EditDeviceName').val(name);  // Device Name
    $('#EditStageId').val(stageId);  // Stage
    $('#EditIssue').val(issue);  // Issue
    $('#EditDateUse').val(date);  // Date of Use
    $('#EditOrigin').val(origin);  // Origin
    $('#Edityom').val(yom);  // Year of Manufacturing

    // Show the edit modal
    $('#editDeviceModal').modal('show');
  }
  
  function closeEditModal() {
    $('#editDeviceModal').modal('hide');
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
