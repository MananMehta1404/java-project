<!DOCTYPE html>
<html>
<head>
    <title>Sunbase Portal Integration</title>
</head>
<body>
    <h1>Login</h1>
    <h2>${errorMessage}</h2>
    <form id="loginForm" method="post">
        <label for="login_id">Username:</label>
        <input type="text" id="login_id" name="login_id" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <br>
        <input type="submit" value="Login">
    </form>
</body> 
</html>
