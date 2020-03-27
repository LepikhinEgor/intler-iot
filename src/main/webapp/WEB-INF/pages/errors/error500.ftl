<#ftl encoding="UTF-8"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Unexpected Error</title>
    <link rel="stylesheet" href="./resources/css/errors/error500.css"/>
</head>

<body class="loading">
<div class="wrapper">
    <div class="box">
        <h1>500</h1>
        <p>Internal server error</p>
        <details>
            <summary>
                Stack trace
            </summary>
            <div class="stacktrace">
                <br>
                ${stacktrace}
            </div>
        </details>
        <p>&#58;&#40;</p>
        <p><a href="${url}">Let me try again!</a></p>
    </div>
</div>
</body>
</html>

