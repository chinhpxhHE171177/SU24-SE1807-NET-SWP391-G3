<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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

        .action-buttons button:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
        }

        .action-buttons button:active {
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
    <div class="form-group">
        <label for="roleSelect">Chọn quyền:</label>
        <select name="role" id="roleSelect" onchange="loadPermissions()">
            <c:forEach items="${listr}" var="r">
                <option value="${r.id}" ${r.id == role ? 'selected' : ''}>${r.name}</option>
            </c:forEach>
        </select>
    </div>

    <!-- Bảng phân quyền -->
    <form id="permissionsForm" method="post" action="Authorization">
        <input type="hidden" name="roleId" id="roleId" value="${role}">
        <table>
            <thead>
                <tr>
                    <th>Điều hành viên</th>
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
                        <td><input type="checkbox" name="${perm.pageName}.canAccess" value="true" ${perm.canAccess ? 'checked' : ''}></td>
                        <td><input type="checkbox" name="${perm.pageName}.canAdd" value="true" ${perm.canAdd ? 'checked' : ''}></td>
                        <td><input type="checkbox" name="${perm.pageName}.canEdit" value="true" ${perm.canEdit ? 'checked' : ''}></td>
                        <td><input type="checkbox" name="${perm.pageName}.canDelete" value="true" ${perm.canDelete ? 'checked' : ''}></td>
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
    </script>
</body>

</html>
