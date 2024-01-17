package com.damoacon.domain.preference.service;

import com.damoacon.domain.event.entity.Event;
import com.damoacon.domain.event.repository.EventRepository;
import com.damoacon.domain.member.entity.Member;
import com.damoacon.domain.member.repository.MemberRepository;
import com.damoacon.domain.preference.dto.comment.CommentRequestDto;
import com.damoacon.domain.preference.entity.Comment;
import com.damoacon.domain.preference.repository.CommentRepository;
import com.damoacon.global.constant.ErrorCode;
import com.damoacon.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Long postComment(Long eventId, Member member, CommentRequestDto commentRequestDto) throws GeneralException {
        // validate Member and Event
        memberRepository.findById(member.getId()).orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));
        Event event = validateEvent(eventId);

        Comment comment = Comment.builder()
                .event(event)
                .member(member)
                .content(commentRequestDto.getContent())
                .build();

        return commentRepository.save(comment).getId();
    }

    private Event validateEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new GeneralException(ErrorCode.EVENT_NOT_FOUND));

        return event;
    }
}
