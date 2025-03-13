package bst.bobsoolting.post.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RecruitmentStatus {
    RECRUITING("RECRUITING"),
    CLOSED("CLOSED");

    private final String value;

    RecruitmentStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RecruitmentStatus fromValue(String value) {
        for (RecruitmentStatus status : RecruitmentStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid recruitment status: " + value);
    }
}
