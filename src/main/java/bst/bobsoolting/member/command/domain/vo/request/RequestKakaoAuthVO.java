package bst.bobsoolting.member.command.domain.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestKakaoAuthVO {
    @Schema(description = "카카오에서 받은 인가 코드", example = "abcdef1234567890")
    private String code;
}