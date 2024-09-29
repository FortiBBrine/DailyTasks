package me.fortibrine.dailytasks.api.task

import com.google.gson.*
import java.lang.reflect.Type

class TaskAdapter: JsonSerializer<Task>, JsonDeserializer<Task> {
    override fun serialize(src: Task, type: Type, context: JsonSerializationContext): JsonElement {
        val obj = JsonObject()
        obj.addProperty("className", src::class.java.name)
        obj.add("properties", context.serialize(src))
        return obj
    }

    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Task {
        val jsonObject = json.asJsonObject
        val prim = jsonObject.getAsJsonPrimitive("className")
        val className = prim.asString
        val klass = Class.forName(className)
        return context.deserialize(
            jsonObject.get("properties"),
            klass
        )
    }


}