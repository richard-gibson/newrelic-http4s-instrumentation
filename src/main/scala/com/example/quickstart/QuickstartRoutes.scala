package com.example.quickstart

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import cats.effect.Timer
import scala.concurrent.duration.DurationInt

object QuickstartRoutes {

  def jokeRoutes[F[_]: Sync](J: Jokes[F])(implicit t: Timer[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    import com.newrelic.cats.api.TraceOps.asyncTrace
    
    HttpRoutes.of[F] { case GET -> Root / "joke" =>
      for {
        _ <- asyncTrace("sleep")(t.sleep(1500.millis))
        joke <- asyncTrace("fetch-joke")(J.get)
        resp <- asyncTrace("render-response")(Ok(joke))
      } yield resp
    }
  }

  def helloWorldRoutes[F[_]: Sync](H: HelloWorld[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / "hello" / name =>
      for {
        greeting <- H.hello(HelloWorld.Name(name))
        resp     <- Ok(greeting)
      } yield resp
    }
  }
}
