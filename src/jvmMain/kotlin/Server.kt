import com.google.gson.Gson
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Paths


fun main() {

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
}