package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory18 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8,en-GB;q=0.7")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Purpose" -> "prefetch",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_6 = Map(
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
			.headers(headers_1)
			.resources(http("request_4")
			.get("/login")
			.headers(headers_2),
            http("request_5")
			.get("/login")
			.headers(headers_5)))
		.pause(20)
	}

	object Logged {
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_6)
			.formParam("username", "judge1")
			.formParam("password", "jugd3")
			.formParam("_csrf", "3266f751-82c5-4675-9c9a-5b7b4552e270")
			.resources(http("request_7")
			.get("/")
			.headers(headers_2)))
		.pause(34)
	}

	object JudgeProfile {
		val judgeProfile = exec(http("JudgeProfile")
			.get("/judges/details")
			.headers(headers_1)
			.resources(http("request_13")
			.get("/judges/details")
			.headers(headers_2)))
		.pause(27)
	}

	object MyTournamentsList {
		val myTournamentsList = exec(http("MyTournamentsList")
			.get("/judges/1/tournaments")
			.headers(headers_1)
			.resources(http("request_15")
			.get("/judges/1/tournaments")
			.headers(headers_2)))
		.pause(14)
	}

	object NewReportForm {
		val newReportForm = exec(http("NewReportForm")
			.get("/judges/1/reports/1/new")
			.headers(headers_1)
			.resources(http("request_17")
			.get("/judges/1/reports/1/new")
			.headers(headers_2)))
		.pause(19)
	}

	object ReportDone {
		val reportDone = exec(http("ReportDone")
			.post("/judges/1/reports/1/new")
			.headers(headers_6)
			.formParam("id", "")
			.formParam("id", "")
			.formParam("id", "")
			.formParam("points", "70")
			.formParam("comments", "Chinchon")
			.formParam("pet", "Levert")
			.formParam("_csrf", "ae3029ba-13c6-448a-a9cf-b0d2cae8e85f")
			.resources(http("request_19")
			.get("/judges/1/reports")
			.headers(headers_2)))
		.pause(15)
	}

	val okscn = scenario("ReportDoneOk").exec(Home.home, Login.login, Logged.logged, JudgeProfile.judgeProfile, 
	MyTournamentsList.myTournamentsList, NewReportForm.newReportForm, ReportDone.reportDone)

	val notOkscn = scenario("NoReportsToDo").exec(Home.home, Login.login, Logged.logged, 
	JudgeProfile.judgeProfile, MyTournamentsList.myTournamentsList)
		
	setUp(
		okscn.inject(rampUsers(3700) during (250 seconds)),
		notOkscn.inject(rampUsers(3700) during (250 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(1000),
			 global.successfulRequests.percent.gt(95)
		)  
}