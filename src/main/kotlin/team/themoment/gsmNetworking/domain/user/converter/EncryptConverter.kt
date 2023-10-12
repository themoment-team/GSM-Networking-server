package team.themoment.gsmNetworking.domain.user.converter

import team.themoment.gsmNetworking.global.security.encrypt.EncryptManager
import org.springframework.stereotype.Component
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
@Component
class EncryptConverter(
    private val encryptManager: EncryptManager
) : AttributeConverter<String, String> {

    override fun convertToDatabaseColumn(attribute: String?): String? {
        if (attribute == null) return null
        return encryptManager.encrypt(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): String? {
        if (dbData == null) return null
        return encryptManager.decrypt(dbData)
    }

}
