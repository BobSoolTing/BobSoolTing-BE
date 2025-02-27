package bst.bobsoolting.member.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MemberGender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String memberGender;

    MemberGender(String memberGender) { this.memberGender = memberGender; }

    @JsonValue
    public String getMemberGender() {
        return memberGender;
    }
}
