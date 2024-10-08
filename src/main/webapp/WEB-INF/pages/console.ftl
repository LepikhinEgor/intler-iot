<#ftl encoding="UTF-8"/>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Intler console</title>
    <link rel="icon" href="./resources/images/pageIcon.png" type="image/x-icon" />
    <link rel="shortcut icon" href="./resources/images/pageIcon.png" type="image/x-icon">
    <link rel="stylesheet" href="./resources/css/consoleStyle.css"/>
    <link rel="stylesheet" href="./resources/css/dashboard.css"/>
    <link rel="stylesheet" href="./resources/css/logic.css"/>
    <link rel="stylesheet" href="./resources/JQueryUI/jquery-ui.css" />
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300" type="text/css">
    <script src="./resources/js/jquery-3.4.1.js" type="text/javascript"></script>
    <script src="./resources/JQueryUI/jquery-ui.js" type="text/javascript"></script>
    <script src="./resources/js/dashboard.js" type="text/javascript"></script>
    <script src="./resources/js/console.js" type="text/javascript"></script>
    <script src="./resources/js/sensorsLogs.js" type="text/javascript"></script>
    <script src="./resources/js/logic.js" type="text/javascript"></script>
    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
<div id="wrapper">
    <aside>
        <p>
            <img src="./resources/images/logo.png" class="logo" alt="Our offices">
        </p>
        <nav>
            <ul class="aside-menu">
                <li id="stats-tab">Статистика</li>
                <li id="dashboard-tab" class="active">Панель приборов</li>
                <li id="db-tab">База данных</li>
                <li id="logic-tab">Управление</li>
            </ul>
        </nav>
    </aside>
    <section>
        <div class="top-panel">
            <div class="user-bar">
<#--                <div>-->
                    <span id="user-login">${username}</span>
<#--                </div>-->
                <div id="dd" class="wrapper-dropdown-5">
                    <img class="user-icon-img" src="./resources/images/user_icon.png">
                    <ul class="dropdown">
                        <li><a href="profile">Профиль</a></li>
<#--                        <li><a href="#">Настройки</a></li>-->
                        <li class="logout-button"><a href="#">Выйти</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="content-panel">
        </div>
    </section>
</div>

<#--<footer>-->
<#--    <div id="footer">-->
<#--        <div id="twitter">-->
<#--            <h3>TWITTER FEED</h3>-->
<#--            <time datetime="2012-10-23"><a href="#">23 oct</a></time>-->
<#--            <p>In ultricies pellentesque massa a porta. Aliquam ipsum enim, hendrerit ut porta nec, ullamcorper et nulla. In eget mi dui, sit amet scelerisque nunc. Aenean aug</p>-->
<#--        </div>-->
<#--        <div id="sitemap">-->
<#--            <h3>SITEMAP</h3>-->
<#--            <div>-->
<#--                <a href="/home/">Home</a>-->
<#--                <a href="/about/">About</a>-->
<#--                <a href="/services/">Services</a>-->
<#--            </div>-->
<#--            <div>-->
<#--                <a href="/partners/">Partners</a>-->
<#--                <a href="/customers/">Support</a>-->
<#--                <a href="/contact/">Contact</a>-->
<#--            </div>-->
<#--        </div>-->
<#--        <div id="social">-->
<#--            <h3>SOCIAL NETWORKS</h3>-->
<#--            <a href="http://twitter.com/" class="social-icon twitter"></a>-->
<#--            <a href="http://facebook.com/" class="social-icon facebook"></a>-->
<#--            <a href="http://plus.google.com/" class="social-icon google-plus"></a>-->
<#--            <a href="http://vimeo.com/" class="social-icon-small vimeo"></a>-->
<#--            <a href="http://youtube.com/" class="social-icon-small youtube"></a>-->
<#--            <a href="http://flickr.com/" class="social-icon-small flickr"></a>-->
<#--            <a href="http://instagram.com/" class="social-icon-small instagram"></a>-->
<#--            <a href="/rss/" class="social-icon-small rss"></a>-->
<#--        </div>-->
<#--        <div id="footer-logo">-->
<#--            <a href="/"><img src="images/footer-logo.png" alt="Whitesquare logo"></a>-->
<#--            <p>Copyright &copy; 2012 Whitesquare. A <a href="http://pcklab.com">pcklab</a> creation </p>-->
<#--        </div>-->
<#--    </div>-->
<#--</footer>-->
</body>
</html>