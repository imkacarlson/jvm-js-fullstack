import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            anyHost()
        }
        install(Compression) {
            gzip()
        }

        routing {
            get("/phrases/{locale}") {
                val locale = call.parameters["locale"]?.toString() ?: error("Invalid phrases request")
                call.respond(phrases(locale))
            }
            get("/") {
                call.respondText(
                        this::class.java.classLoader.getResource("index.html")!!.readText(),
                        ContentType.Text.Html
                )
            }
            static("/") {
                resources("")
            }
        }
    }.start(wait = true)
}

fun phrases(locale: String): GreetingItem {
    val filePath: String

    when (locale) {
        "en" -> {
            filePath = "localization/EN-US.json"
        }
        "es" -> {
            filePath = "localization/ES-MX.json"
        }
        else -> {
            throw NullPointerException()
        }
    }

    // https://attacomsian.com/blog/gson-read-json-file
    try {
        // create Gson instance
        val gson = Gson()

        // create a reader
        val reader: Reader = Files.newBufferedReader(Paths.get(filePath))

        // convert JSON string to User object
        val greeting: GreetingItem = gson.fromJson(reader, GreetingItem::class.java)

        // close reader
        reader.close()

        return greeting
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    throw NullPointerException()

}