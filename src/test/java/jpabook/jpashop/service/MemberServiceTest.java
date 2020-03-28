package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)      //JUnit5
@SpringBootTest
@Transactional  //test에 있는 경우 기본적으로 rollback작동
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;
    @Test
    public void 회원가입() throws Exception {

        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        //em.flush(); // 영속성 컨텍스트에 있는 쿼리 날림 -> 이후 Transactional이 rollback해버림
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test//(expected = IllegalStateException.class) -> junit4
    public void 중복_회원_예외() throws Exception {

        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);

        //junit5
        Exception exception = Assertions.assertThrows(IllegalStateException.class, ()->{
            memberService.join(member2);
        });

        //then
        String expectedMessage = "이미 존재하는 회원입니다";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);

        //junit5로 오면서 함수구조가 바뀌어 위와 같이 테스트함
        //fail -> fail코드가 실행되지 않고 밖으로 나가야함 -> 테스트 성공
        //fail("예외가 발생해야 한다");
    }


}