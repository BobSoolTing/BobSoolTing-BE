package bst.bobsoolting.comment.query.service;

import bst.bobsoolting.comment.command.application.mapper.CommentConverter;
import bst.bobsoolting.comment.command.domain.vo.response.ResponseCommentVO;
import bst.bobsoolting.comment.command.domain.vo.response.ResponseCommentWithRepliesVO;
import bst.bobsoolting.comment.query.repository.CommentMapper;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentConverter commentConverter;
    private final CommentMapper commentMapper;

    @Override
    public List<ResponseCommentWithRepliesVO> getCommentsByPost(Long postId, Long cursor, int size) {
        log.info("댓글 + 대댓글 조회 - postId: {}, cursor: {}, size: {}", postId, cursor, size);

        try (com.github.pagehelper.Page<?> ignored = PageHelper.startPage(1, size)) {
            return commentMapper.findCommentsByPost(postId, cursor).stream()
                    .map(comment -> ResponseCommentWithRepliesVO.builder()
                            .comment(commentConverter.fromEntityToResponseCommentVO(comment))
                            .replies(commentMapper.findRepliesByCommentId(comment.getCommentId())
                                    .stream()
                                    .map(commentConverter::fromEntityToResponseReplyVO)
                                    .collect(Collectors.toList()))
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
