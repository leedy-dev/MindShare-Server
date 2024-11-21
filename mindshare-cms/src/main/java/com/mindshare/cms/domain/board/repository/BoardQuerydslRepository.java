package com.mindshare.cms.domain.board.repository;

import com.mindshare.cms.domain.board.entity.Board;
import com.mindshare.cms.domain.board.entity.QBoard;
import com.mindshare.cms.domain.board.service.dto.BoardDto;
import com.mindshare.core.common.utils.CommonObjectUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Board> findById(Long id) {
        QBoard board = QBoard.board;

        return Optional.ofNullable(jpaQueryFactory.selectFrom(board)
                .where(board.id.eq(id))
                .fetchOne());
    }

    /**
     * 게시글 목록 조회
     * 검색 및 페이징
     */
    public Page<Board> findWithPaging(BoardDto.Search search, Pageable pageable) {
        QBoard board = QBoard.board;

        List<Board> boardList = jpaQueryFactory.selectFrom(board)
                .where(getSearch(board, search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = Optional.ofNullable(
                jpaQueryFactory.select(board.count())
                        .from(board)
                        .where(getSearch(board, search))
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(boardList, pageable, count);
    }

    /**
     * 검색용
     */
    private BooleanBuilder getSearch(QBoard board, BoardDto.Search search) {
        BooleanBuilder builder = new BooleanBuilder();

        if (CommonObjectUtils.isNotEmpty(search.getTitle())) {
            builder.and(board.title.like("%" + search.getTitle() + "%"));
        }

        return builder;
    }

}
