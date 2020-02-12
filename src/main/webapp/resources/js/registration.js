$(document).ready(function() {
    addEventHanglers();
});

function addEventHanglers() {
    $(".submit-registration").on('click', tryRegistration);
    $(".input-password").change(checkPasswordFieldForEmpty);
    $(".input-password").focus(hidePassword);
    $(".confirm-password").on("input keyup",passwordConfirmUpdated);
    $(".confirm-password").focus(hideConfirmPassword);
    checkPasswordFieldForEmpty();
    checkPasswordConfirmFieldForEmpty();
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

function checkPasswordFieldForEmpty() {
    var inputPass = $(".input-password");
    if (inputPass.val() == '') {
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
    console.log($(".confirm-password").is( ":focus" ));
    if (inputPass.val() == '' && !$(".confirm-password").is( ":focus" )) {
        inputPass.attr("type", "text");
        inputPass.val("Подтвердите пароль");
    } else
    {
        inputPass.attr("type", "password");
    }
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
        url: "console/dashboard/update-widget",
        contentType: 'application/json',
        data: JSON.stringify(newUser),
        success: function(data) {
            document.location.href = "#";
            requestWidgets();
        }
    });
}