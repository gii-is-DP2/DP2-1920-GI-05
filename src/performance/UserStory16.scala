package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory16 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,en-GB;q=0.7",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,en-GB;q=0.7",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,en-GB;q=0.7",
		"Proxy-Connection" -> "keep-alive")

	val headers_6 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,en-GB;q=0.7",
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
			.resources(http("request_4")
			.get("/login")
			.headers(headers_1),
            http("request_5")
			.get("/login")
			.headers(headers_5)))
		.pause(10)
	}

	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_6)
			.formParam("username", "judge1")
			.formParam("password", "jugd3")
			.formParam("_csrf", "62a69165-dd29-412c-aea3-009eb3d1e794")
			.resources(http("request_7")
			.get("/")
			.headers(headers_1)))
		.pause(13)
	}

	object MyProfile {
		val myProfile = exec(http("MyProfile")
			.get("/judges/details")
			.headers(headers_0)
			.resources(http("request_9")
			.get("/judges/details")
			.headers(headers_1)))
		.pause(15)
	}

	object ReportList {
		val reportList = exec(http("ReportList")
			.get("/judges/1/reports")
			.headers(headers_0)
			.resources(http("request_11")
			.get("/judges/1/reports")
			.headers(headers_1)))
		.pause(16)
	}

	val okscn = scenario("ListReportOk").exec(Home.home, Login.login, 
	Logged.logged, MyProfile.myProfile, ReportList.reportList)

	val notOkscn = scenario("NoListReport").exec(Home.home, 
	Login.login, Logged.logged, MyProfile.myProfile)

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