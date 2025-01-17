package org.wsp.mybookshelf.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.wsp.mybookshelf.global.commonEntity.enums.Genre;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;             // 사용자 ID
    private String email;        // 이메일
    private String realname;         // 본명
    private String nickname;     // 닉네임
    private List<Genre> genre; // 선호 장르
}