<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý vai trò & phân quyền</title>
    <!-- Bootstrap CSS for styling -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include DataTables CSS and JS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        
     <style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #f4f6f9;
    margin: 20px;
}

h1 {
    text-align: center;
    margin-bottom: 20px;
    font-size: 2rem;
    color: #343a40;
}

.table-container {
    margin-top: 20px;
}

.table {
    border-radius: 10px;
    margin-top: 5px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.table th,
.table td {
    text-align: center;
    vertical-align: middle;
    padding: 5px;
}

.table thead {
    background-color: #007bff;
    color: white;
}

.table tbody tr:hover {
    background-color: #f1f1f1;
}

.image-column img {
    width: 50px;
    height: 50px;
    border-radius: 8px;
}

.btn-custom {
    margin: 5px;
}

.modal-header {
    background-color: #007bff;
    color: white;
}

.modal-footer .btn {
    border-radius: 5px;
}

.btn-add {
    margin-bottom: 15px;
    margin-left: 20px;
}

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: cursive;
    background-color: #a1c4fd;
}

/* Sidebar styles */
.sidebar {
    position: fixed;
    top: 0;
    left: -250px;
    /* Sidebar hidden by default */
    width: 250px;
    height: 100%;
    background-color: #333;
    color: white;
    transition: all 0.3s ease;
    overflow-y: auto;
}

.sidebar.open {
    left: 0;
    /* Sidebar visible when open */
}

.sidebar .header {
    padding: 15px;
    font-size: 26px;
    font-weight: bold;
    text-align: center;
    border-bottom: 1px solid #444;
}

.sidebar ul {
    flex: 1;
    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;
    flex-direction: column;
}

.sidebar ul li {
    padding: 10px 20px;
    display: flex;
    align-items: center;
    border-bottom: 1px solid #444;
    cursor: pointer;
    transition: background 0.3s;
}

.sidebar ul li:hover {
    background-color: #555;
}

.sidebar ul li i {
    margin-right: 10px;
}

.sidebar ul li .arrow {
    margin-left: auto;
    transition: transform 0.3s;
}

.sidebar ul li.collapsed .arrow {
    transform: rotate(180deg);
}

/* Login button styles */
.sidebar ul .login {
    margin-top: auto;
}

.sidebar ul li.login {
    text-align: center;
    background-color: #444;
    padding: 12px 0;
    border-radius: 5px;
}

.sidebar ul li.login:hover {
    background-color: #555;
}

.sidebar ul li.login a {
    color: white;
    text-decoration: none;
    font-size: 18px;
    font-weight: bold;
}

/* Hamburger button */
.hamburger {
    position: fixed;
    top: 15px;
    left: 15px;
    font-size: 24px;
    cursor: pointer;
    z-index: 1000;
}

.hamburger:hover {
    color: #555;
}

/* Styles for the Welcome and Logout sections */
.sidebar .user-info {
    margin-top: auto;
    text-align: center;
    padding-top: 30px;
    color: white;
}

.sidebar .user-info h5 {
    font-size: 18px;
    margin-bottom: 10px;
}

.sidebar .user-info a {
    color: #f39c12;
    font-size: 16px;
    text-decoration: none;
}

.sidebar .user-info a:hover {
    color: #e67e22;
}

/* Styles for the Welcome and Logout sections */
.sidebar .user-info {
    margin-top: auto;
    text-align: center;
    padding-top: 30px;
    color: white;
}

.sidebar .user-info h5 {
    font-size: 18px;
    margin-bottom: 10px;
}

.sidebar .user-info a {
    color: #f39c12;
    font-size: 16px;
    text-decoration: none;
}

.sidebar .user-info a:hover {
    color: #e67e22;
}

.admin-list {
    display: none;
    background: #B0C4DE;
}

.dropdown-container {
    position: relative;
    width: 100%;
}

.dropdown-input {
    width: 100%;
    padding: 6px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
    cursor: pointer;
}

.dropdown {
    position: absolute;
    top: 100%;
    left: 0;
    width: 100%;
    max-height: 200px;
    overflow-y: auto;
    background: #fff;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    display: none;
    z-index: 100;
}

.dropdown.open {
    display: block;
}

.dropdown-item {
    padding: 10px;
    cursor: pointer;
    display: flex;
    align-items: center;
}

.dropdown-item:hover {
    background: #f0f0f0;
}

.dropdown-item input {
    margin-right: 10px;
}
    </style>
