package team.themoment.gsmNetworking.global.security.oauth.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import java.io.Serializable

class UserInfo(
    authorities: MutableCollection<out GrantedAuthority>,
    attributes: Map<String, Any>,
    nameAttributeKey: String
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey), Serializable
