package com.mindshare.cms.domain.board.service;

import com.mindshare.cms.domain.board.service.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    BoardDto.Response getBoardById(Long id);

    Page<BoardDto.Response> getBoardList(BoardDto.Search search, Pageable pageable);

    BoardDto.Response createBoard(BoardDto.Request boardRequestDto);

    BoardDto.Response updateBoard(Long id, BoardDto.Request boardRequestDto);

    Long deleteBoardById(Long id);

}
