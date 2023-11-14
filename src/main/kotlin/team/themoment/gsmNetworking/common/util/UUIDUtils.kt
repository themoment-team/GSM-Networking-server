package team.themoment.gsmNetworking.common.util

import com.fasterxml.uuid.Generators
import java.time.*
import java.util.*

class UUIDUtils {
    companion object {
        private const val MAX_EPOCH_MILLIS = 95649119999000L // 5000-12-31 23:59:59 GMT
        private const val MIN_EPOCH_MILLIS = 0L // 1970-1-1 00:00:00 GMT

        val MAX_TIME: Instant = Instant.ofEpochMilli(MAX_EPOCH_MILLIS)
        val MIN_TIME: Instant = Instant.ofEpochMilli(MIN_EPOCH_MILLIS)

        /**
         * v7 UUID를 생성합니다.
         *
         * @return v7 UUID
         */
        fun generateUUIDv7(): UUID = Generators.timeBasedEpochGenerator().generate()

        /**
         * 주어진 epoch 밀리초를 사용하여 가장 작은 v7 UUID를 생성합니다.
         *
         * @param epochMilli 특정 시간을 나타내는 epoch 밀리초 값입니다.
         * @return epoch 밀리초에서 생성된 가장 작은 v7 UUID입니다.
         */
        private fun generateSmallestUUIDv7(epochMilli: Long): UUID {
            // 지정된 위치에 '-'를 삽입하여 포맷된 UUID 문자열을 생성합니다.
            // 결과적으로 형식은 "xxxxxxxx-xxxx-7000-0000-000000000000"와 같습니다.
            val hex = String.format("%012x-7000-0000-000000000000", epochMilli)
                .replaceRange(8, 8, "-")
            return UUID.fromString(hex)
        }

        /**
         * 가장 작은 v7 UUID를 생성합니다.
         *
         * @param time 변환할 LocalDateTime 객체
         * @return epoch 밀리초 값
         */
        fun generateSmallestUUIDv7(time: LocalDateTime): UUID {
            val epochMilli = getEpochMilli(time)
            return generateSmallestUUIDv7(epochMilli)
        }

        /**
         * 가장 작은 v7 UUID를 생성합니다.
         *
         * @param time 변환할 Instant 객체
         * @return epoch 밀리초 값
         */
        fun generateSmallestUUIDv7(instant: Instant): UUID {
            val epochMilli = instant.toEpochMilli()
            return generateSmallestUUIDv7(epochMilli)
        }

        /**
         * 주어진 UUID에서 시간 정보를 반환합니다.
         *
         * @param uuid epoch 밀리초를 추출할 UUID
         * @return UUID에서 추출된 시간 정보, LocalDateTime 타입
         */
        fun getTime(uuid: UUID): LocalDateTime {
            return Instant.ofEpochMilli(getEpochMilli(uuid))
                .atZone(ZoneId.systemDefault()).toLocalDateTime()
        }

        /**
         * 주어진 UUID에서 epoch 밀리초를 추출합니다.
         *
         * @param uuid epoch 밀리초를 추출할 UUID
         * @return UUID에서 추출된 epoch 밀리초
         */
        fun getEpochMilli(uuid: UUID): Long {
            // v7 UUID는 타임스탬프 정보가 Most Significant Bits(총 48bit) 중 앞 36bit에 저장됩니다.
            // MSB를 오른쪽으로 16bit 시프트하여 타임스탬프를 추출합니다.
            return uuid.mostSignificantBits ushr 16
        }

        /**
         * 주어진 UUID에서 시간 정보를 반환합니다.
         *
         * @param uuid epoch 밀리초를 추출할 UUID
         * @return UUID에서 추출된 시간 정보, Instant 타입
         */
        fun getInstant(uuid: UUID): Instant {
            val milliseconds = getEpochMilli(uuid)
            return Instant.ofEpochMilli(milliseconds)
        }

        /**
         * LocalDateTime 객체에서 epoch 밀리초를 추출합니다.
         *
         * @param time 변환할 LocalDateTime 객체
         * @return epoch 밀리초 값
         */
        private fun getEpochMilli(time: LocalDateTime): Long {
            return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }
    }
}
