package app.aaps.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import app.aaps.database.entities.embedments.InterfaceIDs
import app.aaps.database.entities.interfaces.DBEntryWithTimeAndDuration
import app.aaps.database.entities.interfaces.TraceableDBEntry
import java.util.TimeZone

@Entity(
    tableName = TABLE_TEMPORARY_TARGETS,
    foreignKeys = [ForeignKey(
        entity = TemporaryTarget::class,
        parentColumns = ["id"],
        childColumns = ["referenceId"]
    )],
    indices = [
        Index("id"),
        Index("isValid"),
        Index("nightscoutId"),
        Index("referenceId"),
        Index("timestamp")
    ]
)
data class TemporaryTarget(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    override var version: Int = 0,
    override var dateCreated: Long = -1,
    override var isValid: Boolean = true,
    override var referenceId: Long? = null,
    @Embedded
    override var interfaceIDs_backing: InterfaceIDs? = InterfaceIDs(),
    override var timestamp: Long,
    override var utcOffset: Long = TimeZone.getDefault().getOffset(timestamp).toLong(),
    var reason: Reason,
    var highTarget: Double, // in mgdl
    var lowTarget: Double, // in mgdl
    override var duration: Long // in millis
) : TraceableDBEntry, DBEntryWithTimeAndDuration {

    enum class Reason {
        CUSTOM,
        HYPOGLYCEMIA,
        ACTIVITY,
        EATING_SOON,
        AUTOMATION,
        WEAR
        ;
    }
}