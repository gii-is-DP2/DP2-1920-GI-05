package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory7SimulationDiagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
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

	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(15)
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
	
	object Logged{
		val logged =exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "e6381408-2684-4daa-bc17-3aedba422f16"))
		.pause(17)
	}
	
	object ListTournament{
		val listTournament = exec(http("ListTournament")
			.get("/tournaments/all")
			.headers(headers_0))
		.pause(19)
	}
	
	object ShowTournament{
		val showTournament = exec(http("ShowTournament")
			.get("/tournaments/4/edit")
			.headers(headers_0))
		.pause(117)
	}
	
	object TournamentUpdated{
		val tournamentUpdated = exec(http("TournamentUpdated")
			.post("/tournaments/4/edit")
			.headers(headers_3)
			.formParam("id", "4")
			.formParam("name", "Dogs speed tournament 2020 ")
			.formParam("location", "York")
			.formParam("category", "Speed")
			.formParam("petType", "Dog")
			.formParam("judge", "Dredd")
			.formParam("applyDate", "2020/10/10")
			.formParam("startDate", "2020/11/01")
			.formParam("endDate", "2020/11/03")
			.formParam("prize.amount", "150.0")
			.formParam("prize.currency", "€")
			.formParam("_csrf", "502613d8-0c69-4a19-bd91-0ac009c85836"))
		.pause(38)
	}
	
	object TournamentNeg{
		val tournamentNeg = exec(http("TournamentNeg")
			.post("/tournaments/4/edit")
			.headers(headers_3)
			.formParam("id", "4")
			.formParam("name", "Dogs speed tournament 2020 ")
			.formParam("location", "York")
			.formParam("category", "Speed")
			.formParam("petType", "Dog")
			.formParam("judge", "Dredd")
			.formParam("applyDate", "2020/12/02")
			.formParam("startDate", "2020/11/01")
			.formParam("endDate", "2020/11/03")
			.formParam("prize.amount", "150.0")
			.formParam("prize.currency", "€")
			.formParam("_csrf", "502613d8-0c69-4a19-bd91-0ac009c85836"))
		.pause(16)
	}

	val pScn = scenario("Positives").exec(Home.home, Login.login, Logged.logged, ListTournament.listTournament, ShowTournament.showTournament,
										TournamentUpdated.tournamentUpdated)
										
	val nScn = scenario("Negatives").exec(Home.home, Login.login, Logged.logged, ListTournament.listTournament, ShowTournament.showTournament,
										TournamentNeg.tournamentNeg)

	setUp(
		pScn.inject(rampUsers(3000) during (100)),
		nScn.inject(rampUsers(3000) during (100))
		).protocols(httpProtocol)
		 .assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			global.successfulRequests.percent.gt(95),
		 )
}