package com.mindshare.cms.domain.board.service.impl;

import com.mindshare.cms.domain.board.entity.Board;
import com.mindshare.cms.domain.board.repository.BoardRepository;
import com.mindshare.cms.domain.board.service.BoardService;
import com.mindshare.cms.domain.board.service.dto.BoardDto;
import com.mindshare.core.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    private Board getBoardEntityById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new ApiException("Board Not Found"));
    }

    @Override
    public BoardDto.Response getBoardById(Long id) {
        Board board = getBoardEntityById(id);

        return modelMapper.map(board, BoardDto.Response.class);
    }

    @Override
    public Page<BoardDto.Response> getBoardList(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);

        return new PageImpl<>(boardPage.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDto.Response.class))
                .collect(Collectors.toList()),
                boardPage.getPageable(),
                boardPage.getTotalElements());
    }

    @Override
    @Transactional
    public BoardDto.Response createBoard(BoardDto.Request boardRequestDto) {
        // set
        Board board = modelMapper.map(boardRequestDto, Board.class);

        // save
        board = boardRepository.save(board);

        // response
        return modelMapper.map(board, BoardDto.Response.class);
    }

    @Override
    @Transactional
    public BoardDto.Response updateBoard(Long id, BoardDto.Request boardRequestDto) {
        // get
        Board board = getBoardEntityById(id);

        // set
        modelMapper.map(boardRequestDto, board);

        // response
        return modelMapper.map(board, BoardDto.Response.class);
    }

    @Override
    @Transactional
    public Long deleteBoardById(Long id) {
        boardRepository.deleteById(id);
        return id;
    }
}
