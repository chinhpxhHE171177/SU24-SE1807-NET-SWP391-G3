<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    
</head>

<body>
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
     
    <%@ include file="components/sidebar.jsp" %>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow-lg">
                    <div class="card-header bg-primary text-white text-center">
                        <h3>Thay đổi mật khẩu</h3>
                    </div>
                    <div class="card-body">
                        <form id="changePasswordForm" action="change-password" method="post" onsubmit="return validateForm()">
                            <!-- Input Code -->
                            <div class="mb-3">
                                <label for="code" class="form-label">Mã nhân viên</label>
                                <input type="text" class="form-control" id="code" name="code"
                                    value="${sessionScope.user.code}" readonly="readonly">
                                <div class="text-danger" id="codeError"></div>
                            </div>
                            <!-- Input Name -->
                            <div class="mb-3">
                                <label for="name" class="form-label">Tên</label>
                                <input type="text" class="form-control" id="name" name="name"
                                    value="${sessionScope.user.name}" readonly="readonly">
                                <div class="text-danger" id="nameError"></div>
                            </div>
                            <!-- Input New Password -->
                            <div class="mb-3">
                                <label for="newPassword" class="form-label">Mật khẩu mới</label>
                                <input type="password" class="form-control" id="newPassword" name="newPassword"
                                    placeholder="Enter new password">
                                <div class="text-danger" id="newPasswordError"></div>
                            </div>
                            <!-- Input Re-enter New Password -->
                            <div class="mb-3">
                                <label for="reNewPassword" class="form-label">Nhập lại mật khẩu</label>
                                <input type="password" class="form-control" id="reNewPassword" name="reNewPassword"
                                    placeholder="Re-enter new password">
                                <div class="text-danger" id="reNewPasswordError"></div>
                            </div>
                            <!-- Hidden field for old password -->
                            <input type="hidden" id="oldPassword" name="oldPassword" value="${sessionScope.user.pasword}">
                            <!-- Submit Button -->
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">Change Password</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Success Modal -->
    <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="loginModalLabel">Mật khẩu đã được thay đổi thành công</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Bạn có muốn đăng nhập lại không?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="loginButton">Đăng nhập lại</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript Validation -->
    <script>
        function validateForm() {
            // Clear previous errors
            document.getElementById("newPasswordError").innerText = "";
            document.getElementById("reNewPasswordError").innerText = "";

            // Get form values
            const newPassword = document.getElementById("newPassword").value.trim();
            const reNewPassword = document.getElementById("reNewPassword").value.trim();
            const oldPassword = document.getElementById("oldPassword").value.trim();

            let isValid = true;

            // Validate new password
            const passwordRegex = /^(?=.*[!@#$%^&*])[^\s]{8,}$/; // At least 8 characters, 1 special character, no spaces
            if (!passwordRegex.test(newPassword)) {
                document.getElementById("newPasswordError").innerText = 
                    "Mật khẩu phải có ít nhất 8 ký tự, bao gồm 1 ký tự đặc biệt và không được chứa khoảng trắng.";
                isValid = false;
            }

            // Check if new password is different from old password
            if (newPassword === oldPassword) {
                document.getElementById("newPasswordError").innerText = "Mật khẩu mới phải khác với mật khẩu cũ.";
                isValid = false;
            }

            // Validate re-entered password
            if (newPassword !== reNewPassword) {
                document.getElementById("reNewPasswordError").innerText = "Mật khẩu nhập lại không khớp.";
                isValid = false;
            }

            return isValid;
        }
        
        // Show login modal if password change is successful
        document.addEventListener("DOMContentLoaded", function () {
            const message = "<%= request.getParameter("toastMessage") %>";
            const type = "<%= request.getParameter("type") %>"; // success or error

            if (type === "success" && message) {
                // Show success modal
                const loginModal = new bootstrap.Modal(document.getElementById('loginModal'));
                loginModal.show();

                // Handle login again button click
                document.getElementById("loginButton").addEventListener("click", function () {
                    window.location.href = "login"; 
                });
            }
        });
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

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
    
</body>

</html>
