package io.festival.distance.domain.conversation.waiting.repository;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.conversation.waiting.entity.ChatWaiting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatWaitingRepository extends JpaRepository<ChatWaiting,Long> {
    List<ChatWaiting> findAllByLoveReceiver(Member member);
    Integer countByLoveReceiver(Member member);
    boolean existsByLoveSenderAndLoveReceiver(Member loveSender, Member loveReceiver);
    void deleteByWaitingIdAndLoveReceiver(Long waitingId, Member member);
}
