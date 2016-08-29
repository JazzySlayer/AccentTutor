<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>
		Accent Tutor
	</title>
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
						<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>

								%{--<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>--}%
								<input type='text' class='text_' name='j_username' id='username' placeholder="Username"/>



								%{--<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>--}%
								<input type='password' class='text_' name='j_password' id='password' placeholder="Password"/>


							%{--<p id="remember_me_holder">--}%
								%{--<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>--}%
								%{--<label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>--}%
							%{--</p>--}%


								<input type='submit' id="button"  value='${message(code: "springSecurity.login.button")}'/>

						</form>

					</div>
				</div>
			</div>




		<script type='text/javascript'>
		(function() {
			document.forms['loginForm'].elements['j_username'].focus();
		})();
		</script>
</body>
</html>
