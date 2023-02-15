package perf

import io.gatling.core.Predef._

object Feeders {

  val users = csv (fileName="users.csv").circular

}
