
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory4 extends Simulation {

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

	val uri2 = "http://www.imgur.com/image"
	
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

		object FieldList {
			val fieldList = exec(http("fieldList")
			.get("/fields/all")
			.headers(headers_0))
		.pause(8)
		}

		object FieldForm {
			val fieldForm = exec(http("fieldForm")
			.get("/fields/new")
			.headers(headers_0))
		.pause(48)
		}

		object FieldCreated {
			val fieldCreated = exec(http("fieldCreated")
			.post("/fields/new")
			.headers(headers_3)
			.formParam("name", "nuevoCampo")
			.formParam("photoURL", "http://www.imgur.com/image")
			.formParam("lenght", "300.55")
			.formParam("width", "250.55")
			.formParam("_csrf", "0b54dcd1-e42a-410e-a274-a758ea2e3b6a")
			.resources(http("request_7")
			.get(uri2)
			.headers(headers_2)
			.check(status.is(301))))
		.pause(33)
		}

	val scn1 = scenario("Admins").exec(Home.home,
									  Login.login,
									  Logged.logged,
									  FieldList.fieldList,
									  FieldForm.fieldForm,
									  FieldCreated.fieldCreated)
	val scn2 = scenario("Owners").exec(Home.home,
									  Login.login,
									  Logged.logged,
									  FieldList.fieldList,
									  FieldForm.fieldForm,
									  FieldCreated.fieldCreated)

	

	setUp(
		scn1.inject(rampUsers(5000) during (90 seconds)),
		scn2.inject(rampUsers(5000) during (90 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95))
}