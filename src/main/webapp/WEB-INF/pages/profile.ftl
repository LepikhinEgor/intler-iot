<#ftl encoding="UTF-8"/>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>User profile</title>
    <link rel="icon" href="./resources/images/pageIcon.png" type="image/x-icon" />
    <link rel="shortcut icon" href="./resources/images/pageIcon.png" type="image/x-icon">
    <link rel="stylesheet" href="./resources/css/consoleStyle.css"/>
    <link rel="stylesheet" href="./resources/css/profile.css"/>
    <link rel="stylesheet" href="./resources/JQueryUI/jquery-ui.css" />
    <script src="./resources/js/jquery-3.4.1.js" type="text/javascript"></script>
    <script src="./resources/JQueryUI/jquery-ui.js" type="text/javascript"></script>
    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>

<body>
<div class="top-panel">
    <div class="top_panel_name"><h1>Profile</h1></div>
</div>
<div class="content-panel">
    <div class="content-panel-page">
        <h2>
           Account
        </h2>
        <p><span class="prop_key">Login:</span><span class="prop_value">${login}</span></p>
        <p><span class="prop_key">Email:</span><span class="prop_value">${email}</span></p>
        <p><span class="prop_key">Server IP:</span><span class="prop_value">${serverIP}</span></p>
        <div class="to_console_wrap" onclick="document.location.href='console'">
            <a href="console" class="to_console_button">To console</a>
        </div>
    </div>
</div>
</body>
</html>