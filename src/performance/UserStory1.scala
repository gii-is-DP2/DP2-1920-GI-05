package testDp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory1 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.png""", """.*.ico""", """.*:"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-GB,en;q=0.9,es-ES;q=0.8,es;q=0.7,de-DE;q=0.6,de;q=0.5,ko-KR;q=0.4,ko;q=0.3,en-US;q=0.2")
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

    object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(8)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(2)
		.exec(http("request_3")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "34d1d3f9-f2fb-46c2-96e4-35d38013a461"))
		.pause(5)
	}	

	object CreateTournament {
		val createTournament = exec(http("CreateTournament")
            .get("/tournaments/all")
			.headers(headers_0))
		.pause(1)
		    .exec(http("request_5")
			.get("/tournaments/new")
			.headers(headers_0))
		.pause(15)
		    .exec(http("request_6")
			.post("/tournaments/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "Test")
			.formParam("location", "Sevilla")
			.formParam("category", "Beauty")
			.formParam("petType", "Bird")
			.formParam("field", "Map 1")
			.formParam("judge", "Dacon")
			.formParam("applyDate", "2020/09/09")
			.formParam("startDate", "2021/01/01")
			.formParam("endDate", "2022/01/01")
			.formParam("prize.amount", "500.00")
			.formParam("prize.currency", "EUR")
			.formParam("_csrf", "f6e3d972-2077-4dce-8202-bc7aad1fbfee"))
		.pause(8)
	}  

	val scn1 = scenario("AdminCreateTournament").exec(Home.home,Login.login,CreateTournament.createTournament)
    val scn2 = scenario("AdminCreateTournamentNegative").exec(Home.home,Login.login,CreateTournament.createTournament)

setUp(
		scn1.inject(rampUsers(1400) during (100 seconds)), 
		scn2.inject(rampUsers(1400) during (100 seconds))
		).protocols(httpProtocol).assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
	
}