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
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


fun main() {
    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
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

    /*
    val path = System.getProperty("user.dir")

    println("Working Directory = $path")

    // https://attacomsian.com/blog/gson-read-json-file

    try {
        // create Gson instance
        val gson = Gson()

        // create a reader
        val reader: Reader = Files.newBufferedReader(Paths.get("localization/US-EN.json"))

        // convert JSON file to map
        val map = gson.fromJson<Map<*, *>>(reader, MutableMap::class.java)

        // print map entries
        for ((key, value) in map) {
            println(key.toString() + "=" + value)
        }

        // close reader
        reader.close()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
     */
}

fun phrases(locale: String): GreetingItem {
    if(locale == "en") {
        var gson = Gson()
        var test = "{\"greeting\": \"Hello World\"}"

        return gson.fromJson(test, GreetingItem::class.java)
    }else{
        throw NullPointerException()
    }
}