<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="<c:url value="/css/mystyle.css" />" rel="stylesheet"
	type="text/css">
<title>${title }</title>
</head>
<body>

	<!-- side bar -->
	<div class="wrapper d-flex align-items-stretch">
		<nav id="sidebar">
			<div class="p-4 pt-5">
				<a href="#" class="img logo rounded-circle mb-2"
					style="background-image: url(<c:url value="/images/logo.jpg" />)"></a>
 					<p class="text-center">${object.name.fname }</p>
				<ul class="list-unstyled components mb-5">
					
					<li><a href="#">Home</a></li>
					<li><a href="#">About</a></li>
					<li><a href="#">Portfolio</a></li>
					<li><a href="#">Contact</a></li>
				</ul>

				<div class="footer">
					<p>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;
						<script>
							document.write(new Date().getFullYear());
						</script>
						All rights reserved | This template is made with <i
							class="icon-heart" aria-hidden="true"></i> by <a
							href="https://colorlib.com" target="_blank">Colorlib.com</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</p>
				</div>

			</div>
		</nav>

		<!-- Page Content  -->
		<div id="content" class="p-4 p-md-5">

			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container-fluid">

					<button type="button" id="sidebarCollapse" class="btn btn-primary">
						<i class="fa fa-bars"></i> <span class="sr-only">Toggle
							Menu</span>
					</button>
					<button class="btn btn-dark d-inline-block d-lg-none ml-auto"
						type="button" data-toggle="collapse"
						data-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false"
						aria-label="Toggle navigation">
						<i class="fa fa-bars"></i>
					</button>

					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="nav navbar-nav ml-auto">
							<li class="nav-item active"><a class="nav-link" href="#">Home</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="#">About</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="#">Portfolio</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="#">Contact</a>
							</li>
						</ul>
					</div>
				</div>
			</nav>
			<h2 class="mb-4">Admin Deshbored</h2>
			<div class="container">
				<div class="row">
					<div class="col">
						<h3>${object}</h3>
						<h3>${object.name.fname}</h3>
						<h3>${object.email}</h3>
						<h3>${object.password}</h3>
					</div>
				</div>
			</div>

		</div>
	</div>
	<!--endoofbar-->










	<script src="<c:url value="/js/jquery.min.js" />"></script>
	<script src="<c:url value="/js/popper.js" />"></script>
	<script src="<c:url value="/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/js/main.js" />"></script>


</body>
</html>