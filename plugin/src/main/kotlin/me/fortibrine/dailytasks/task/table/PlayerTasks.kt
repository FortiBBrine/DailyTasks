package me.fortibrine.dailytasks.task.table

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "PlayerTasks")
class PlayerTasks (
    @field:DatabaseField(id = true)
    var username: String? = "",

    @field:DatabaseField(canBeNull = false, dataType = DataType.LONG_STRING)
    var tasks: String? = "",

    @field:DatabaseField(canBeNull = false)
    var date: Date? = Date()
)
