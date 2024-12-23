<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <!-- Bootstrap CSS for styling -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.0/mdb.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.0/mdb.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <title>Phân Quyền Quản Trị</title>
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

        .form-group {
            margin-bottom: 15px;
        }

        select {
            padding: 10px;
            width: 200px;
            font-size: 16px;
            border-radius: 6px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f0f0f0;
            font-weight: bold;
        }

        th:not(:first-child) {
            text-align: center;
        }

        input[type="checkbox"] {
            margin: 0 auto;
            display: block;
        }

        .action-buttons {
            margin-top: 20px;
        }

        .action-buttons button {
            padding: 12px 30px;
            margin: 0 10px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
            transition: all 0.3s ease;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }
        
        .btn-new-role {
            padding: 6px 15px;
            background-color: #4caf50;
            margin: 0 10px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
            transition: all 0.3s ease;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }
        

        .btn-actions button {
            padding: 12px 30px;
            margin: 0 10px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
            transition: all 0.3s ease;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        .btn-delete {
            background-color: #ff4d4d;
            color: white;
        }

        .btn-copy {
            background-color: #4caf50;
            color: white;
        }

        .btn-save {
            background-color: #007bff;
            color: white;
        }
        
        .btn-add-role {
            background-color: #4caf50;
            color: white;
        }

        .btn-add-permission {
            background-color: #007bff;
            color: white;
        }

        .action-buttons button:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
        }

        .action-buttons button:active {
            transform: translateY(0);
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        
         .action-buttons button:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
        }

        .action-buttons button:active {
            transform: translateY(0);
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        
         .btn-actions button:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
        }

        .btn-actions button:active {
            transform: translateY(0);
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .btn-delete:hover {
            background-color: #e60000;
        }

        .btn-copy:hover {
            background-color: #388e3c;
        }

        .btn-save:hover {
            background-color: #0056b3;
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
    <%@include file="../components/sidebar.jsp"%>
    
    <h1>Phân quyền</h1>
    
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
    <!-- Dropdown chọn quyền -->
    <div style="display: flex; justify-content: space-between;">
       <div class="form-group">
           <label for="roleSelect">Chọn quyền:</label>
           <select name="role" id="roleSelect" onchange="loadPermissions()">
               <c:forEach items="${listr}" var="r">
                   <option value="${r.id}" ${r.id == role ? 'selected' : ''}>${r.name}</option>
               </c:forEach>
           </select>
       </div>
    
        <div class="btn-actions">
            <button type="button" class="btn-add-role" data-mdb-toggle="modal" data-mdb-target="#staticBackdrop1">Role</button>
            <button type="button" class="btn-add-permission" data-mdb-toggle="modal" data-mdb-target="#staticBackdrop2">Permission</button>
        </div>
     </div>
     
 <!-- Modal -->
    <div class="modal fade" id="staticBackdrop1" tabindex="-1" aria-labelledby="staticBackdrop1Label" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdrop1Label">Thêm quyền mới</h5>
                    <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="roleForm" action="authorization" method="post">
                      <input type="hidden" name="action" value="role">
                        <!-- Tên quyền -->
                        <div class="form-outline mb-4">
                            <input type="text" id="roleName" name="roleName" class="form-control" required />
                            <label class="form-label" for="roleName">Tên quyền</label>
                        </div>

                        <!-- Mô tả quyền -->
                        <div class="form-outline mb-4">
                            <textarea id="roleDescription" name="roleDescription" class="form-control" rows="3" required></textarea>
                            <label class="form-label" for="roleDescription">Mô tả quyền</label>
                        </div>

                        <!-- Submit button -->
                        <button type="submit" class="btn btn-primary btn-block">Lưu</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal -->
    
    
     <!-- Modal -->
    <div class="modal fade" id="staticBackdrop2" tabindex="-1" aria-labelledby="staticBackdrop1Label" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" style="max-width: 600px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdrop1Label">Thêm mới trang</h5>
                    <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="roleForm" action="authorization" method="post">
                      <input type="hidden" name="action" value="permission">
                        <!-- Tên quyền -->
                        <div class="form-outline mb-4">
                            <input type="text" id="pageName" name="pageName" class="form-control" required />
                            <label class="form-label" for="pageName">Tên trang</label>
                        </div>
                        
                        <div style="display: flex; justify-content: space-between; margin: 20px 0; padding: 10px;">
                            <div class="form-check" style="margin-right: 10px;">
                                <input type="checkbox" id="canAccess" name="canAccess" class="form-check-input" value="true">
                                <label for="canAccess" class="form-check-label" style="margin-left: 5px;">Can Access</label>
                            </div>
                            <div class="form-check" style="margin-right: 10px;">
                                <input type="checkbox" id="canAdd" name="canAdd" class="form-check-input" value="true">
                                <label for="canAdd" class="form-check-label" style="margin-left: 5px;">Can Add</label>
                            </div>
                            <div class="form-check" style="margin-right: 10px;">
                                <input type="checkbox" id="canEdit" name="canEdit" class="form-check-input" value="true">
                                <label for="canEdit" class="form-check-label" style="margin-left: 5px;">Can Edit</label>
                            </div>
                            <div class="form-check" style="margin-right: 10px;">
                                <input type="checkbox" id="canDelete" name="canDelete" class="form-check-input" value="true">
                                <label for="canDelete" class="form-check-label" style="margin-left: 5px;">Can Delete</label>
                            </div>
                        </div>

                        <!-- Submit button -->
                        <button type="submit" class="btn btn-primary btn-block">Lưu</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal -->

          <!-- Modal cho việc chỉnh sửa -->
     <div class="modal fade" id="staticBackdrop3" tabindex="-1" aria-labelledby="staticBackdrop1Label" aria-hidden="true">
         <div class="modal-dialog modal-dialog-centered" style="max-width: 600px;">
             <div class="modal-content">
                 <div class="modal-header">
                     <h5 class="modal-title" id="staticBackdrop1Label">Chỉnh sửa tên trang</h5>
                     <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                 </div>
                 <div class="modal-body">
                     <form id="editForm" action="authorization" method="post">
                         <input type="hidden" name="action" value="editPermission">
                         <input type="hidden" name="permissionIds" id="permissionIds"> <!-- Chứa nhiều permission ID -->
                         <div class="form-outline mb-4">
                             <input type="text" id="editPageName" name="pageName" class="form-control" required />
                             <label class="form-label" for="editPageName">Tên trang</label>
                         </div>
                         <button type="submit" class="btn btn-primary btn-block">Cập nhật</button>
                     </form>
                 </div>
             </div>
         </div>
     </div>


    <!-- Bảng phân quyền -->
    <form id="permissionsForm" method="post" action="authorization">
    <input type="hidden" name="action" value="deleteAndSave">
    <input type="hidden" name="roleId" id="roleId" value="${role}">
        <table>
            <thead>
                <tr>
                    <th>Điều hành viên <button type="button" class="btn-new-role" data-mdb-toggle="modal" data-mdb-target="#staticBackdrop2"><i class="fas fa-plus"></i></button></th>
                    <th>Truy cập</th>
                    <th>Thêm</th>
                    <th>Sửa</th>
                    <th>Xóa</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${permissions}" var="perm">
                    <tr>
                        <td>
                            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#staticBackdrop3" onclick="editPageName('${perm.pageName}', ${perm.id})">🖋️</button>
                            <button type="button" class="btn" onclick="deletePageName(${perm.id}, '${perm.pageName}')">🗑️</button>
                            ${perm.pageName}
                        </td>
                        <td><input type="checkbox" name="${perm.pageName}.canAccess" value="1" ${perm.canAccess ? 'checked' : ''}></td>
                        <td><input type="checkbox" name="${perm.pageName}.canAdd" value="1" ${perm.canAdd ? 'checked' : ''}></td>
                        <td><input type="checkbox" name="${perm.pageName}.canEdit" value="1" ${perm.canEdit ? 'checked' : ''}></td>
                        <td><input type="checkbox" name="${perm.pageName}.canDelete" value="1" ${perm.canDelete ? 'checked' : ''}></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="action-buttons">
            <button type="button" class="btn-delete" onclick="deletePermissions()">Xóa</button>
            <button type="submit" class="btn-save">Lưu</button>
        </div>
    </form>

    <script>
        function loadPermissions() {
            const roleId = document.getElementById('roleSelect').value;
            // Redirect to the new URL with the selected role
            window.location.href = 'authorization?role=' + roleId;
        }

        function deletePermissions() {
            if (confirm('Bạn có chắc chắn muốn xóa tất cả quyền truy cập?')) {
                const form = document.getElementById('permissionsForm');
                form.action = 'authorization'; 
                form.method = 'POST'; 
                const roleId = document.getElementById('roleId').value;
                form.innerHTML += `<input type="hidden" name="delete" value="true">`;
                form.innerHTML += `<input type="hidden" name="roleId" value="${roleId}">`; 
                form.submit();
            }
        }
        
        document.querySelectorAll('.form-outline').forEach((formOutline) => {
            new mdb.Input(formOutline).init();
        });
        

        function deletePageName(permId, pageName) {
            if (confirm('Bạn có chắc chắn muốn xóa tất cả permissions cho trang: ' + pageName + '?')) {
                // Gọi một servlet để xóa tất cả permissions liên quan
                fetch('authorization', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'action=deletePagePermissions&pageName=' + encodeURIComponent(pageName)
                })
                .then(response => {
                    if (response.ok) {
                        window.location.reload(); // Tải lại trang sau khi xóa thành công
                    } else {
                        alert('Có lỗi xảy ra khi xóa page permissions.');
                    }
                });
            }
        }

        
        function editPageName(pageName, permIds) {
            event.preventDefault();
            document.getElementById('editPageName').value = pageName;
            document.getElementById('permissionIds').value = permIds.join(','); // Giả định permIds là mảng
            var editModal = new bootstrap.Modal(document.getElementById('staticBackdrop3'));
            editModal.show();
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
