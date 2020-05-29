package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ListReportsGuide extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.png""", """.*.ico""", """.*.js"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-GB,en;q=0.9,es-ES;q=0.8,es;q=0.7,de-DE;q=0.6,de;q=0.5,ko-KR;q=0.4,ko;q=0.3,en-US;q=0.2")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
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
			.headers(headers_1)))
		.pause(2)
		.exec(http("request_3")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "guide1")
			.formParam("password", "gu1d3")
			.formParam("_csrf", "0719f983-b871-420e-a157-370ae826bb8d"))
		.pause(5)
	}
object ListReportsGuide {
	val listReportsGuide = exec(http("listReportsGuide")
			.get("/guides/details")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_4")
			.get("/guides/1/reports")
			.headers(headers_0))
}

	val scn1 = scenario("GuideListReports").exec(Home.home,Login.login,ListReportsGuide.listReportsGuide)
    val scn2 = scenario("JudgeListReportsNegative").exec(Home.home,Login.login,ListReportsGuide.listReportsGuide)

setUp(
		scn1.inject(rampUsers(9000) during (100 seconds)), 
		scn2.inject(rampUsers(9000) during (100 seconds))
		).protocols(httpProtocol).assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}