</head>
<body>

    <h1>Danh Sách Vai trò</h1>
   
    <!-- Button to Add New Record -->
    <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">Thêm Mới</button>
    
    <!-- Toast Container -->
    <div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1050;">
        <div id="toastMessage" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header">
                <strong class="me-auto">Thông báo</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                <!-- Nội dung thông báo sẽ được hiển thị -->
            </div>
        </div>
    </div>
      
  <button id="deleteSelected" class="btn btn-danger" disabled="disabled"><i class="fa-solid fa-trash"></i> Xóa</button>
    
  <div class="table-container">
    <table class="table table-striped">
        <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"></th>
                <th>STT</th>
                <th>Vai trò</th>
                <th>Mô tả</th>
                <th>Ngày tạo</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="r" items="${listr}" varStatus="status">
                <tr>
                    <td><input type="checkbox" name="selectCode" value="${r.id}"></td>
                    <td>${status.index + 1}</td>
                    <td>${r.name}</td>
                    <td>${r.description}</td>
                    <td><fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                    <td>
                        <button class="btn btn-warning btn-custom" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editCode('${r.id}', '${r.name}', '${r.description}')">Sửa</button>
                        <button class="btn btn-danger btn-custom" onclick="deleteCode(${r.id})">Xoá</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
  </div>

   <!-- Kiểm tra nếu không có dữ liệu -->
   <c:if test="${empty listr}">
       <tr>
           <p style="text-align: center;" class="text-center">Không có dữ liệu</p>
       </tr>
   </c:if>

    <%@include file="../components/sidebar.jsp"%>
    
 
    <!-- Modal for Add New Code -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addModalLabel">Thêm Vai Trò Mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="addForm" method="post" action="group-role">
                        <input type="hidden" name="action" value="add">
                        <div class="mb-3">
                            <label for="name" class="form-label">Vai trò</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                         <div class="mb-3">
                            <label for="description" class="form-label">Mô tả</label>
                            <input type="text" class="form-control" id="description" name="description" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Lưu</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for Edit Code -->
    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="editModalLabel">Sửa Mã</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form id="editForm" method="post" action="group-role">
              <input type="hidden" name="action" value="update">
              <input type="hidden" id="editId" name="id">

              <!-- Name Input -->
              <div class="mb-3">
                <label for="editName" class="form-label">Tên</label>
                <input type="text" class="form-control" id="editName" name="name" required>
              </div>
              
               <div class="mb-3">
                <label for="editDescription" class="form-label">Mô tả</label>
                <input type="text" class="form-control" id="editDescription" name="description" required>
              </div>

              <!-- Submit Button -->
              <button type="submit" class="btn btn-primary">Cập nhật</button>
            </form>
          </div>
        </div>
      </div>
    </div>


    <script>
       // Hàm mở modal và gán giá trị ban đầu
        function editCode(id, name, description) {
            document.getElementById('editId').value = id;
            document.getElementById('editName').value = name;
            document.getElementById('editDescription').value = description;
        }

        $(document).ready(function() {
            // Initialize DataTable
            var table = $('.table').DataTable({
                "paging": true,
                "searching": true,
                "lengthChange": true,
                "pageLength": 10, // Default number of entries
                "language": {
                    "lengthMenu": "Hiển thị _MENU_ mục",
                    "search": "Tìm kiếm:",
                    "info": "Hiển thị _START_ đến _END_ trong tổng số _TOTAL_ mục",
                    "infoEmpty": "Không có dữ liệu",
                    "paginate": {
                        "first": "Đầu",
                        "last": "Cuối",
                        "next": "Tiếp",
                        "previous": "Trước"
                    }
                }
            });
        });
         // Handle delete selected codes
            $('#deleteSelected').on('click', function() {
                var selectedIds = [];
                $('input[name="selectCode"]:checked').each(function() {
                    selectedIds.push($(this).val());
                });
                if (selectedIds.length > 0) {
                    if (confirm("Bạn có chắc chắn muốn xóa các mã đã chọn không?")) {
                        // Redirect to delete action with selected IDs
                        window.location.href = 'group-role?action=deleteSelected&ids=' + selectedIds.join(',');
                    }
                } else {
                    alert("Vui lòng chọn ít nhất một mã để xóa.");
                }
            });

            // Function to handle delete
            function deleteCode(id) {
                if (confirm("Bạn có chắc muốn xóa mã này không?")) {
                    window.location.href = 'group-role?action=delete&id=' + id;
                }
            }
    </script>
    
    <script>
        // Select/Deselect all checkboxes
        $('#selectAll').on('click', function() {
            $('input[name="selectCode"]').prop('checked', this.checked);
            toggleDeleteButton(); // Check the state of the delete button
        });

        // Check/uncheck individual checkboxes and toggle delete button
        $('input[name="selectCode"]').on('change', function() {
            toggleDeleteButton();
        });

        // Function to toggle the delete button
        function toggleDeleteButton() {
            if ($('input[name="selectCode"]:checked').length > 0) {
                $('#deleteSelected').prop('disabled', false); // Enable button
            } else {
                $('#deleteSelected').prop('disabled', true); // Disable button
            }
        }
    </script>
    
    <%-- Lấy thông báo từ query string --%>
   <c:if test="${not empty param.toastMessage}">
    <script>
       document.addEventListener("DOMContentLoaded", function () {
           // Lấy thông tin từ query string
           const message = "<%= request.getParameter("toastMessage") %>";
           const type = "<%= request.getParameter("type") %>"; // success hoặc error

           // Cập nhật nội dung và màu sắc của Toast
           const toastMessage = document.querySelector("#toastMessage");
           toastMessage.querySelector(".toast-body").textContent = message;

           if (type === "success") {
               toastMessage.classList.add("bg-success", "text-white"); // Thêm màu xanh
           } else if (type === "error") {
               toastMessage.classList.add("bg-danger", "text-white"); // Thêm màu đỏ
           }

           // Hiển thị Toast
           const toast = new bootstrap.Toast(toastMessage, { delay: 5000 }); // Hiển thị 30 giây
           console.log('Toast show with delay:', 5000); // Check delay
           toast.show();
       });
    </script>
  </c:if>
    
    <!-- Bootstrap JS for modals -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
