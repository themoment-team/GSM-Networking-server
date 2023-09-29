package team.themoment.gsmNetworking.global.security.principal

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import team.themoment.gsmNetworking.domain.auth.domain.Authority

class AuthDetails(
    private val authenticationId: Long,
    private val authority: Authority
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(authority.role))

    override fun getPassword(): String? = null

    override fun getUsername(): String = authenticationId.toString()

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false

}