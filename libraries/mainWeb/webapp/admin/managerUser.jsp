<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý người dùng</title>
    <!-- Bootstrap CSS for styling -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include DataTables CSS and JS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <link href="../assets/css/manager-user.css" rel="stylesheet" media="screen">   

</head>
<body>

    <h1>Danh Sách Người Dùng</h1>
   
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
                <th>Mã</th>
                <th>Tên</th>
                <th>Giới tính</th>
                <th>Dây chuyền</th>
                <th>Vai trò</th>
                <th>Ngày tạo</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${listu}" varStatus="status">
                <tr>
                    <td><input type="checkbox" name="selectUser" value="${u.id}"></td>
                    <td>${status.index + 1}</td>
                    <td>${u.code}</td>
                    <td>${u.name}</td>
                    <td>${u.gender}</td>
                    <td>${u.lineName}</td>
                    <td>${u.roleName}</td>
                    <td><fmt:formatDate value="${u.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                    <td>
                        <button class="btn btn-warning btn-custom" data-bs-toggle="modal" data-bs-target="#editModal" onclick="editUser ('${u.id}', '${u.code}', '${u.name}', '${u.gender}', '${u.lineName}', '${u.lineIds}', '${u.roleId}')">Sửa</button>
                        <button class="btn btn-danger btn-custom" onclick="deleteUser(${u.id})">Xoá</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
  </div>

   <!-- Kiểm tra nếu không có dữ liệu -->
   <c:if test="${empty listu}">
       <tr>
           <p style="text-align: center;" class="text-center">Không có dữ liệu</p>
       </tr>
   </c:if>

   <%@ include file="../components/sidebar.jsp" %>
    
     <!-- Button to Add New Record -->
    <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">Thêm Mới</button>
    
    <!-- Modal for Add New Code -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addModalLabel">Thêm Người Dùng Mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="addForm" method="post" action="manager-user">
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
                            <label for="gender" class="form-label">Giới Tính</label>
                            <select class="form-control" id="gender" name="gender" required>
                                <option value="">--Chọn Giới Tính--</option>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                            </select>
                        </div>

                        <div class="mb-3 dropdown-container">
                            <label for="dropdownInput" class="form-label">Dây chuyền</label>
                            <input type="text" class="form-control" placeholder="--Chọn Dây Chuyền--" readonly id="dropdownInput" onclick="toggleAddDropdown()"/>

                            <div class="dropdown" id="addDropdown" style="display: none;">
                                <c:forEach var="item" items="${listl}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="add_line_${item.id}" value="${item.id}" onchange="updateAddLineDisplay()" />
                                        <label class="form-check-label" for="add_line_${item.id}">${item.name}</label>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="role" class="form-label">Vai trò</label>
                            <select class="form-control" id="role" name="roleId" required>
                                <option value="">--Chọn Vai Trò--</option>
                                <c:forEach var="r" items="${listr}">
                                    <option value="${r.id}">${r.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-primary">Thêm</button>
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
                    <h5 class="modal-title" id="editModalLabel">Cập nhật người dùng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editForm" method="post" action="manager-user">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" id="editId" name="id">

                        <div class="mb-3">
                            <label for="editCode" class="form-label">Mã</label>
                            <input type="text" class="form-control" id="editCode" name="code" required readonly="readonly">
                        </div>
                        
                        <div class="mb-3">
                            <label for="editName" class="form-label">Tên</label>
                            <input type="text" class="form-control" id="editName" name="name" required readonly="readonly">
                        </div>
                        
                        <div class="mb-3">
                            <label for="editGender" class="form-label">Giới Tính</label>
                            <select class="form-control" id="editGender" name="gender" required>
                                <option value="">--Chọn Giới Tính--</option>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="editLineDisplay" class="form-label">Dây chuyền</label>
                            <input type="text" class="form-control" id="editLineDisplay" readonly onclick="toggleEditLineCheckboxes()" style="cursor: pointer;">
                            <div id="editLine" class="form-control" style="height: auto; display: none;">
                                <c:forEach var="line" items="${listl}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="edit_line_${line.id}" name="lineIds" value="${line.id}" onchange="updateEditLineDisplay()">
                                        <label class="form-check-label" for="edit_line_${line.id}">${line.name}</label>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        
                        <div class="mb-3">
                            <label for="editRole" class="form-label">Vai trò</label>
                            <select class="form-control" id="editRole" name="roleId" required>
                                <option value="">--Chọn Vai Trò--</option>
                                <c:forEach var="r" items="${listr}">
                                    <option value="${r.id}">${r.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

  <script>
  // For Add Modal
  function toggleAddDropdown() {
      const dropdown = document.getElementById('addDropdown');
      dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
  }

  function updateAddLineDisplay() {
      const selectedLines = Array.from(document.querySelectorAll('#addDropdown .form-check-input:checked'))
          .map(input => input.nextElementSibling.textContent.trim());

      document.getElementById('dropdownInput').value = selectedLines.join(', ') || '--Chọn Dây Chuyền--';
  }

  // Close dropdown when clicking outside
  document.addEventListener('click', function(event) {
      const dropdown = document.getElementById('addDropdown');
      const dropdownInput = document.getElementById('dropdownInput');
      if (!event.target.closest('.dropdown-container')) {
          dropdown.style.display = 'none';
      }
  });

  // For Edit Modal
  function editUser(id, code, name, gender, lineName, lineIds, roleId) {
      document.getElementById('editId').value = id;
      document.getElementById('editCode').value = code;
      document.getElementById('editName').value = name;
      document.getElementById('editGender').value = gender;
      document.getElementById('editRole').value = roleId;

      // Update line checkboxes
      const selectedLineIds = lineIds.split(',');
      document.querySelectorAll('#editLine .form-check-input').forEach(input => {
          input.checked = selectedLineIds.includes(input.value);
      });
      document.getElementById('editLineDisplay').value = lineName;
  }

  function toggleEditLineCheckboxes() {
      const lineDiv = document.getElementById('editLine');
      lineDiv.style.display = lineDiv.style.display === 'none' ? 'block' : 'none';
  }

  function updateEditLineDisplay() {
      const selectedLines = Array.from(document.querySelectorAll('#editLine .form-check-input:checked'))
          .map(input => input.nextElementSibling.textContent.trim());
      document.getElementById('editLineDisplay').value = selectedLines.join(', ') || 'No line selected';
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
                $('input[name="selectUser"]:checked').each(function() {
                    selectedIds.push($(this).val());
                });
                if (selectedIds.length > 0) {
                    if (confirm("Bạn có chắc chắn muốn xóa các mã đã chọn không?")) {
                        // Redirect to delete action with selected IDs
                        window.location.href = 'manager-user?action=deleteSelected&ids=' + selectedIds.join(',');
                    }
                } else {
                    alert("Vui lòng chọn ít nhất một mã để xóa.");
                }
            });

            // Function to handle delete
            function deleteUser(id) {
                if (confirm("Bạn có chắc muốn xóa người dùng này không?")) {
                    window.location.href = 'manager-user?action=delete&id=' + id;
                }
            }
    </script>
    
    <script>
        // Select/Deselect all checkboxes
        $('#selectAll').on('click', function() {
            $('input[name="selectUser"]').prop('checked', this.checked);
            toggleDeleteButton(); // Check the state of the delete button
        });

        // Check/uncheck individual checkboxes and toggle delete button
        $('input[name="selectUser"]').on('change', function() {
            toggleDeleteButton();
        });

        // Function to toggle the delete button
        function toggleDeleteButton() {
            if ($('input[name="selectUser"]:checked').length > 0) {
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
