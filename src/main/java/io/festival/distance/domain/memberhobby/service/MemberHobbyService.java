package io.festival.distance.domain.memberhobby.service;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.memberhobby.entity.MemberHobby;
import io.festival.distance.domain.memberhobby.repository.MemberHobbyRepository;
import io.festival.distance.domain.membertag.entity.MemberTag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class MemberHobbyService {
    private final MemberHobbyRepository memberHobbyRepository;

    @Transactional
    public void updateHobby(Member member, List<MemberHobbyDto> memberHobbyDto){
        List<MemberHobby> memberHobby=new ArrayList<>();

        for (MemberHobbyDto hobbyDto : memberHobbyDto) {
            MemberHobby hobby = MemberHobby.builder()
                    .hobbyName(hobbyDto.hobby())
                    .member(member)
                    .build();
            memberHobby.add(hobby);
        }
        memberHobbyRepository.saveAll(memberHobby);
    }

    @Transactional(readOnly = true)
    public List<MemberHobbyDto> showHobby(Member member){
        List<MemberHobby> allByMember = memberHobbyRepository.findAllByMember(member);
        List<MemberHobbyDto> hobbyDtoList=new ArrayList<>();
        for(MemberHobby hobby:allByMember){
            MemberHobbyDto hobbyDto = MemberHobbyDto.builder()
                    .hobby(hobby.getHobbyName())
                    .build();
            hobbyDtoList.add(hobbyDto);
        }
        return hobbyDtoList;
    }

    @Transactional
    public void modifyHobby(Member member, List<MemberHobbyDto> memberHobbyDto){
        List<MemberHobby> allByMember = memberHobbyRepository.findAllByMember(member);
        for (int i = 0; i < memberHobbyDto.size(); i++) {
            MemberHobbyDto dto=memberHobbyDto.get(i);
            if(allByMember.size()>i)
                allByMember.get(i).modifyHobby(dto.hobby());
            else{
                MemberHobby hobby = MemberHobby.builder()
                        .member(member)
                        .hobbyName(dto.hobby())
                        .build();
                memberHobbyRepository.save(hobby);
            }
        }

        if(allByMember.size()>memberHobbyDto.size()){
            memberHobbyRepository.deleteAll(allByMember.subList(memberHobbyDto.size(),allByMember.size()));
        }
    }
}
