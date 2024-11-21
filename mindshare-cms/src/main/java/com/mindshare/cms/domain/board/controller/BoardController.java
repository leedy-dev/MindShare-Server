package com.mindshare.cms.domain.board.controller;

import com.mindshare.cms.domain.board.service.BoardService;
import com.mindshare.cms.domain.board.service.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cms/api/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{id}")
    public ResponseEntity<BoardDto.Response> doGetBoardById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(boardService.getBoardById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<BoardDto.Response>> doGetBoardPage(BoardDto.Search search, Pageable pageable) {
        return new ResponseEntity<>(boardService.getBoardList(search, pageable), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BoardDto.Response> doCreateBoard(
            @Validated @RequestBody BoardDto.Request boardDto,
            BindingResult bindingResult) {
        return new ResponseEntity<>(boardService.createBoard(boardDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDto.Response> doUpdateBoard(
            @PathVariable("id") Long id,
            @Validated @RequestBody BoardDto.Request boardDto,
            BindingResult bindingResult) {
        return new ResponseEntity<>(boardService.updateBoard(id, boardDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> doDeleteBoardById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(boardService.deleteBoardById(id), HttpStatus.OK);
    }

}
