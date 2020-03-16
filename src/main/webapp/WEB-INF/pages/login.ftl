<#ftl encoding="UTF-8"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход Intler cloud</title>
    <link rel="stylesheet" href="./resources/css/login.css" media="screen" type="text/css" />
    <link rel="icon" href="./resources/images/pageIcon.png" type="image/x-icon" />
<#--    <link rel="icon" href="http://vladmaxi.net/favicon.ico" type="image/x-icon">-->
    <link rel="shortcut icon" href="./resources/images/pageIcon.png"  type="image/x-icon">
    <script src="./resources/js/jquery-3.4.1.js" type="text/javascript"></script>
    <script src="./resources/js/login.js" type="text/javascript"></script>
</head>
<body>
<div id="login">
    <form class=".login-form" action="login-url" method='POST'>
        <fieldset class="clearfix">
            <#if RequestParameters.error??><h4 class="err_message">Неправильный логин или пароль</h4> </#if>
            <p><span class="fontawesome-user"></span><input name = "username"class="login-field" type="text" value="Логин" onBlur="if(this.value == '') this.value = 'Логин'" onFocus="if(this.value == 'Логин') this.value = ''" required></p> <!-- JS because of IE support; better: placeholder="Username" -->
            <p><span class="fontawesome-lock"></span><input name = "password" class="pass-field" type="password" value="" onBlur="if(this.value == '') { this.type = 'text'; this.value = 'Пароль'}" onFocus="if(this.value == 'Пароль') {this.value = ''; this.type = 'password'}" required></p> <!-- JS because of IE support; better: placeholder="Password" -->
            <p><input class="login-submit" type="submit" value="ВОЙТИ"></p>
        </fieldset>
    </form>
    <div>Нет аккаунта? &nbsp;&nbsp;<a href="registration">Регистрация</a></div>
</div>
<script>
    $(".pass-field").blur();
</script>
</body>
</html>