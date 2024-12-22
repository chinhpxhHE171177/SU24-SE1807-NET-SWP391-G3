<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Glint - Creative Agency</title>
    <link href="assets/css/menuSideBar.css" rel="stylesheet" media="screen">
    <!-- Include Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <div class="container">
        <!-- Hamburger button -->
        <div class="hamburger" onclick="toggleSidebar()">☰</div>

        <!-- Sidebar -->
        <div class="sidebar" id="sidebar">
            <div class="header">SideMenu</div>
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/home" style="color: white; text-decoration: none;"><i>🏠</i> Home</a>
                </li>
                <li onclick="toggleSection(this)">
                    <i>🔄</i> Quản trị viên <span class="arrow">▼</span>
                </li>
                <!-- Collapsible list for Admin -->
                <ul class="admin-list" style="display: none"> 
                    <li><a href="${pageContext.request.contextPath}/list-code" style="color: white; text-decoration: none; padding: 2px 25px;"><i>⚙️</i>Danh sách mã</a></a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/manager-user" style="color: white; text-decoration: none; padding: 2px 25px;"><i>👨🏽‍💼</i>Quản lý user</a></a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/group-role" style="color: white; text-decoration: none; padding: 2px 25px;">📈 Vai trò</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/authorization" style="color: white; text-decoration: none; padding: 2px 25px;">👤 Phân quyền</a></li>
                </ul>
                <li>
                    <a href="${pageContext.request.contextPath}/change-password" style="color: white; text-decoration: none;"><i>✏️</i> Thay đổi mật khẩu</a>
                </li>
                <li>
                    <i>📧</i> Contact us
                </li>
                 <!-- Login/Logout Section -->
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <div class="user-info">
                            <h5>Welcome ${sessionScope.user.code}!</h5>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/logout"> <i class="fas fa-sign-out-alt"></i> Logout</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <li style="margin-top: auto; text-align: center;">
                            <a href="${pageContext.request.contextPath}/login" style="color: white; text-decoration: none;">🔒 Login</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>

    <script>
        // Toggle sidebar visibility
        function toggleSidebar() {
            const sidebar = document.getElementById('sidebar');
            sidebar.classList.toggle('open');
        }
        
     // Toggle collapsible sections
        function toggleSection(item) {
            const adminList = item.nextElementSibling; // Get the next sibling (the admin list)
            adminList.style.display = adminList.style.display === "none" ? "block" : "none"; // Toggle display
            item.classList.toggle('collapsed'); // Optional: Add a class for styling
        }
    </script>
     <!-- Bootstrap JS for modals -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
