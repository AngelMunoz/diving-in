package com.github.angelmunoz.plugins

import io.ktor.server.plugins.compression.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File
import io.ktor.server.routing.*

fun Application.configureHTTP() {
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }
    routing {
        staticFiles("/assets", File("src/main/resources/static")) {
            preCompressed(CompressedFileType.BROTLI, CompressedFileType.GZIP)
        }
    }
}
