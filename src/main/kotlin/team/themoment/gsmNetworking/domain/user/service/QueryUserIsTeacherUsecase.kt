package team.themoment.gsmNetworking.domain.user.service

import team.themoment.gsmNetworking.domain.user.dto.UserIsTeacherDto

interface QueryUserIsTeacherUsecase {
    fun queryUserIsTeacher(): UserIsTeacherDto
}