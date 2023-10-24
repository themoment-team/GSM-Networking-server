package team.themoment.gsmNetworking.thirdParty.firebase.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

@Configuration
class FirebaseConfig (
    @Value("\${firebase.key-file-name}")
    private val keyFileName: String
) {

    @Bean
    fun firestore(): Firestore {
        val serviceAccount = FileInputStream(keyFileName)
        val options = FirestoreOptions.newBuilder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setProjectId("gsm-networking")
            .build()

        return options.service
    }
}
