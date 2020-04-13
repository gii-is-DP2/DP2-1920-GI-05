<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="find owners">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Find owners</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'vets'}" url="/vets"
					title="veterinarians">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Veterinarians</span>
				</petclinic:menuItem>
				
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'reports'}" url="/myReports/"
					title="My reports">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>My reports</span>
				</petclinic:menuItem>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><strong>Authenticated</strong> <span
							class="glyphicon glyphicon-chevron-down"></span> </a>
						<ul class="dropdown-menu">
							<li><a href="<c:url value="/tournaments/active" />">Active
									tournaments </a></li>
						</ul></li>
				</sec:authorize>

				<sec:authorize access="hasAuthority('admin')">
					<li class="dropdown"><a id="adminbar" href="#" class="dropdown-toggle"
						data-toggle="dropdown"><strong>Admin</strong> <span
							class="glyphicon glyphicon-chevron-down"></span> </a>
						<ul class="dropdown-menu">
							<li><a id="allTournaments" href="<c:url value="/tournaments/all" />">All tournaments</a></li>
							<li><a id="allCategories" href="<c:url value="/categories/all" />"> All categories</a></li>
							<li><a id="allFields" href="<c:url value="/fields/all" />"> All fields</a></li>
							<li><a id="allApplications" href="<c:url value="/applications/all" />"> All applications</a></li>
							<li><a id="newJudge" href="<c:url value="/judges/new" />">Register a Judge	</a></li>
						</ul></li>
				</sec:authorize>

				<sec:authorize access="hasAuthority('owner')">
					<li class="dropdown"><a id="ownerbar" href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <strong>Owner</strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li><a id="myapplist" href="<c:url value="/applications/list_mine" />">My applications
									</a></li>
						<li><a id="profile" href="<c:url value="/owners/details" />">My profile
									</a></li>		
						</ul></li>
				</sec:authorize>

				<sec:authorize access="hasAuthority('judge')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><strong>Judge</strong> <span
							class="glyphicon glyphicon-chevron-down"></span> </a>
						<ul class="dropdown-menu">
						<li><a href="<c:url value="/judges/details" />">My profile
									</a></li>		
						</ul></li>
				</sec:authorize>

				<sec:authorize access="hasAuthority('guide')">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><strong>Guide</strong> <span
							class="glyphicon glyphicon-chevron-down"></span> </a>
						<ul class="dropdown-menu">
						<li><a href="<c:url value="/guides/details" />">Edit my profile
									</a></li>		
						</ul></li>
				</sec:authorize>
			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a id="login" href="<c:url value="/login" />">Login</a></li>
					<li class="dropdown"><a id="signinbar" href="#" class="dropdown-toggle"
						data-toggle="dropdown"><strong>Sign in</strong> <span
							class="glyphicon glyphicon-chevron-down"></span> </a>
					<ul class="dropdown-menu">
							<li><a id="signowner" href="<c:url value="/users/new" />">As Owner
									</a></li>
						<li><a id="signguide" href="<c:url value="/guides/new" />">As Guide
									</a></li>		
						</ul>
					
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a id="logout" href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
