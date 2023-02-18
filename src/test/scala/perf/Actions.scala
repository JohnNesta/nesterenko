package perf

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Actions {

  val webtours = http("webtours")
    .get("/webtours/")
    .check(status is 200)

  val welcome = http("welcome.pl")
    .get("/cgi-bin/welcome.pl")
    .queryParam("singOff","true")
    .check(status is 200)

  val nav = http("nav.pl")
    .get("/cgi-bin/nav.pl")
    .queryParam("in", "home")
    .check(status is 200)
    .check(regex("""name="userSession" value="(.+)"""").saveAs("userSession"))

  //login

  val loginPl = http("/cgi-bin/login.pl")
    .post("/cgi-bin/login.pl")
    .formParam("userSession","${userSession}")
    .formParam("username","${login}")
    .formParam("password","${password}")
    .formParam("login.x","0")
    .formParam("login.y","0")
    .formParam("JSFormSubmit","off")
    .check(status is 200)

  //tickets

  val reservations = http("/cgi-bin/reservations")
    .get("/cgi-bin/reservations.pl")
    .formParam("page", "welcome")
    .check(status is 200)

  val find_flight_step1 = http("find_flight_step1")
    .post("/cgi-bin/reservations.pl")
    .formParam("advanceDiscount", "0")
    .formParam("depart", "${depart}")
    .formParam("departDate", "02/12/2023")
    .formParam("arrive", "${arrive}")
    .formParam("returnDate", "02/13/2023")
    .formParam("numPassengers", "1")
    .formParam("seatPref", "None")
    .formParam("seatType", "Coach")
    .formParam("findFlights.x", "44")
    .formParam("findFlights.y", "9")
    .formParam(".cgifields", "roundtrip")
    .formParam(".cgifields", "seatType")
    .formParam(".cgifields", "seatPref")
    .check(status is 200)
    .check(regex("""name="outboundFlight" value="(.*?)"""").findAll.saveAs("outboundFlight1"))

  val find_flight_step2 = http("find_flight_step2")
    .post("/cgi-bin/reservations.pl")
    .formParam("outboundFlight", "${outboundFlight1.random()}")
    .formParam("numPassengers", "1")
    .formParam("advanceDiscount", "0")
    .formParam("seatType", "Coach")
    .formParam("seatPref", "Window")
    .formParam("reserveFlights.x", "72")
    .formParam("reserveFlights.y", "18")
    .check(status is 200)
    .check(regex("""name="outboundFlight" value="(.*?)"""").find.saveAs("outboundFlight2"))

  val payment_details = http("payment_details")
    .post("/cgi-bin/reservations.pl")
    .formParam("firstName", "Evgeni")
    .formParam("lastName", "Nesterenko")
    .formParam("address1", "KARLA MARKSA, D. 116a")
    .formParam("address2", "Voronezh")
    .formParam("pass1", "Evgeni Nesterenko")
    .formParam("creditCard", "")
    .formParam("expDate", "")
    .formParam("oldCCOption", "")
    .formParam("numPassengers", "1")
    .formParam("seatType", "Coach")
    .formParam("seatPref", "Window")
    .formParam("outboundFlight", "${outboundFlight2}")
    .formParam("advanceDiscount", "0")
    .formParam("returnFlight", "")
    .formParam("JSFormSubmit", "off")
    .formParam("buyFlights.x:", "63")
    .formParam("buyFlights.y:", "17")
    .formParam("cgifields", "saveCC")
    .check(status is 200)

    val itinerary = http("itinerary")
      .get("/cgi-bin/itinerary.pl")
      .check(status is 200)

  val logOut = http("logOut")
    .get("/cgi-bin/welcome.pl")
    .queryParam("singOff","1")
    .check(status is 200)

}
