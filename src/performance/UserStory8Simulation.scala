package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory8Simulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.png""", """.*.js""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8,pl;q=0.7")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("UserStory8Simulation")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(20)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(13)
		// Login
		.exec(http("Logged1")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "e96521fa-84d4-47ab-bedb-3979dd74e484"))
		.pause(24)
		// Logged1
		.exec(http("ListTournaments")
			.get("/tournaments/all")
			.headers(headers_0))
		.pause(26)
		// ListTournaments
		
		.exec(http("logged2")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "aff8b6a1-f293-4241-b1aa-84ab21ca577a"))
		.pause(14)
		// logged2

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}