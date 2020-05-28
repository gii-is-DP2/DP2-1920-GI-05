
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory22Bottleneck extends Simulation {

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
			val home = exec(http("home")
			.get("/")
			.headers(headers_0))
		.pause(8)
		}

		object Login {
			val login = exec(http("login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(14)
		}

		object Logged {
			val logged = exec(http("logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "1d4beacc-448e-4cbc-a8b1-b5a4eafeed12"))
		.pause(32)
		}

		object RankingList {
			val rankingList = exec(http("rankingsList")
			.get("/rankings/all")
			.headers(headers_0))
		.pause(21)
		}

		object RankingChosen {
			val rankingChosen = exec(http("rankingChosen")
			.get("/rankings/1/show")
			.headers(headers_0))
		.pause(21)
		}



	val scn1 = scenario("Owners").exec(Home.home,
									  Login.login,
									  Logged.logged,
									  RankingList.rankingList,
									  RankingChosen.rankingChosen)
	val scn2 = scenario("Guides").exec(Home.home,
									  Login.login,
									  Logged.logged,
									  RankingList.rankingList,
									  RankingChosen.rankingChosen)
		

	setUp(
		scn1.inject(rampUsers(20000) during (100 seconds)),
		scn2.inject(rampUsers(20000) during (100 seconds))
	).protocols(httpProtocol)
     
}