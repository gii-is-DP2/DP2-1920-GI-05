package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory13 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8,en-GB;q=0.7")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/")
			.headers(headers_1)))
		.pause(9)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_3")
			.get("/login")
			.headers(headers_1),
            http("request_4")
			.get("/login")
			.headers(headers_4)))
		.pause(11)
	}

	object LoggedOwner {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "a0027317-dcd6-4d6d-9dc5-8a4213042fb3")
			.resources(http("request_6")
			.get("/")
			.headers(headers_1)))
		.pause(11)
	}

	object LoggedAdmin {
		val loggedAdmin = exec(http("LoggedAdmin")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "242999a7-cc43-4f6d-b123-5d3d37396537")
			.resources(http("request_17")
			.get("/")
			.headers(headers_1)))
		.pause(9)
	}

	object ApplicationList {
		val applicationList = exec(http("ApplicationList")
			.get("/applications/list_mine")
			.headers(headers_0)
			.resources(http("request_8")
			.get("/applications/list_mine")
			.headers(headers_1)))
		.pause(11)
	}



	val okscn = scenario("OwnerList").exec(Home.home, Login.login, LoggedOwner.logged, ApplicationList.applicationList)

	val notOkscn = scenario("Admin403").exec(Home.home, Login.login, LoggedAdmin.loggedAdmin, ApplicationList.applicationList)
		
	setUp(
		okscn.inject(rampUsers(4800) during (100 seconds)),
		notOkscn.inject(rampUsers(4800) during (100 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			 global.successfulRequests.percent.gt(95)
		) 
}