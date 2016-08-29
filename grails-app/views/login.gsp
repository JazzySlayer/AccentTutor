<%--
  Created by IntelliJ IDEA.
  User: anijor
  Date: 8/26/2016
  Time: 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Accent Tutor</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
</head>

<body>
<div class="jumbotron head">
    <h1 class="text-center">Accent Tutor</h1>
</div>
<div class="container-fluid">

    <div class="login-page">
        <div class="form">
            <g:form class="login-form">
                <input type="text" placeholder="username"/>
                <input type="password" placeholder="password"/>
                <button>login</button>

            </g:form>
        </div>
    </div>

</div>



</body>
</html>