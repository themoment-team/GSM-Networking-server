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

    given("조회할 멘토, 임시 멘토 리스트와 예상 결과 리스트가 주어질 때") {
        every { mentorRepository.findAllMentorInfoDto() } returns dummyMentorInfoDtos
        every { queryTempMentorListService.execute() } returns dummyTempMentorInfoDtos
        val sortedMentorIds = listOf(2, 1, 3, 4, 13, 10, 12, 11)

        `when`("조회 요청 메서드 실행 시") {
            val allMentors = queryAllMentorsService.execute()

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
                val allMentorIds = allMentors.map { it.id }

                // 실행 결과가 예상 결과 리스트(sortedMentorIds) 처럼 정렬된 상태가 되도록 구현되어야 함
                allMentorIds shouldBe sortedMentorIds
            }
        }
    }

    given("같은 사용자 정보를 가지는 멘토, 임시 멘토가 주어질 때") {
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
