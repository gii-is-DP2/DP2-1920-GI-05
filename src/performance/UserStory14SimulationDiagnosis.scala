package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class UserStory14SimulationDiagnosis extends Simulation {

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
		.pause(13)
	}
	object Login{
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(18)
	}
	object Logged{
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner2")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "6597a659-b85e-420e-9b55-95a351132dd4"))
		.pause(26)
	}
	
	object ShowOwner{
		val showOwner = exec(http("ShowOwner")
			.get("/owners/details")
			.headers(headers_0))
		.pause(25)
	}
	
	object EditPet{
		val editPet = exec(http("EditPet")
			.get("/owners/2/pets/4/edit")
			.headers(headers_0))
		.pause(24)
	}
	
	object PetUpdated{
		val petUpdate = exec(http("PetUpdated")
			.post("/owners/2/pets/4/edit")
			.headers(headers_3)
			.formParam("id", "4")
			.formParam("name", "Jewel")
			.formParam("birthDate", "2010/03/07")
			.formParam("type", "Bird")
			.formParam("guide", "Justice")
			.formParam("_csrf", "2a79178d-f87e-4f38-9172-f87706fadf3c"))
		.pause(21)
	}
	
	object EditSecondPet{
		val editSecondPet = exec(http("EditSecondPet")
			.get("/owners/2/pets/3/edit")
			.headers(headers_0))
		.pause(37)
	}
	
	object ErrorEditForm{
		val errorEditForm = exec(http("ErrorEditForm")
			.post("/owners/2/pets/3/edit")
			.headers(headers_3)
			.formParam("id", "3")
			.formParam("name", "Jewel")
			.formParam("birthDate", "2011/04/17")
			.formParam("type", "Dog")
			.formParam("guide", "Justice")
			.formParam("_csrf", "2a79178d-f87e-4f38-9172-f87706fadf3c"))
		.pause(26)
	}
	
	val pScn = scenario("Positive").exec(Home.home, Login.login, Logged.logged, ShowOwner.showOwner, EditPet.editPet, PetUpdated.petUpdate)
										
	val nScn = scenario("Negative").exec(Home.home, Login.login, Logged.logged, ShowOwner.showOwner, EditSecondPet.editSecondPet, ErrorEditForm.errorEditForm)

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