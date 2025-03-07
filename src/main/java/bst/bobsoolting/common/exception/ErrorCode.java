package bst.bobsoolting.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400: 잘못된 요청 (Bad Request)
    WRONG_ENTRY_POINT(40000, HttpStatus.BAD_REQUEST, "잘못된 접근입니다"), // 사용자가 잘못된 URL로 접근했을 때 발생
    MISSING_REQUEST_PARAMETER(40001, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."), // 요청에 필요한 파라미터가 누락된 경우
    INVALID_PARAMETER_FORMAT(40002, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자 형식입니다."), // 파라미터 형식이 잘못된 경우
    BAD_REQUEST_JSON(40003, HttpStatus.BAD_REQUEST, "잘못된 JSON 형식입니다."), // JSON 요청 형식 오류
    DATA_INTEGRITY_VIOLATION(40004, HttpStatus.BAD_REQUEST, "데이터 무결성 위반입니다. 필수 값이 누락되었거나 유효하지 않습니다."), // 데이터베이스 무결성 위반 (예: NOT NULL 컬럼에 NULL 삽입 시도)
    INVALID_INPUT_VALUE(40005, HttpStatus.BAD_REQUEST, "잘못된 입력 값입니다."), // 입력 값이 유효하지 않은 경우
    INVALID_REQUEST_BODY(40006, HttpStatus.BAD_REQUEST, "잘못된 요청 본문입니다."), // 요청 본문에 유효하지 않은 데이터가 포함된 경우
    MISSING_REQUIRED_FIELD(40007, HttpStatus.BAD_REQUEST, "필수 필드가 누락되었습니다."), // JSON 또는 요청 데이터에서 필수 필드가 누락된 경우
    EMPTY_REQUEST_INPUTSTREAM(40008, HttpStatus.BAD_REQUEST, "요청 본문이 비어 있습니다. 필수 데이터를 포함해야 합니다."),
    ALREADY_LIKED(40009, HttpStatus.BAD_REQUEST, "이미 좋아요 누른 게시글입니다."),
    ALREADY_EXISTS(40010, HttpStatus.BAD_REQUEST, "이미 등록된 유저입니다."),

    // 401: 인증 실패 (Unauthorized)
    INVALID_HEADER_VALUE(40100, HttpStatus.UNAUTHORIZED, "올바르지 않은 헤더값입니다."), // 헤더 값이 잘못되었거나 누락된 경우
    EXPIRED_TOKEN_ERROR(40101, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."), // 인증 토큰이 만료된 경우
    INVALID_TOKEN_ERROR(40102, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."), // 토큰이 잘못되었거나 위조된 경우
    TOKEN_MALFORMED_ERROR(40103, HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."), // 토큰 구조가 올바르지 않은 경우
    TOKEN_TYPE_ERROR(40104, HttpStatus.UNAUTHORIZED, "토큰 타입이 일치하지 않거나 비어있습니다."), // 토큰의 타입이 잘못되었거나 누락된 경우
    TOKEN_UNSUPPORTED_ERROR(40105, HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."), // 서버가 지원하지 않는 토큰 유형
    TOKEN_GENERATION_ERROR(40106, HttpStatus.UNAUTHORIZED, "토큰 생성에 실패하였습니다."), // 토큰 생성 중 오류 발생
    TOKEN_UNKNOWN_ERROR(40107, HttpStatus.UNAUTHORIZED, "알 수 없는 토큰입니다."), // 알 수 없는 이유로 토큰이 유효하지 않은 경우
    LOGIN_FAILURE(40108, HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다"), // 로그인 실패
    UNAUTHORIZED_ACCESS(40109, HttpStatus.UNAUTHORIZED, "인증되지 않은 접근입니다."), // 인증되지 않은 사용자 접근
    EXPIRED_SESSION(40110, HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다."), // 사용자 세션이 만료된 경우
    EXIST_USER(40111, HttpStatus.UNAUTHORIZED, "이미 회원가입한 회원입니다."), // 이미 회원가입된 사용자
    NOT_FOUND_USER_ID(40112, HttpStatus.UNAUTHORIZED, "아이디를 잘못 입력하셨습니다."), // 잘못된 아이디 입력
    INVALID_PASSWORD(40113, HttpStatus.UNAUTHORIZED, "비밀번호를 잘못 입력하셨습니다."), // 비밀번호가 잘못된 경우
    EXPIRED_CODE(40114, HttpStatus.UNAUTHORIZED, "만료된 코드입니다."), // redis TTL이 만료된 경우(음수)
    INVALID_AUTHENTICATION_OBJECT(40115, HttpStatus.UNAUTHORIZED, "Authentication 객체가 CustomUserDetails 타입이 아닙니다."),
    EMAIL_AUTH_CODE_EXPIRED(40116, HttpStatus.UNAUTHORIZED, "인증 번호가 만료되었습니다. 다시 요청해주세요."),
    EMAIL_AUTH_CODE_INVALID(40117, HttpStatus.UNAUTHORIZED, "인증 번호가 올바르지 않습니다. 다시 요청해주세요."),
    LOGIN_HISTORY_FAILURE(40118, HttpStatus.UNAUTHORIZED, "로그인 이력 저장에 실패했습니다."), // 로그인 이력 기록 실패
    LOGGED_OUT(40119, HttpStatus.UNAUTHORIZED, "로그아웃된 토큰입니다."), // 로그아웃된 토큰인 경우
    NEW_MEMBER_REGISTRATION_REQUIRED(40120, HttpStatus.UNAUTHORIZED, "신규 회원입니다. 추가 정보를 입력해 주세요."),

    // 403: 권한 부족 (Forbidden)
    FORBIDDEN_ROLE(40300, HttpStatus.FORBIDDEN, "요청한 리소스에 대한 권한이 없습니다."), // 사용자가 요청한 리소스에 대한 권한이 없는 경우
    ACCESS_DENIED(40301, HttpStatus.FORBIDDEN, "접근 권한이 거부되었습니다."), // 권한 부족으로 접근이 거부된 경우
    INACTIVE_ACCOUNT(40302, HttpStatus.FORBIDDEN, "비활성화된 계정입니다."), // 비활성화된 계정인 경우

    // 404: 리소스를 찾을 수 없음 (Not Found)
    NOT_FOUND_MEMBER(40401, HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    NOT_FOUND_COMMENT(40402, HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    NOT_FOUND_REPLY(40403, HttpStatus.NOT_FOUND, "해당 대댓글이 존재하지 않습니다"),
    NOT_FOUND_POST(40404, HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다"),
    NOT_FOUND_APPLY(40405, HttpStatus.NOT_FOUND, "해당 신청이 존재하지 않습니다"),
    NOT_FOUND_MATCHING(40406, HttpStatus.NOT_FOUND, "해당 매칭이 존재하지 않습니다"),

    // 429: 요청 과다 (Too Many Requests)
    TOO_MANY_REQUESTS(42900, HttpStatus.TOO_MANY_REQUESTS, "요청 횟수가 너무 많습니다. 잠시 후 다시 시도해 주세요."),

    // 500: 서버 내부 오류 (Internal Server Error)
    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
