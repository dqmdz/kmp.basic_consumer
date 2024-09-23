package client

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonArray
import model.Persona

// Definir la base URL en un solo lugar
private const val BASE_URL = "http://192.168.201.218:8080"

// Instancia de Json configurada para pretty print
val jsonPretty = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}

val client = HttpClient {
    install(ContentNegotiation) {
        json(jsonPretty) // Usar jsonPretty para pretty print
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                // Detectar si el mensaje es JSON y formatearlo
                if (message.trim().startsWith("{") || message.trim().startsWith("[")) {
                    try {
                        // Detectamos si es un objeto o un array JSON
                        val jsonElement = jsonPretty.parseToJsonElement(message)
                        val prettyJson = if (jsonElement is JsonArray) {
                            jsonPretty.encodeToString(JsonArray.serializer(), jsonElement.jsonArray)
                        } else {
                            jsonPretty.encodeToString(JsonObject.serializer(), jsonElement.jsonObject)
                        }
                        println("Ktor Log (Pretty JSON):\n$prettyJson")
                    } catch (e: Exception) {
                        println("Ktor Log: $message") // Si no es JSON, imprimir el mensaje normal
                    }
                } else {
                    println("Ktor Log: $message")
                }
            }
        }
        level = LogLevel.BODY
    }
}

// Función que consume el backend local y deserializa directamente a una lista de Persona
suspend fun getPersonaData(): List<Persona> {
    return client.get("$BASE_URL/personas").body() // Deserializa automáticamente a List<Persona>
}

// Función para eliminar una persona por ID
suspend fun deletePersona(personaId: Int?): HttpResponse {
    return client.delete("$BASE_URL/personas/$personaId")
}

// Función para agregar una persona y devolver la persona creada
suspend fun addPersona(persona: Persona): Persona {
    return client.post("$BASE_URL/personas") {
        contentType(ContentType.Application.Json)
        setBody(persona) // Enviamos la persona al backend
    }.body() // Devolvemos el cuerpo de la respuesta deserializado como Persona
}

// Función para actualizar una persona y devolver la persona actualizada
suspend fun updatePersona(personaId: Int?, persona: Persona): Persona {
    return client.put("$BASE_URL/personas/$personaId") {
        contentType(ContentType.Application.Json)
        setBody(persona)
    }.body()
}