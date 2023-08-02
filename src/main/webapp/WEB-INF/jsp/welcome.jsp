<html>
    <head>
    <title>Sunbase Portal Integration</title>
    </head>

    <body>
        <h1>Welcome ${user} to this site</h1>
        <form method="post">
            <a href="localhost:8080/customers"><input type="submit" value="Create New Customer" /></a>
        </form>
        <form method="post">
            <a href="localhost:8080/getcustomers"><input type="submit" value="View Customer List" /></a>
        </form>
    </body>
</html>