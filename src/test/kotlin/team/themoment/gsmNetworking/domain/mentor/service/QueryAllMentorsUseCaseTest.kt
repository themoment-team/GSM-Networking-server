package team.themoment.gsmNetworking.domain.mentor.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.beLessThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import team.themoment.gsmNetworking.domain.mentor.dto.CompanyInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.CareerRepository
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.mentor.service.impl.MentorService
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import team.themoment.gsmNetworking.domain.user.service.DeleteUserInfoByIdUseCase
import team.themoment.gsmNetworking.domain.user.service.GenerateUserUseCase
import team.themoment.gsmNetworking.domain.user.service.ModifyUserInfoByIdUseCase
import team.themoment.gsmNetworking.domain.user.service.QueryUserByIdUseCase

@SpringBootTest(classes = [QueryAllMentorsUseCase::class])
class QueryAllMentorsUseCaseTest : BehaviorSpec({

    val mentorRepository: MentorRepository = mockk()
    val careerRepository: CareerRepository = mockk()
    val queryAllTempMentorsUseCase: QueryAllTempMentorsUseCase = mockk()
    val generateUserUseCase: GenerateUserUseCase = mockk()
    val modifyUserInfoByIdUseCase: ModifyUserInfoByIdUseCase = mockk()
    val deleteUserInfoByIdUseCase: DeleteUserInfoByIdUseCase = mockk()
    val queryUserByIdUseCase: QueryUserByIdUseCase = mockk()


    val queryAllMentorsUseCase: QueryAllMentorsUseCase =
        MentorService(
            mentorRepository,
            careerRepository,
            queryAllTempMentorsUseCase,
            generateUserUseCase,
            modifyUserInfoByIdUseCase,
            deleteUserInfoByIdUseCase,
            queryUserByIdUseCase
        )

    //TODO 더미데이터가 코드를 너무 많이 차지하는데...
    val dummyMentorInfoDtos = listOf(
        MentorInfoDto(
            id = 1L,
            name = "홍길동",
            email = "hong@gmail.com",
            generation = 3,
            position = "디자이너",
            company = CompanyInfoDto(
                name = "ABC 회사",
                url = "https://www.abc-company.com",
                address = "광주광역시 광산구 상무대로 312"
            ),
            sns = "https://www.instagram.com/a",
            profileUrl = "https://www.instagram.com/img/a",
            registered = true,
            defaultImgNumber = 0
        ),
        MentorInfoDto(
            id = 2L,
            name = "김철수",
            email = "kim@gmail.com",
            generation = 3,
            position = "개발자",
            company = CompanyInfoDto(
                name = "DEF 회사",
                url = "https://www.def-company.com",
                address = "광주광역시 광산구 상무대로 312"
            ),
            sns = "https://www.instagram.com/b",
            profileUrl = "https://www.instagram.com/img/b",
            registered = true,
            defaultImgNumber = 1
        ),
        MentorInfoDto(
            id = 3L,
            name = "이영희",
            email = "lee@gmail.com",
            generation = 5,
            position = "디자이너",
            company = CompanyInfoDto(
                name = "GHI 회사",
                url = "https://www.ghi-company.com",
                address = "광주광역시 광산구 상무대로 312"
            ),
            sns = "https://www.instagram.com/c",
            profileUrl = "https://www.instagram.com/img/c",
            registered = true,
            defaultImgNumber = 2
        ),
        MentorInfoDto(
            id = 4L,
            name = "박철호",
            email = "park@gmail.com",
            generation = 2,
            position = "마케터",
            company = CompanyInfoDto(
                name = "JKL 회사",
                url = "https://www.jkl-company.com",
                address = "광주광역시 광산구 상무대로 312"
            ),
            sns = "https://www.instagram.com/d",
            profileUrl = "https://www.instagram.com/img/d",
            registered = true,
            defaultImgNumber = 3
        )
    )

    val dummyTempMentorInfoDtos = listOf(
        TempMentorInfoDto(
            id = 10L,
            name = "임꺽정",
            email = "lim@gmail.com",
            generation = 3,
            position = "개발자",
            company = CompanyInfoDto(
                name = "GHI 회사",
                url = "https://www.ghi-company.com",
                address = "광주광역시 광산구 상무대로 312"
            ),
            snsUrl = "https://www.instagram.com/c",
            defaultImgNumber = 4
        ),
        TempMentorInfoDto(
            id = 11L,
            name = "유관순",
            email = "you@gmail.com",
            generation = 5,
            position = "기획자",
            company = CompanyInfoDto(
                name = "JKL 회사",
                url = "https://www.jkl-company.com",
                address = "광주광역시 광산구 상무대로 312"
            ),
            snsUrl = "https://www.instagram.com/d",
            defaultImgNumber = 0
        ),
        TempMentorInfoDto(
            id = 12L,
            name = "정도전",
            email = "jeong@gmail.com",
            generation = 3,
            position = "개발자",
            company = CompanyInfoDto(
                name = "MNO 회사",
                url = "https://www.mno-company.com",
                address = "광주광역시 광산구 상무대로 312"
            ),
            snsUrl = "https://www.instagram.com/e",
            defaultImgNumber = 1
        ),
        TempMentorInfoDto(
            id = 13L,
            name = "김유신",
            email = "kim@gmail.com",
            generation = 2,
            position = "개발자",
            company = CompanyInfoDto(
                name = "PQR 회사",
                url = "https://www.pqr-company.com",
                address = "광주광역시 광산구 상무대로 312"
            ),
            snsUrl = "https://www.instagram.com/f",
            defaultImgNumber = 2
        )
    )

    val sameUserMentor = MentorInfoDto(
        id = 1L,
        name = "홍길동",
        email = "hong@gmail.com",
        generation = 3,
        position = "디자이너",
        company = CompanyInfoDto(
            name = "ABC 회사",
            url = "https://www.abc-company.com",
            address = "광주광역시 광산구 상무대로 312"
        ),
        sns = "https://www.instagram.com/a",
        profileUrl = "https://www.instagram.com/img/a",
        registered = true,
        defaultImgNumber = 3
    )

    val sameUserTempMentor = TempMentorInfoDto(
        id = 14L,
        name = "홍길동",
        email = "hong@gmail.com",
        generation = 3,
        position = "디자이너",
        company = CompanyInfoDto(
            name = "ABC 회사",
            url = "https://www.abc-company.com",
            address = "광주광역시 광산구 상무대로 312"
        ),
        snsUrl = "https://www.instagram.com/a",
        defaultImgNumber = 4
    )

    given("조회할 멘토, 임시 멘토 리스트와 예상 결과 리스트가 주어질 때") {
        every { mentorRepository.findAllMentorInfoDto() } returns dummyMentorInfoDtos
        every { queryAllTempMentorsUseCase.queryAllTempMentors() } returns dummyTempMentorInfoDtos
        val sortedMentorNames = listOf("김철수", "홍길동", "이영희", "박철호", "김유신", "임꺽정", "정도전", "유관순")
        val sortedMentorIds = listOf(1, 2, 3, 4, 5, 6, 7, 8)

        `when`("조회 요청 메서드 실행 시") {
            val allMentors = queryAllMentorsUseCase.queryAllMentors()

            then("블루체크 된 멘토, 임시 멘토가 결과로 반환되어야 한다") {
                val blueCheckedUsers = allMentors.filter { it.registered }
                val tempUsers = allMentors.filterNot { it.registered }

                blueCheckedUsers.shouldNotBeEmpty()
                tempUsers.shouldNotBeEmpty()
            }

            then("임시 사용자와 블루체크 된 사용자 중 블루체크된 사용자가 앞에 존재한다") {
                val blueCheckedUsers = allMentors.filter { it.registered }
                val tempUsers = allMentors.filterNot { it.registered }

                // 블루체크 된 사용자가 먼저 나오는지 확인
                val firstBlueCheckedUser = blueCheckedUsers.first()
                val firstTempUser = tempUsers.first()
                allMentors.indexOf(firstBlueCheckedUser) should beLessThan(allMentors.indexOf(firstTempUser))
            }

            then("인증(블루체크) 여부(true가 먼저), 직군, 기수, 이름 순 정렬되어 반환된다") {
                val allMentorNames = allMentors.map { it.name }

                // 실행 결과가 예상 결과 리스트(sortedMentorIds) 처럼 정렬된 상태가 되도록 구현되어야 함
                allMentorNames shouldBe sortedMentorNames
            }

            then("id가 1부터 재할당되어 반환된다") {
                val allMentorIds = allMentors.map { it.id }

                // 실행 결과가 예상 결과 리스트(sortedMentorIds) 처럼 정렬된 상태가 되도록 구현되어야 함
                allMentorIds shouldBe sortedMentorIds
            }
        }
    }

    given("같은 사용자 정보를 가지는 멘토, 임시 멘토가 주어질 때") {
        every { mentorRepository.findAllMentorInfoDto() } returns listOf(sameUserMentor)
        every { queryAllTempMentorsUseCase.queryAllTempMentors() } returns listOf(sameUserTempMentor)

        `when`("조회 요청 메서드 실행 시") {
            val allMentors = queryAllMentorsUseCase.queryAllMentors()

            then("임시 사용자와 블루체크 된 사용자 정보가 중복된다면, 블루체크 된 사용자만 반환") {
                val blueCheckedUsers = allMentors.filter { it.registered }

                blueCheckedUsers.size shouldBe 1

                val firstBlueCheckedUser = blueCheckedUsers.firstOrNull()
                firstBlueCheckedUser shouldNotBe null

                firstBlueCheckedUser!!.id shouldBe sameUserMentor.id
                firstBlueCheckedUser.name shouldBe sameUserMentor.name
                firstBlueCheckedUser.email shouldBe sameUserMentor.email
            }
        }
    }
})
