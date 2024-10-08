$(document).ready(function() {
    addEventHanglers();
});

var loginIsOk = false;
var emailIsOk = false;
var passwordIsOk = false;
var confirmIsOk = false;

function addEventHanglers() {
    $(".submit-registration").on('click', tryRegistration);

    $(".input-login").on("input keyup",loginUpdate);
    $(".input-password").change(passwordUpdate);
    $(".input-password").focus(hidePassword);
    $(".confirm-password").on("input keyup",passwordConfirmUpdated);
    $(".confirm-password").focus(hideConfirmPassword);
    $(".input-email").change(checkEmailValid)
    checkPasswordFieldForEmpty();
    checkPasswordConfirmFieldForEmpty();
}

var emailBusyTimeout;

function checkEmailValid() {
    clearTimeout(emailBusyTimeout);
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var address =  $(".input-email").val();
    if(reg.test(address) == false) {
        highlightEmail("red");
        emailAlertWrite("Некорректный E-mail");
        emailIsOk = false;
    } else {
        // highlightEmail("green");
        emailAlertWrite("");
        emailBusyTimeout = setTimeout(requestCheckEmailBusy, 200);
    }
}

function requestCheckEmailBusy() {
    var login = $(".input-email").val();
    $.ajax({
        type: "GET",
        url: "registration/check-email-is-free?email=" + login,
        contentType: 'application/json',
        success: function(data) {
            if (data == "true") {
                highlightEmail("green");
                emailIsOk = true;
            }
            else {
                highlightEmail("red");
                emailAlertWrite("Email занят");
                emailIsOk = false;
            }
        }
    });
}

function highlightLogin(color) {
    $(".input-login").css("border", "1px solid " + color);
}
function highlightEmail(color) {
    $(".input-email").css("border", "1px solid " + color);
}
function highlightPassword(color) {
    $(".input-password").css("border", "1px solid " + color);
}

var loginHighlightTimeout;
function loginUpdate() {
    clearTimeout(loginHighlightTimeout);
    clearTimeout(loginBusyTimeout);

    loginHighlightTimeout = setTimeout(checkLoginValid, 500);
}

function loginAlertWrite(message) {
    $(".login-alert").html(message);
}
function emailAlertWrite(message) {
    $(".email-alert").html(message);
}
function passwordAlertWrite(message) {
    $(".password-alert").html(message);
}
function passwordConfirmAlertWrite(message) {
    $(".confirm-password-alert").html(message);
}

var loginBusyTimeout;
function checkLoginValid() {
    var regex = /^(?!.*\\.\\.)(?!\\.)(?!.*\\.$)(?!\\d+$)[a-zA-Z0-9_.]{5,50}$/;
    var login =  $(".input-login").val();
    if(regex.test(login) == false) {
        highlightLogin("red");
        loginIsOk = false;
        if (login.length < 5)
            loginAlertWrite("Минимум 5 символов");
        else
            loginAlertWrite("Недопустимые символы");
    } else {
        $(".login-alert").html("");
        loginBusyTimeout = setTimeout(requestCheckLoginBusy, 200);
    }
}

function checkPasswordValid() {
    var regex = /[0-9a-zA-Z!@#$%^&*]{6,}/;
    var password =  $(".input-password").val();
    if(regex.test(password) == false || password.includes(" ")) {
        highlightPassword("red");
        passwordIsOk = false;
        if (password.length < 6)
            passwordAlertWrite("Не менее 6 символов");
        else
            passwordAlertWrite("Недопустимые символы");
    } else {
        highlightPassword("green");
        passwordAlertWrite("");
        passwordIsOk = true;
    }
}

function hidePassword() {
    $(".input-password").attr("type", "password");
    if(this.value == 'Пароль')
        $(this).val('');
}

function hideConfirmPassword() {
    $(".confirm-password").attr("type", "password");
    if(this.value == 'Подтвердите пароль')
        $(this).val('');
}

function passwordConfirmUpdated() {
    checkPasswordConfirmFieldForEmpty();
    checkPasswordEquals();
}

function passwordUpdate() {
    checkPasswordValid();
    checkPasswordFieldForEmpty();
}

function checkPasswordFieldForEmpty() {
    var inputPass = $(".input-password");
    if (inputPass.val() == '' && !$(".confirm-password").is( ":focus" )) {
        inputPass.attr("type", "text");
        inputPass.val("Пароль");
    } else
    {
        inputPass.attr("type", "password");
    }
}

var highlightTimeout;

function checkPasswordEquals() {
    clearTimeout(highlightTimeout);
    var inputPass = $(".input-password");
    var inputConfirmPass = $(".confirm-password");

    if (inputPass.val() === inputConfirmPass.val()) {
        inputPass.css("border", "1px solid green");
        inputConfirmPass.css("border", "1px solid green");
        passwordConfirmAlertWrite("");
        confirmIsOk = true;
    } else {
        highlightTimeout = setTimeout(highlightRedConfirm, 500);
        confirmIsOk = false;
    }
}

function highlightRedConfirm() {
    $(".confirm-password").css("border", "1px solid red");
    passwordConfirmAlertWrite("Пароли не совпадают");
}

function checkPasswordConfirmFieldForEmpty() {
    var inputPass = $(".confirm-password");
    if (inputPass.val() == '' && !$(".confirm-password").is( ":focus" )) {
        inputPass.attr("type", "text");
        inputPass.val("Подтвердите пароль");
    } else
    {
        inputPass.attr("type", "password");
    }
}

function requestCheckLoginBusy(login) {
    var login = $(".input-login").val();
    $.ajax({
        type: "GET",
        url: "registration/check-login-is-free?login=" + login,
        contentType: 'application/json',
        success: function(data) {
            console.log(data);
            if (data == "true") {
                highlightLogin("green");
                loginIsOk = true;
            }
            else {
                highlightLogin("red");
                loginAlertWrite("Логин занят");
                loginIsOk = false;
            }
        }
    });
}

function tryRegistration() {
    if (!(loginIsOk && emailIsOk && passwordIsOk && confirmIsOk))
        return;

    var login = $(".input-login").val();
    var email = $(".input-email").val();
    var password = $(".input-password").val();
    var confirmPassword = $(".confirm-password").val();
    console.log("submit");

    if (password === confirmPassword) {
        var newUser = {
            login:login,
            email:email,
            password:password
        }
        requestRegistration(newUser);
    } else {
        $(".confirm-password").css("border", "1px solid red");
        passwordConfirmAlertWrite("Пароли не совпадают")
    }
    $(".submit-registration").focus();
}

function requestRegistration(newUser) {
    $.ajax({
        type: "POST",
        url: "registration/register-user",
        contentType: 'application/json',
        data: JSON.stringify(newUser),
        success: function(data) {
            console.log(data);
            if (data["status"] == 0)
                loginAfterRegistration();
        }
    });
}

function loginAfterRegistration() {
    var login = $(".input-login").val();
    var password = $(".input-password").val();

    $.ajax({
        type: "POST",
        url: "login-url?username=" + login + "&password="+ password,
        contentType: 'application/json',
        success: function(data) {
            document.location.href = "console";
        }
    });
}