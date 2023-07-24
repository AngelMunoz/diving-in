package com.github.angelmunoz.rotues

import com.github.angelmunoz.DefaultLayout
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.html.*

fun PipelineContext<Unit, ApplicationCall>.isHtmx(): Boolean {
    return call.request.header("HX-Request") == "true"
}

fun HEADER.mainNav(activeRoute: String = "/") {
    nav("uk-navbar-container") {
        attributes["uk-navbar"] = ""
        section("uk-navbar-left") {
            ul("uk-navbar-nav") {
                li(if (activeRoute == "/") "uk-active" else "") {
                    a("/") { +"Home" }
                }
                li(if (activeRoute == "/about") "uk-active" else "") {
                    a("/about") { +"About" }
                }
            }
        }
    }
}

fun FOOTER.mainFooter() {
    section {
        h4 { +"With Love <3" }
    }
    section {
        h4 { +"and Confusion as well!" }
    }
}

fun Application.addPublicRoutes() {
    routing {
        get("/") {
            call.respondHtmlTemplate(DefaultLayout(isHtmx = isHtmx())) {
                laHeader { mainNav() }
                laContent {
                    h1 { +"Hello world!" }
                    section {
                        p { +"This let's go HTMX" }
                        button {
                            classes += "uk-button uk-button-default"
                            attributes["hx-get"] = "/swap-me"
                            attributes["hx-swap"] = "outerHTML"
                            +"Swap me baby!"
                        }
                    }
                }
                laFooter { mainFooter() }
            }
        }
        get("/about") {
            call.respondHtmlTemplate(DefaultLayout(isHtmx = isHtmx())) {
                laHeader { mainNav("/about") }
                laContent {
                    h1 { +"About..." }
                    section {
                        classes += "uk-card uk-card-body"
                        h3 {
                            classes += "uk-card-title"; +"This is the about content!"
                        }
                    }
                }
                laFooter { mainFooter() }
            }
        }
        get("/swap-me") {
            call.respondHtml {
                body {
                    div {
                        attributes["uk-alert"] = ""
                        button(classes = "uk-close-large uk-alert-close") {
                            attributes["uk-close"] = ""
                        }
                        p { +"This is an alert, which should be closable :)" }
                    }
                }
            }
        }
    }
}
