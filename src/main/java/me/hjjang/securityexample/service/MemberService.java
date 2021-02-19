package me.hjjang.securityexample.service;

import lombok.RequiredArgsConstructor;
import me.hjjang.securityexample.controller.dto.MemberDto;
import me.hjjang.securityexample.domain.Member;
import me.hjjang.securityexample.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member member = optionalMember.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(member.getUsername(), member.getPassword(),authorities);
    }
}
