package team.themoment.gsmNetworking.global.security.encrypt

interface EncryptManager {

    fun encrypt(encryptString: String): String
    fun decrypt(decryptString: String): String

}