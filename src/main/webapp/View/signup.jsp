<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" />
<link href="<c:url value="/css/mystyle.css" />" rel="stylesheet"
	type="text/css">
<title>${title }</title>
</head>
<body>
	<div id="content" class="p-4 p-md-5">

		<!-- Navbar -->

		<%@ include file="navbar.jsp"%>

		<!--Main Cotnent  -->
		<div class="container">
			<div class="row">
				<div class="offset-3 col-6 my-5" style="background-color: white;border:2px solid #f8b739">
					<form:form action="signup" method="post" modelAttribute="object" 
						class="mt-5 p-3" id="reg-form">

						<h2 class="text-center mb-3">Create an Account</h2>
						<div class="form-group mb-3">
							<div class="row">
								<div class="col">
									<form:input type="text" class="form-control" path='name.fname'
										placeholder="First name" />
								</div>
								<div class="col">
									<form:input type="text" class="form-control " path="name.lname"
										placeholder="Last name" />
								</div>

							</div>
						</div>

						<div class="form-group mb-3">
							<div class="row">
								<div class="col">
									<form:input type="email" class="form-control" path="email"
										placeholder="Email" />
								</div>
								<div class="col">
									<form:input type="password" class="form-control"
										path="password" placeholder="Password" />
								</div>
							</div>
						</div>

						<div class="d-grid gap-2">
							<button class="btn btn-primary form-input" type="submit">Register
								Account</button>
						</div>
					</form:form>
					<hr>
					<div class="form-group mt-0 mb-2 text-center ">
						<p class="font-weight-bold">
							Already have an Account? <a href="#" class="fornt-weight-bold ">Login</a>
						</p>

					</div>

				</div>
			</div>
		</div>


	</div>
	<!-- End of Main -->










	<script src="<c:url value="/js/jquery.min.js" />"></script>
	<script src="<c:url value="/js/popper.js" />"></script>
	<script src="<c:url value="/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/js/main.js" />"></script>


</body>
</html>