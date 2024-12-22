<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Mã</title>
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

        .table th, .table td {
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
    </style>
</head>
<body>

    <h1>Danh Sách Mã</h1>
   
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
  
  <!-- Button to Import Excel File -->
<button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#importModal">Nhập từ Excel</button>

<!-- Modal for Importing Excel -->
<div class="modal fade" id="importModal" tabindex="-1" aria-labelledby="importModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="importModalLabel">Nhập dữ liệu từ file Excel</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="importForm" method="post" action="import-excel" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="excelFile" class="form-label">Chọn file Excel</label>
                        <input type="file" class="form-control" id="excelFile" name="excelFile" accept=".xls,.xlsx" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Nhập</button>
                </form>
            </div>
        </div>
    </div>
</div>
  
    
  <div class="table-container">
    <table class="table table-striped">
        <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"></th>
                <th>STT</th>
                <th>Mã</th>
                <th>Tên</th>
                <th>Dây chuyền</th>
                <th>Hình ảnh</th>
                <th>Ngày tạo</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="code" items="${codes}" varStatus="status">
                <tr>
                    <td><input type="checkbox" name="selectCode" value="${code.id}"></td>
                    <td>${status.index + 1}</td>
                    <td>${code.code}</td>
                    <td>${code.name}</td>
                    <td>${code.lineName}</td>
                    <td class="image-column"><img src="${code.image}" alt="Image"></td>
                    <td><fmt:formatDate value="${code.createdDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                    <td>
                        <button class="btn btn-warning btn-custom" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editCode('${code.id}', '${code.code}', '${code.name}','${code.lineId}','${code.image}')">Sửa</button>
                        <button class="btn btn-danger btn-custom" onclick="deleteCode(${code.id})">Xoá</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
  </div>

   <!-- Kiểm tra nếu không có dữ liệu -->
   <c:if test="${empty codes}">
       <tr>
           <p style="text-align: center;" class="text-center">Không có dữ liệu</p>
       </tr>
   </c:if>

    <%@include file="components/sidebar.jsp"%>
    

    <!-- Modal for Add New Code -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addModalLabel">Thêm Mã Mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="addForm" method="post" action="list-code" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="add">
                        <div class="mb-3">
                            <label for="code" class="form-label">Mã</label>
                            <input type="text" class="form-control" id="code" name="code" required>
                        </div>
                        <div class="mb-3">
                            <label for="name" class="form-label">Tên</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="line" class="form-label">Dây chuyền</label>
                            <select class="form-control" id="line" name="lineId" required>
                                    <option value="#">--Chọn Dây chuyền--</option>
                                <c:forEach var="line" items="${lines}">
                                    <option value="${line.id}">${line.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="image" class="form-label">Hình ảnh</label>
                            <input type="file" class="form-control" id="image" name="image" accept="image/*" onchange="previewAddImage(event)">
                            <div class="mt-3">
                                <!-- Image Preview -->
                                <img id="imageAddPreview" src="" alt="Hình ảnh" style="max-width: 30%; height: auto; border: 1px solid #ddd; padding: 5px;">
                            </div>
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
            <form id="editForm" method="post" action="list-code" enctype="multipart/form-data">
              <input type="hidden" name="action" value="update">
              <input type="hidden" id="editId" name="id">
              <input type="hidden" id="existingImage" name="existingImage"> <!-- Hidden input to store current image -->

              <!-- Code Input -->
              <div class="mb-3">
                <label for="editCode" class="form-label">Mã</label>
                <input type="text" class="form-control" id="editCode" name="code" required>
              </div>

              <!-- Name Input -->
              <div class="mb-3">
                <label for="editName" class="form-label">Tên</label>
                <input type="text" class="form-control" id="editName" name="name" required>
              </div>

              <!-- Line Selection -->
              <div class="mb-3">
                <label for="editLine" class="form-label">Dây chuyền</label>
                <select class="form-control" id="editLine" name="lineId" required>
                  <c:forEach var="line" items="${lines}">
                    <option value="${line.id}">${line.name}</option>
                  </c:forEach>
                </select>
              </div>

              <!-- Image Input -->
              <div class="mb-3">
                <label for="editImage" class="form-label">Hình ảnh</label>
                <input type="file" class="form-control" id="editImage" name="image" accept="image/*" onchange="previewImage(event)">
                <div class="mt-3">
                  <!-- Image Preview -->
                  <img id="imagePreview" src="" alt="Hình ảnh" style="max-width: 30%; height: auto; border: 1px solid #ddd; padding: 5px;">
                </div>
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
        function editCode(id, code, name, lineId, image) {
            document.getElementById('editId').value = id;
            document.getElementById('editCode').value = code;
            document.getElementById('editName').value = name;
            document.getElementById('editLine').value = lineId;

            // Gán hình ảnh hiện tại vào phần preview
            const imagePreview = document.getElementById('imagePreview');
            imagePreview.src = image; // URL ảnh từ danh sách hiện tại

            // Gán giá trị hình ảnh hiện tại vào hidden input để gửi cho backend nếu không thay đổi ảnh
            document.getElementById('existingImage').value = image;
        }

        // Hàm preview hình ảnh khi chọn file mới
        function previewImage(event) {
            const file = event.target.files[0];
            const imagePreview = document.getElementById('imagePreview');

            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    imagePreview.src = e.target.result; // Hiển thị ảnh được chọn
                };
                reader.readAsDataURL(file);
            } else {
                imagePreview.src = "";
            }
        }

        
        // Hàm hiển thị hình ảnh khi add
        function previewAddImage(event) {
            const file = event.target.files[0];
            const imagePreview = document.getElementById('imageAddPreview');

            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    imagePreview.src = e.target.result; // Hiển thị ảnh được chọn
                };
                reader.readAsDataURL(file);
            } else {
                imagePreview.src = "";
            }
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
                        window.location.href = 'list-code?action=deleteSelected&ids=' + selectedIds.join(',');
                    }
                } else {
                    alert("Vui lòng chọn ít nhất một mã để xóa.");
                }
            });

            // Function to handle delete
            function deleteCode(id) {
                if (confirm("Bạn có chắc muốn xóa mã này không?")) {
                    window.location.href = 'list-code?action=delete&id=' + id;
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
