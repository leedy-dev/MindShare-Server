package com.mindshare.cms.domain.board.service.impl;

import com.mindshare.cmm.common.components.LoginUserComponent;
import com.mindshare.cmm.common.components.RedisLockComponent;
import com.mindshare.cmm.common.exception.ApiException;
import com.mindshare.cmm.common.redis.service.RedisStoreService;
import com.mindshare.cms.common.exception.CmsErrorMessage;
import com.mindshare.cms.domain.board.entity.Board;
import com.mindshare.cms.domain.board.repository.BoardQuerydslRepository;
import com.mindshare.cms.domain.board.repository.BoardRepository;
import com.mindshare.cms.domain.board.service.BoardService;
import com.mindshare.cms.domain.board.service.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    public static final long REDIS_VIEW_TTL = Duration.ofHours(1).getSeconds();
    public static final long REDIS_LOCK_TTL = Duration.ofSeconds(10).getSeconds();

    private final BoardQuerydslRepository boardQuerydslRepository;
    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;
    private final LoginUserComponent loginUserComponent;
    private final RedisStoreService redisStoreService;
    private final RedisLockComponent redisLockComponent;

    // lock 대기 큐 생성
    private final BlockingQueue<Long> lockWaitingQueue = new LinkedBlockingQueue<>();

    private Board getBoardEntityById(Long id) {
        return boardQuerydslRepository.findById(id)
                .orElseThrow(() -> new ApiException(CmsErrorMessage.BOARD_NOT_FOUND));
    }

    @Override
    @Transactional
    public BoardDto.Response getBoardById(Long id) {
        Board board = getBoardEntityById(id);

        // 비동기 redis에 값 없을 경우 값 증가 및 redis 저장
        asyncIncrementViewCount(id, board);

        return modelMapper.map(board, BoardDto.Response.class);
    }

    @Override
    public Page<BoardDto.Response> getBoardList(BoardDto.Search search, Pageable pageable) {
        Page<Board> boardPage = boardQuerydslRepository.findWithPaging(search, pageable);

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

    /**
     * redis key
     * post:view:게시글ID:uid
     */
    private String generateViewRedisKey(Long id) {
        return "board:view:" + id + ":" + loginUserComponent.getCurrentLoginUser();
    }

    /**
     * lock key
     * board:lock:게시글ID
     */
    private String generateLockKey(Long id) {
        return "board:lock:" + id;
    }

    /**
     * 비동기
     * redis에 값 없을 경우 값 증가 및 redis 저장
     */
    @Async
    public void asyncIncrementViewCount(Long id, Board board) {
        String lockKey = generateLockKey(id);

        // 락 획득
        String lockId = redisLockComponent.tryLock(lockKey, REDIS_LOCK_TTL);

        // 락 실패 시
        if (lockId == null) {
            lockWaitingQueue.offer(id); // 요청을 대기 큐에 추가
            return;
        }

        try {
            // redis key 생성
            String redisKey = generateViewRedisKey(id);

            // 키 존재하지 않을 경우 증가
            if (!redisStoreService.hasKey(redisKey)) {
                // 1시간
                redisStoreService.save(redisKey, "true", REDIS_VIEW_TTL);
                // increment viewCount
                board.incrementViewCount();
            }
        } finally {
            // 락 해제
            redisLockComponent.unlock(lockKey, lockId);

            // 락 해제 후 대기 중인 요청 처리
            Long nextPostId = lockWaitingQueue.poll();
            if (nextPostId != null) {
                asyncIncrementViewCount(nextPostId, board); // 대기 중인 요청 처리
            }
        }
    }

}
