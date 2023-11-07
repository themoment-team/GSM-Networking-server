package team.themoment.gsmNetworking.domain.mentor.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import team.themoment.gsmNetworking.domain.mentor.dto.CompanyInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.repository.MentorCustomRepository
import team.themoment.gsmNetworking.domain.mentor.service.impl.QueryAllMentorsServiceImpl

@SpringBootTest(classes = [QueryAllMentorsService::class])
class QueryAllMentorsServiceTest : BehaviorSpec({

    val mentorRepository: MentorCustomRepository = mockk()
    val queryTempMentorListService: QueryTempMentorListService = mockk()

    val queryAllMentorsService: QueryAllMentorsService =
        QueryAllMentorsServiceImpl(mentorRepository, queryTempMentorListService)

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
                url = "https://www.abc-company.com"
            ),
            sns = "https://www.instagram.com/a",
            profileUrl = "https://www.instagram.com/img/a",
            registered = true
        ),
        MentorInfoDto(
            id = 2L,
            name = "김철수",
            email = "kim@gmail.com",
            generation = 3,
            position = "개발자",
            company = CompanyInfoDto(
                name = "DEF 회사",
                url = "https://www.def-company.com"
            ),
            sns = "https://www.instagram.com/b",
            profileUrl = "https://www.instagram.com/img/b",
            registered = true
        ),
        MentorInfoDto(
            id = 3L,
            name = "이영희",
            email = "lee@gmail.com",
            generation = 5,
            position = "디자이너",
            company = CompanyInfoDto(
                name = "GHI 회사",
                url = "https://www.ghi-company.com"
            ),
            sns = "https://www.instagram.com/c",
            profileUrl = "https://www.instagram.com/img/c",
            registered = true
        ),
        MentorInfoDto(
            id = 4L,
            name = "박철호",
            email = "park@gmail.com",
            generation = 2,
            position = "마케터",
            company = CompanyInfoDto(
                name = "JKL 회사",
                url = "https://www.jkl-company.com"
            ),
            sns = "https://www.instagram.com/d",
            profileUrl = "https://www.instagram.com/img/d",
            registered = true
        )
    )

    val dummyTempMentorInfoDtos = listOf(
        TempMentorInfoDto(
            id = 10L,
            firebaseId = "firebase_1",
            name = "임꺽정",
            email = "lim@gmail.com",
            generation = 3,
            position = "개발자",
            company = CompanyInfoDto(
                name = "GHI 회사",
                url = "https://www.ghi-company.com"
            ),
            snsUrl = "https://www.instagram.com/c"
        ),
        TempMentorInfoDto(
            id = 11L,
            firebaseId = "firebase_2",
            name = "유관순",
            email = "you@gmail.com",
            generation = 5,
            position = "기획자",
            company = CompanyInfoDto(
                name = "JKL 회사",
                url = "https://www.jkl-company.com"
            ),
            snsUrl = "https://www.instagram.com/d"
        ),
        TempMentorInfoDto(
            id = 12L,
            firebaseId = "firebase_3",
            name = "정도전",
            email = "jeong@gmail.com",
            generation = 3,
            position = "개발자",
            company = CompanyInfoDto(
                name = "MNO 회사",
                url = "https://www.mno-company.com"
            ),
            snsUrl = "https://www.instagram.com/e"
        ),
        TempMentorInfoDto(
            id = 13L,
            firebaseId = "firebase_4",
            name = "김유신",
            email = "kim@gmail.com",
            generation = 2,
            position = "개발자",
            company = CompanyInfoDto(
                name = "PQR 회사",
                url = "https://www.pqr-company.com"
            ),
            snsUrl = "https://www.instagram.com/f"
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
            url = "https://www.abc-company.com"
        ),
        sns = "https://www.instagram.com/a",
        profileUrl = "https://www.instagram.com/img/a",
        registered = true
    )

    val sameUserTempMentor = TempMentorInfoDto(
        id = 14L,
        firebaseId = "firebase_5",
        name = "홍길동",
        email = "hong@gmail.com",
        generation = 3,
        position = "디자이너",
        company = CompanyInfoDto(
            name = "ABC 회사",
            url = "https://www.abc-company.com"
        ),
        snsUrl = "https://www.instagram.com/a"
    )

    given("블루체크 여부(true가 먼저), 직군, 기수, 이름(전부 오름차순)을 기준으로 정렬된 리스트 반환") {
        every { mentorRepository.findAllMentorInfoDto() } returns dummyMentorInfoDtos
        every { queryTempMentorListService.execute() } returns dummyTempMentorInfoDtos

        `when`("조회 요청 메서드 실행 시") {
            val allMentors = queryAllMentorsService.execute()

            then("블루체크 된 사용자들이 반환 순서의 앞쪽에 위치한다") {
                val blueCheckedUsers = allMentors.filter { it.registered }
                val tempUsers = allMentors.filterNot { it.registered }

                val firstBlueCheckedUser = blueCheckedUsers.firstOrNull()
                val firstTempUser = tempUsers.firstOrNull()

                // 테스트를 위해서 두 종류 각각 하나 이상 더미가 존재해야 함
                firstBlueCheckedUser shouldNotBe null
                firstTempUser shouldNotBe null

                val blueCheckedUserIds = blueCheckedUsers.map { it.id }
                val tempUserIds = tempUsers.map { it.id }

                // 더미와 리턴 결과의 id가 일치하는지 확인
                dummyMentorInfoDtos.forEach { it.id shouldBeIn blueCheckedUserIds }
                dummyTempMentorInfoDtos.forEach { it.id shouldBeIn tempUserIds }
            }

            then("임시 사용자와 블루체크 된 사용자 각각 직군, 기수, 이름(전부 오름차순)을 기준으로 정렬된 상태를 가진다") {
                val blueCheckedUsers = allMentors.filter { it.registered }
                val tempUsers = allMentors.filterNot { it.registered }

                //TODO 근데 이런 식이면 구현이 드러나는 테스트 코드 아닌가? 개선이 필요해 보임
                val sortedBlueCheckedUsers = blueCheckedUsers.sortedWith(compareBy({ it.registered }, { it.position }, { it.generation }, { it.name }))
                val sortedTempUsers = tempUsers.sortedWith(compareBy({ it.registered }, { it.position }, { it.generation }, { it.name }))

                allMentors.filter { it.registered } shouldBe sortedBlueCheckedUsers
                allMentors.filterNot { it.registered } shouldBe sortedTempUsers
            }
        }
    }

    given("블루체크 된 사용자와 임시 사용자의 정보가 동일한 경우, 블루체크 된 사용자 정보만 반환") {
        every { mentorRepository.findAllMentorInfoDto() } returns listOf(sameUserMentor)
        every { queryTempMentorListService.execute() } returns listOf(sameUserTempMentor)

        `when`("조회 요청 메서드 실행 시") {
            val allMentors = queryAllMentorsService.execute()

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
