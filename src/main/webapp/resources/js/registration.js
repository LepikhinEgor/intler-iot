$(document).ready(function() {
    addEventHanglers();
});

function addEventHanglers() {
    $(".submit-registration").on('click', tryRegistration);
    $(".input-password").change(checkPasswordFieldForEmpty);
    $(".input-password").focus(hidePassword);
    $(".confirm-password").change(checkPasswordConfirmFieldForEmpty);
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
function checkPasswordConfirmFieldForEmpty() {
    var inputPass = $(".confirm-password");
    if (inputPass.val() == '') {
        inputPass.attr("type", "text");
        inputPass.val("Подтвердите пароль");
    } else
    {
        inputPass.attr("type", "password");
    }
}

function tryRegistration() {
    console.log("submit");
    $(".submit-registration").focus();
}