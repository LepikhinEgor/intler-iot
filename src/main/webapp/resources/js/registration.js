$(document).ready(function() {
    addEventHanglers();
});

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

function checkEmailValid() {
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    var address =  $(".input-email").val();
    if(reg.test(address) == false) {
        highlightEmail("red");
    } else {
        highlightEmail("green");
    }
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

var loginBusyTimeout;
function checkLoginValid() {
    var regex = /^(?!.*\\.\\.)(?!\\.)(?!.*\\.$)(?!\\d+$)[a-zA-Z0-9_.]{5,50}$/;
    var login =  $(".input-login").val();
    if(regex.test(login) == false) {
        highlightLogin("red");
    } else {
        highlightLogin("green");
        loginBusyTimeout = setTimeout(requestCheckLoginBusy, 500);
    }
}

function checkPasswordValid() {
    var regex = /[0-9a-zA-Z!@#$%^&*]{6,}/;
    var password =  $(".input-password").val();
    if(regex.test(password) == false) {
        highlightPassword("red");
    } else {
        highlightPassword("green");
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

    console.log(inputPass.val() + " " + inputConfirmPass.val());
    if (inputPass.val() === inputConfirmPass.val()) {
        inputPass.css("border", "1px solid green");
        inputConfirmPass.css("border", "1px solid green");
    } else {
        highlightTimeout = setTimeout(highlightRedConfirm, 500);
    }


}

function highlightRedConfirm() {
    $(".confirm-password").css("border", "1px solid red");
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
        }
    });
}

function tryRegistration() {
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
            if (data === "true")
                highlightLogin("green");
            else
                highlightLogin("red");
        }
    });
}