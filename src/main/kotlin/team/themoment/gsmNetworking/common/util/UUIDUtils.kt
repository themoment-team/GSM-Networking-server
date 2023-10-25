package team.themoment.gsmNetworking.common.util

import java.time.*
import java.util.*

class UUIDUtils {
    companion object {
        private const val UUID_FORMAT = "%012x-7000-0000-000000000000"
        private const val MAX_EPOCH_MILLIS = 95649119999000L // 5000-12-31 23:59:59 GMT
        private const val MIN_EPOCH_MILLIS = 0L // 1970-1-1 00:00:00 GMT

        val MAX_TIME: Instant = Instant.ofEpochMilli(MAX_EPOCH_MILLIS)
        val MIN_TIME: Instant = Instant.ofEpochMilli(MIN_EPOCH_MILLIS)

        private fun generateUUIDv7FromEpochMilli(epochMilli: Long): UUID {
            val hex = String.format(UUID_FORMAT, epochMilli).replaceRange(8, 8, "-")
            return UUID.fromString(hex)
        }

        fun generateSmallestUUIDv7(time: LocalDateTime): UUID {
            val epochMilli = getEpochMilli(time)
            return generateUUIDv7FromEpochMilli(epochMilli)
        }

        fun generateSmallestUUIDv7(instant: Instant): UUID {
            val epochMilli = instant.toEpochMilli()
            return generateUUIDv7FromEpochMilli(epochMilli)
        }

        fun getTime(uuid: UUID): LocalDateTime {
            return Instant.ofEpochMilli(getEpochMilli(uuid))
                .atZone(ZoneId.systemDefault()).toLocalDateTime()
        }

        fun getEpochMilli(uuid: UUID): Long {
            return uuid.mostSignificantBits ushr 16
        }

        fun getInstant(uuid: UUID): Instant {
            val milliseconds = getEpochMilli(uuid)
            return Instant.ofEpochMilli(milliseconds)
        }

        fun getEpochMilli(time: LocalDateTime): Long {
            return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }
    }
}
