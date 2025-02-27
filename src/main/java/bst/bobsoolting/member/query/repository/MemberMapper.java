package bst.bobsoolting.member.query.repository;

import bst.bobsoolting.member.command.domain.aggregate.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    Member findByKakaoId(@Param("kakaoId") String kakaoId);
}
