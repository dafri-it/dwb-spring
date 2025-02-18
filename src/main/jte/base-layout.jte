@param gg.jte.Content content

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Start</title>
    <script src="https://unpkg.com/htmx.org@1.9.6"></script>
    <style>
        .container {
            display: flex;
            @media screen and (min-width: 1000px) {
                width: 1000px;
                margin: auto;
            }
        }
        aside {
            display: none;
            @media screen and (min-width: 1000px) {
                display: block;
                width: 400px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    ${content}
</div>
</body>
</html>