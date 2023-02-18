package perf

import io.gatling.http.Predef._
import io.gatling.core.Predef.{constantUsersPerSec, _}


class TestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://webtours.load-test.ru:1090")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .disableFollowRedirect

  <!--
  CommonScenario().inject(
    incrementUsersPerSec(0.13)
    .times(10)
    .eachLevelLasting(120)
      .startingFrom(0)
      .separatedByRampsLasting(1)
  )
).protocols(httpProtocol)
  /> -->

  setUp(
    CommonScenario().inject(
      incrementUsersPerSec(0.13)
        .times(10)
        .eachLevelLasting(120)
        .startingFrom(0)
        .separatedByRampsLasting(1)
    )
  ).protocols(httpProtocol)
}

