package dev.isxander.evergreenhud.utils

import com.google.gson.*
import java.io.*
import java.lang.IllegalArgumentException
import java.util.stream.Collectors
import kotlin.jvm.Throws


class JsonObjectExt(val data: JsonObject) {
    private val pp: Gson = GsonBuilder().setPrettyPrinting().create()

    constructor() : this(JsonObject())
    constructor(jsonIn: String) : this(JsonParser.parseString(jsonIn).asJsonObject)

    fun optString(key: String, value: String? = "", split: Boolean = true): String? {
        if (key.isEmpty() || !has(key)) return value
        val prim = asPrimitive(get(key))
        return if (prim != null && prim.isString) prim.asString else value
    }

    fun optInt(key: String, value: Int = 0, split: Boolean = true): Int {
        if (key.isEmpty() || !has(key)) {
            return value
        }
        val primitive: JsonPrimitive? = asPrimitive(get(key))
        try {
            if (primitive != null && primitive.isNumber) {
                return primitive.asInt
            }
        } catch (ignored: NumberFormatException) {
        }
        return value
    }

    fun optFloat(key: String, value: Float = 0f, split: Boolean = true): Float {
        if (key.isEmpty() || !has(key)) return value
        val primitive: JsonPrimitive? = asPrimitive(get(key))
        try {
            if (primitive != null && primitive.isNumber) return primitive.asFloat
        } catch (ignored: NumberFormatException) {
        }
        return value
    }

    fun optDouble(key: String, value: Double = 0.0, split: Boolean = true): Double {
        if (key.isEmpty() || !has(key)) {
            return value
        }
        val primitive: JsonPrimitive? = asPrimitive(get(key))
        try {
            if (primitive != null && primitive.isNumber) {
                return primitive.asDouble
            }
        } catch (ignored: NumberFormatException) {
        }
        return value
    }

    fun optBoolean(key: String, value: Boolean = false, split: Boolean = true): Boolean {
        if (key.isEmpty() || !has(key)) return value

        val primitive: JsonPrimitive? = asPrimitive(get(key))
        return if (primitive != null && primitive.isBoolean) {
            primitive.asBoolean
        } else value
    }

    fun optObject(key: String, value: JsonObjectExt? = JsonObjectExt(), split: Boolean = true): JsonObjectExt? {
        if (key.isEmpty() || !has(key)) return value
        return JsonObjectExt(data.getAsJsonObject(key))
    }

    fun optArray(key: String, value: JsonArray? = JsonArray(), split: Boolean = true): JsonArray? {
        if (key.isEmpty() || !has(key)) return value
        return data.getAsJsonArray(key)
    }

    fun splitKey(key: String, lazy: Boolean = true): JsonElement {
        val split = key.split('.')
        var obj = this.data
        for (i in 0 until split.size - 2) {
            val element = obj.get(split[i])
            when {
                element.isJsonObject -> obj = element.asJsonObject
                lazy -> return element
                else -> throw IllegalArgumentException("Invalid key.")
            }
        }
        return obj[split[split.size - 1]]
    }

    fun has(key: String): Boolean = data.has(key)

    fun get(key: String): JsonElement {
        return data.get(key)
    }

    fun addProperty(key: String, value: String?): JsonObjectExt {
        data.addProperty(key, value)
        return this
    }

    fun addProperty(key: String, value: Number?): JsonObjectExt {
        data.addProperty(key, value)
        return this
    }

    fun addProperty(key: String, value: Boolean?): JsonObjectExt {
        data.addProperty(key, value)
        return this
    }

    fun add(key: String, obj: JsonObjectExt): JsonObjectExt {
        data.add(key, obj.data)
        return this
    }

    fun writeToFile(file: File) {
        if (file.exists() && file.isDirectory) {
            return
        }
        if (!file.exists()) {
            val parent: File = file.parentFile
            if (!parent.exists()) {
                parent.mkdirs()
            }
            file.createNewFile()
        }
        FileWriter(file).use { writer ->
            BufferedWriter(writer).use { buffer ->
                buffer.write(toPrettyString())
            }
        }
    }

    private fun asPrimitive(element: JsonElement): JsonPrimitive? {
        return if (element is JsonPrimitive) element.getAsJsonPrimitive() else null
    }

    val keys: List<String>
        get() {
            val keys: MutableList<String> = ArrayList()
            val entrySet: Set<Map.Entry<String, JsonElement?>> = data.entrySet()
            for ((key) in entrySet) {
                keys.add(key)
            }
            return keys
        }

    override fun toString(): String {
        return data.toString()
    }

    fun toPrettyString(): String {
        return pp.toJson(data)
    }

    @Throws(IOException::class)
    fun getFromFile(file: File): JsonObjectExt {
        if (!file.getParentFile().exists() || !file.exists()) throw FileNotFoundException()
        val f = BufferedReader(FileReader(file))
        val lines = f.lines().collect(Collectors.toList())
        f.close()
        if (lines.isEmpty()) return JsonObjectExt()
        val builder = java.lang.String.join("", lines)
        return if (builder.trim { it <= ' ' }.isNotEmpty()) JsonObjectExt(builder.trim { it <= ' ' }) else JsonObjectExt()
    }

    fun getFromLines(lines: List<String?>): JsonObjectExt {
        if (lines.isEmpty()) return JsonObjectExt()
        val builder = java.lang.String.join("", lines)
        return if (builder.trim { it <= ' ' }.isNotEmpty()) JsonObjectExt(builder.trim { it <= ' ' }) else JsonObjectExt()
    }
}