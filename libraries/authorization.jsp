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
            <!--<option value="admin">Quản trị viên</option>
            <option value="editor">Biên tập viên</option>
            <option value="user">Người dùng</option>-->
            <c:forEach items="${listr}" var="r">
               <option value="${r.id}" ${r.id = role ? selected : ''}>${r.name}</option>
            </c:forEach>
        </select>
    </div>

    <!-- Bảng phân quyền -->
    <table>
        <thead>
            <tr>
                <th>Điều hành viên</th>
                <th>Cho phép</th>
                <th>Sửa</th>
                <th>Thêm</th>
                <th>Xóa</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Được phép truy cập vào trang quản trị</td>
                <td><input type="checkbox" id="accessAdmin"></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>Quản lý thiết bị</td>
                <td><input type="checkbox" id="manageDevices"></td>
                <td><input type="checkbox" id="editDevices"></td>
                <td><input type="checkbox" id="addDevices"></td>
                <td><input type="checkbox" id="deleteDevices"></td>
            </tr>
            <tr>
                <td>Quản lý thông tin dừng ngắn</td>
                <td><input type="checkbox" id="manageShortInfo"></td>
                <td><input type="checkbox" id="editShortInfo"></td>
                <td><input type="checkbox" id="addShortInfo"></td>
                <td><input type="checkbox" id="deleteShortInfo"></td>
            </tr>
            <tr></tr>
                <td>Quản lý chi tiết thông tin dừng ngắn</td>
                <td><input type="checkbox"></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>Trang hiển thị top thông tin dừng ngắn</td>
                <td><input type="checkbox"></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>Quản lý người dùng</td>
                <td><input type="checkbox"></td>
                <td><input type="checkbox"></td>
                <td><input type="checkbox"></td>
                <td><input type="checkbox"></td>
            </tr>
            <tr>
                <td>Quản lý vai trò người dùng</td>
                <td><input type="checkbox"></td>
                <td><input type="checkbox"></td>
                <td><input type="checkbox"></td>
                <td><input type="checkbox"></td>
            </tr>
            <tr>
                <td>Quản lý vai trò</td>
                <td><input type="checkbox"></td>
                <td><input type="checkbox"></td>
                <td><input type="checkbox"></td>
                <td><input type="checkbox"></td>
            </tr>
        </tbody>
    </table>

    <!-- Các nút thao tác -->
    <div class="action-buttons">
        <button class="btn-delete" onclick="resetPermissions()">Xóa</button>
        <button class="btn-copy" onclick="copyPermissions()">Sao chép</button>
        <button class="btn-save" onclick="savePermissions()">Lưu</button>
    </div>

    <script>
        // Lưu các quyền hiện tại
        const permissions = {
            1: {
                accessAdmin: true,
                manageDevices: true,
                editDevices: true,
                addDevices: true,
                deleteDevices: true,
                manageShortInfo: true,
                editShortInfo: true,
                addShortInfo: true,
                deleteShortInfo: true
            },
            2: {
                accessAdmin: true,
                manageDevices: false,
                editDevices: false,
                addDevices: false,
                deleteDevices: false,
                manageShortInfo: false,
                editShortInfo: false,
                addShortInfo: false,
                deleteShortInfo: false
            }
        };

        // Hàm nạp quyền dựa vào lựa chọn
        function loadPermissions() {
            const role = document.getElementById('roleSelect').value;
            const rolePermissions = permissions[role];

            document.getElementById('accessAdmin').checked = rolePermissions.accessAdmin;
            document.getElementById('manageDevices').checked = rolePermissions.manageDevices;
            document.getElementById('editDevices').checked = rolePermissions.editDevices;
            document.getElementById('addDevices').checked = rolePermissions.addDevices;
            document.getElementById('deleteDevices').checked = rolePermissions.deleteDevices;
            document.getElementById('manageShortInfo').checked = rolePermissions.manageShortInfo;
            document.getElementById('editShortInfo').checked = rolePermissions.editShortInfo;
            document.getElementById('addShortInfo').checked = rolePermissions.addShortInfo;
            document.getElementById('deleteShortInfo').checked = rolePermissions.deleteShortInfo;
        }

        // Hàm lưu quyền đã chọn
        function savePermissions() {
            const role = document.getElementById('roleSelect').value;

            permissions[role].accessAdmin = document.getElementById('accessAdmin').checked;
            permissions[role].manageDevices = document.getElementById('manageDevices').checked;
            permissions[role].editDevices = document.getElementById('editDevices').checked;
            permissions[role].addDevices = document.getElementById('addDevices').checked;
            permissions[role].deleteDevices = document.getElementById('deleteDevices').checked;
            permissions[role].manageShortInfo = document.getElementById('manageShortInfo').checked;
            permissions[role].editShortInfo = document.getElementById('editShortInfo').checked;
            permissions[role].addShortInfo = document.getElementById('addShortInfo').checked;
            permissions[role].deleteShortInfo = document.getElementById('deleteShortInfo').checked;

            alert('Quyền đã được lưu!');
        }

        // Hàm sao chép quyền
        function copyPermissions() {
            alert('Quyền đã được sao chép!');
        }

        // Hàm xóa quyền
        function resetPermissions() {
            document.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
                checkbox.checked = false;
            });
            alert('Đã xóa quyền!');
        }

        // Nạp quyền mặc định cho vai trò khi load trang
        window.onload = loadPermissions;
    </script>
</body>
</html>
