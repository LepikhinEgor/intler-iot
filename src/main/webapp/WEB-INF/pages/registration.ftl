<#ftl encoding="UTF-8"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>22 лучших формы входа и регистрации | Vladmaxi.net</title>
    <link rel="stylesheet" href="./resources/css/registration.css" media="screen" type="text/css" />
    <link rel="icon" href="http://vladmaxi.net/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="http://vladmaxi.net/favicon.ico" type="image/x-icon">
    <script src="./resources/js/jquery-3.4.1.js" type="text/javascript"></script>
    <script src="./resources/js/registration.js" type="text/javascript"></script>
</head>
<body>
<div id="login">
    <form action="javascript:void(0);" method="get">
        <fieldset class="clearfix">
            <p><input type="text" value="Логин" onBlur="if(this.value == '') this.value = 'Логин'" onFocus="if(this.value == 'Логин') this.value = ''" required></p> <!-- JS because of IE support; better: placeholder="Username" -->
            <p><input type="text" value="E-mail" onBlur="if(this.value == '') this.value = 'E-mail'" onFocus="if(this.value == 'E-mail') this.value = ''" required></p> <!-- JS because of IE support; better: placeholder="Password" -->
            <p><input class="input-password" type="password"  value=""  required></p>
            <p><input class="confirm-password" type="password"  value="" required></p>
            <p><input class="submit-registration" type="submit" value="регистрация"></p>
        </fieldset>
    </form>
    <div>Уже есть аккаунт? &nbsp;&nbsp;<a href="login">Войти</a></div>
</div>
</body>
</html>