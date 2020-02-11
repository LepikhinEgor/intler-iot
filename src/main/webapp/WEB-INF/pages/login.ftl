<#ftl encoding="UTF-8"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Intler cloud</title>
    <link rel="stylesheet" href="./resources/css/login.css" media="screen" type="text/css" />
    <link rel="icon" href="http://vladmaxi.net/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="http://vladmaxi.net/favicon.ico" type="image/x-icon">
</head>
<body>
<div id="login">
    <form action="javascript:void(0);" method="get">
        <fieldset class="clearfix">
            <p><span class="fontawesome-user"></span><input class="login-field" type="text" value="Логин" onBlur="if(this.value == '') this.value = 'Логин'" onFocus="if(this.value == 'Логин') this.value = ''" required></p> <!-- JS because of IE support; better: placeholder="Username" -->
            <p><span class="fontawesome-lock"></span><input class="login-field" type="password"  value="Пароль" onBlur="if(this.value == '') this.value = 'Пароль'" onFocus="if(this.value == 'Пароль') this.value = ''" required></p> <!-- JS because of IE support; better: placeholder="Password" -->
            <p><input class="login-submit" type="submit" value="ВОЙТИ"></p>
        </fieldset>
    </form>
    <div>Нет аккаунта? &nbsp;&nbsp;<a href="registration">Регистрация</a></div>
</div>
</body>
</html>