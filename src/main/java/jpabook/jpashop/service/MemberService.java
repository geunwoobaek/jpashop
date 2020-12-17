package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) throws IllegalAccessException {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) throws IllegalAccessException {
        List<Member> findMember=memberRepository.findByName(member.getUsername());
        if(!findMember.isEmpty()){
            throw new IllegalAccessException("이미 존재하는 회원입니다.");
        }
    }
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setUsername(name);
    }

}
