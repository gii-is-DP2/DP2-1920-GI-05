
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory5Bottleneck extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.png""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9,es;q=0.8,es-ES;q=0.7")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

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
		.pause(6)
		}

		object Login {
			val login = exec(http("login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(16)
		}

		object Logged {
			val logged = exec(http("logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "94187ebc-0681-444d-911d-7650e5b8e989"))
		.pause(15)
		}

		object TournamentList {
			val tournamentList = exec(http("tournamentList")
			.get("/tournaments/all")
			.headers(headers_0))
		.pause(7)
		}

		object NewTournament {
			val newTournament = exec(http("newTournament")
			.get("/tournaments/new")
			.headers(headers_0))
		.pause(57)
		}

		object TournamentCreated {
			val tournamentCreated = exec(http("tournamentCreated")
			.post("/tournaments/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "nombre")
			.formParam("location", "Sevilla")
			.formParam("category", "Beauty")
			.formParam("petType", "Dog")
			.formParam("field", "Map 10")
			.formParam("judge", "Dacon")
			.formParam("applyDate", "2020/10/10")
			.formParam("startDate", "2020/11/11")
			.formParam("endDate", "2020/12/12")
			.formParam("prize.amount", "100")
			.formParam("prize.currency", "€")
			.formParam("_csrf", "2f8edbaf-29da-46c9-b859-70bcf5d07f9f"))
		.pause(23)
		}


	val scn1 = scenario("Admins").exec(Home.home,
									  Login.login,
									  Logged.logged,
									  TournamentList.tournamentList,
									  NewTournament.newTournament,
									  TournamentCreated.tournamentCreated)
	val scn2 = scenario("Owners").exec(Home.home,
									  Login.login,
									  Logged.logged,
									  TournamentList.tournamentList,
									  NewTournament.newTournament,
									  TournamentCreated.tournamentCreated)
		

	setUp(
		scn1.inject(rampUsers(20000) during (100 seconds)),
		scn2.inject(rampUsers(20000) during (100 seconds))
	).protocols(httpProtocol)
     
}