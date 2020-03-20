package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional      //Test에 Transactional이 있으면 Test가 끝나면 모두 Rollback해버림
    @Rollback(false)    //Rollback 하지 않고싶은 경우 사용 -> 자동 commit
    public void testMember() throws Exception {

        //given
        Member member = new Member();
        member.setUserName("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUserName()).isEqualTo(member.getUserName());

        //저장한 Entity와 조회한 Entity가 같은가??
        //같은 트랜잭션 안에서 저장하고 조회하는 경우는 같은 영속성 컨텍스트 안에서는 같은 id값을 가지면 같은 Entity로 인식함
        assertThat(findMember).isEqualTo(member);
    }
}