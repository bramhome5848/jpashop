package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 전용에는 readOnly= true가 효과적, public에서 수행됨
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    //회원가입
    //클래스, 메서드위에 @Transactional 이 추가되면, 이 클래스에 트랜잭션 기능이 적용된 프록시 객체가 생성된다.
    //이 프록시 객체는 @Transactional이 포함된 메소드가 호출 될 경우,
    //PlatformTransactionManager를 사용하여 트랜잭션을 시작하고,
    //정상 여부에 따라 Commit 또는 Rollback 한다.
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member);
        memberRepository.save(member);

        // save실행시 실제 DB에 올라가기 전에 .persist수행으로 영속성 컨텍스트에 올라갈 때
        // PK가 생성되고 객체에 적제됨
        return member.getId();
    }

    //이름 중복회원 체크
    private void validateDuplicateMember(Member member) {

        List<Member> findMembers =  memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 단건 조회
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);   //변경감지로 자동 업데이트
    }
}
