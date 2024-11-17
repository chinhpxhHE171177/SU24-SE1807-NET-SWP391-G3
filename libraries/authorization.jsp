<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.0/mdb.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.0/mdb.min.js"></script>
    <link
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
  rel="stylesheet"
/>
    <title>Phân Quyền Quản Trị</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
        }

        h2 {
            color: #333;
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
    </style>
</head>

<body>
    <h2>Phân quyền</h2>
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
     
 <!-- Modal -->
    <div class="modal fade" id="staticBackdrop1" tabindex="-1" aria-labelledby="staticBackdrop1Label" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdrop1Label">Thêm quyền mới</h5>
                    <button type="button" class="btn-close" data-mdb-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="roleForm" action="Authorization" method="POST">
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
                    <form id="roleForm" action="Authorization" method="POST">
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

    <!-- Bảng phân quyền -->
    <form id="permissionsForm" method="post" action="Authorization">
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
                    <td>${perm.pageName}</td>
                    <td><input type="checkbox" name="${perm.pageName}.canAccess" value="1" ${perm.canAccess ? 'checked' : ''}></td>
                    <td><input type="checkbox" name="${perm.pageName}.canAdd" value="1" ${perm.canAdd ? 'checked' : ''}></td>
                    <td><input type="checkbox" name="${perm.pageName}.canEdit" value="1" ${perm.canEdit ? 'checked' : ''}></td>
                    <td><input type="checkbox" name="${perm.pageName}.canDelete" value="1" ${perm.canDelete ? 'checked' : ''}></td>
                   </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Các nút thao tác -->
        <div class="action-buttons">
            <button type="button" class="btn-delete" onclick="deletePermissions()">Xóa</button>
            <button type="submit" class="btn-save">Lưu</button>
        </div>
    </form>

    <script>
        function loadPermissions() {
            const roleId = document.getElementById('roleSelect').value;
            // Redirect to the new URL with the selected role
            window.location.href = 'Authorization?role=' + roleId;
        }

        function deletePermissions() {
            if (confirm('Bạn có chắc chắn muốn xóa tất cả quyền truy cập?')) {
                const form = document.getElementById('permissionsForm');
                form.action = 'Authorization'; 
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
