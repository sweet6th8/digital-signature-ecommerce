function validateEmail() {
    const input = document.getElementById("email").value;
    const errorMessage = document.getElementById("error-email");
    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    // Kiểm tra xem đầu vào có hợp lệ không
    if (regex.test(input)) {
        errorMessage.style.display = "none"; // Ẩn thông báo lỗi nếu hợp lệ
    } else {
        errorMessage.style.display = "block"; // Hiện thông báo lỗi nếu không hợp lệ
    }
}
function validatePassword() {
    const password = document.getElementById("password").value;
    const passwordError = document.getElementById("password-error");
    const regex =
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

    // Kiểm tra xem mật khẩu có hợp lệ không
    if (regex.test(password)) {
        passwordError.style.display = "none"; // Ẩn thông báo lỗi nếu hợp lệ
    } else {
        passwordError.style.display = "block"; // Hiện thông báo lỗi nếu không hợp lệ
    }
}
function validatePhone() {
    const phone = document.getElementById("phone").value;
    const phoneError = document.getElementById("error-phone");
    const regex = /^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-5]|9[0-9])\d{7}$/;

    // Kiểm tra xem số điện thoại có hợp lệ không
    if (regex.test(phone)) {
        phoneError.style.display = "none"; // Ẩn thông báo lỗi nếu hợp lệ
    } else {
        phoneError.style.display = "block"; // Hiện thông báo lỗi nếu không hợp lệ
        phoneError.textContent = "Invalid phone number. Please re-enter.";
    }
}

function validateCheckPassword() {
    const password = document.getElementById("password").value;
    const checkPassword = document.getElementById("Checkpassword").value;
    const checkPasswordError = document.getElementById(
        "Checkpassword-error"
    );

    // Kiểm tra xem mật khẩu có khớp không
    if (password === checkPassword) {
        checkPasswordError.style.display = "none"; // Ẩn thông báo lỗi nếu mật khẩu khớp
    } else {
        checkPasswordError.style.display = "block"; // Hiện thông báo lỗi nếu mật khẩu không khớp
    }
}