package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory20SimulationDiagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.png""", """.*.js""", """.*.ico"""), WhiteList())
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,pl;q=0.7",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,pl;q=0.7",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8,pl;q=0.7",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

    val uri1 = "http://clientservices.googleapis.com/chrome-variations/seed"
	
	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(20)
	}
	
	object Login{
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(16)
	}
	
	object Logged1{
		val logged1 = exec(http("Logged1")
			.post("/login")
			.headers(headers_4)
			.formParam("username", "guide1")
			.formParam("password", "gu1d3")
			.formParam("_csrf", "7bd9e8eb-8cee-4faa-8807-ab60e166ec65"))
		.pause(38)
	}
	
	object ShowGuide{
		val showGuide = exec(http("ShowGuide")
			.get("/guides/details")
			.headers(headers_0))
		.pause(33)
	}
	
	object PetsAssigned{
		val petsAssigned = exec(http("PetsAssigned")
			.get("/guides/1/pets")
			.headers(headers_0))
		.pause(22)
	}
	
	object Logged2{
		val logged2 = exec(http("Logged2")
			.post("/login")
			.headers(headers_4)
			.formParam("username", "judge1")
			.formParam("password", "jugd3")
			.formParam("_csrf", "eb99a2e4-3b32-4e7d-b6bd-2c131a4c3bd5"))
		.pause(10)
	}
	
	val pScn = scenario("Guides").exec(Home.home, Login.login, Logged1.logged1, ShowGuide.showGuide, PetsAssigned.petsAssigned)
										
	val nScn = scenario("Judges").exec(Home.home, Login.login, Logged2.logged2, ShowGuide.showGuide, PetsAssigned.petsAssigned)

	setUp(
		pScn.inject(rampUsers(4000) during (100)),
		nScn.inject(rampUsers(4000) during (100))
		).protocols(httpProtocol)
		 .assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(95),
		 )
}