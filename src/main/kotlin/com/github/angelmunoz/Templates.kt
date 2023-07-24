package com.github.angelmunoz

import io.ktor.server.html.*
import kotlinx.html.*

data class DefaultLayoutOptions(
    val hasHeader: Boolean = true,
    val hasFooter: Boolean = true,
    val headerClasses: String? = null,
    val footerClasses: String? = null,
    val contentClasses: String? = null
);

class DefaultLayout(
    private val isHtmx: Boolean = false,
    private val options: DefaultLayoutOptions = DefaultLayoutOptions()
) : Template<HTML> {
    val headScriptsAndStyles = Placeholder<FlowOrMetaDataOrPhrasingContent>()
    val bodyScripts = Placeholder<FlowOrMetaDataOrPhrasingContent>()

    val laHeader = Placeholder<HEADER>()
    val laContent = Placeholder<MAIN>()
    val laFooter = Placeholder<FOOTER>()

    override fun HTML.apply() {
        fun BODY.buildBody() {
            attributes["hx-boost"] = "true"
            if (options.hasHeader)
                header { classes += options.headerClasses.orEmpty(); insert(laHeader) }

            main { classes += options.contentClasses.orEmpty(); insert(laContent) }

            if (options.hasFooter)
                footer { classes += options.footerClasses.orEmpty(); insert(laFooter) }
        }
        if (!isHtmx) {
            head {
                link("https://cdn.jsdelivr.net/npm/uikit@3.16.22/dist/css/uikit.min.css", "stylesheet", "text/css")
                link("/assets/index.css", "stylesheet", "text/css")
                insert(headScriptsAndStyles)
            }
            body {
                buildBody()

                script("text/javascript", "https://unpkg.com/htmx.org@1.9.3") {}
                script("text/javascript", "https://cdn.jsdelivr.net/npm/uikit@3.16.22/dist/js/uikit.min.js") {}
                script("text/javascript", "https://cdn.jsdelivr.net/npm/uikit@3.16.22/dist/js/uikit-icons.min.js") {}
                insert(bodyScripts)
            }
        } else {
            body {
                buildBody()
            }
        }

    }
}
