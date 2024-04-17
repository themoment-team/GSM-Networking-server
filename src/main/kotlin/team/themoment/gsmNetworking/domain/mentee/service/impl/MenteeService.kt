package team.themoment.gsmNetworking.domain.mentee.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.domain.mentee.domain.Mentee
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeInfoDto
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeRegistrationDto
import team.themoment.gsmNetworking.domain.mentee.repository.MenteeRepository
import team.themoment.gsmNetworking.domain.mentee.service.DeleteMenteeInfoByIdUseCase
import team.themoment.gsmNetworking.domain.mentee.service.GenerateMenteeUseCase
import team.themoment.gsmNetworking.domain.mentee.service.QueryMenteeInfoByIdUseCase
import team.themoment.gsmNetworking.domain.user.dto.UserSaveInfoDto
import team.themoment.gsmNetworking.domain.user.service.DeleteUserInfoByIdUseCase
import team.themoment.gsmNetworking.domain.user.service.GenerateUserUseCase
import team.themoment.gsmNetworking.domain.user.service.QueryUserInfoByIdUseCase

@Service
class MenteeService(
    private val menteeRepository: MenteeRepository,
    private val generateUserUseCase: GenerateUserUseCase,
    private val deleteUserInfoByIdUseCase: DeleteUserInfoByIdUseCase,
    private val queryUserInfoByIdUseCase: QueryUserInfoByIdUseCase
) : GenerateMenteeUseCase,
    DeleteMenteeInfoByIdUseCase,
    QueryMenteeInfoByIdUseCase {

    @Transactional
    override fun generateMentee(menteeRegistrationDto: MenteeRegistrationDto, authenticationId: Long) {
        val userSaveInfoDto = UserSaveInfoDto(
            name = menteeRegistrationDto.name,
            generation = menteeRegistrationDto.generation,
            phoneNumber = menteeRegistrationDto.phoneNumber,
            email = menteeRegistrationDto.email,
            snsUrl = null
        )
        val user = generateUserUseCase.generateUser(userSaveInfoDto, authenticationId)
        val mentee = Mentee(user = user)

        menteeRepository.save(mentee)
    }

    @Transactional
    override fun deleteMenteeInfoById(authenticationId: Long) {
        val user = deleteUserInfoByIdUseCase.deleteUserInfoByIdUseCase(authenticationId)

        menteeRepository.deleteByUser(user)
    }

    @Transactional(readOnly = true)
    override fun queryMenteeInfoById(authenticationId: Long): MenteeInfoDto {
        val userInfoDto = queryUserInfoByIdUseCase.queryUserInfoById(authenticationId)

        return MenteeInfoDto(
            id = userInfoDto.id,
            name = userInfoDto.name,
            email = userInfoDto.email,
            phoneNumber = userInfoDto.phoneNumber,
            generation = userInfoDto.generation,
            defaultImgNumber = userInfoDto.defaultImgNumber
        )
    }
}
