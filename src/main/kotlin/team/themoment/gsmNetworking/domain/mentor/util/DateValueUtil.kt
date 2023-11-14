package team.themoment.gsmNetworking.domain.mentor.util

import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DateValueUtil {

    companion object{
        val valueToReplaceNull = LocalDate.of(9999, 12, 31)!!
    }
}
