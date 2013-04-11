package com.francis.epril.board;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../spring-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BoardTest {

	@Resource
	private BoardService boardService;

	private Board board1;
	private Board board2;

	// ~ Method

	@Before
	public void BEFORE_TEST() throws Exception {
		board1 = new Board(null, "제목", "내용", null, null, null);
		board2 = new Board(null, "제목2", "내용2", null, null, null);
	}

	@Test
	public void 공지사항_입력() throws Exception {
		boardService.deleteBoard(new Board());
		int count = boardService.selectNoticeCount(new BoardFilter());
		assertThat(count, is(0));

		boardService.insertNoticeWrite(board1);
		Board loadBoard = boardService.selectNoticeContent(board1);

		confirmBoardAndLoadBoard(board1, loadBoard);
	}

	@Test
	public void 공지사항_수정() throws Exception {
		boardService.deleteBoard(new Board());
		int count = boardService.selectNoticeCount(new BoardFilter());
		assertThat(count, is(0));

		boardService.insertNoticeWrite(board1);
		boardService.insertNoticeWrite(board2);

		board1.setContent("내용수정");
		board1.setTitle("제목수정");

		boardService.updateNotice(board1);

		Board loadBoard;

		loadBoard = boardService.selectNoticeContent(board1);
		confirmBoardAndLoadBoard(board1, loadBoard);

		loadBoard = boardService.selectNoticeContent(board2);
		confirmBoardAndLoadBoard(board2, loadBoard);
	}

	@Test
	public void 공지사항_삭제() throws Exception {
		boardService.deleteBoard(new Board());
		int count = boardService.selectNoticeCount(new BoardFilter());
		assertThat(count, is(0));

		boardService.insertNoticeWrite(board1);
		boardService.insertNoticeWrite(board2);

		boardService.deleteBoard(board1);
		count = boardService.selectNoticeCount(new BoardFilter());
		assertThat(count, is(1));

		Board loadBoard = boardService.selectNoticeContent(board2);
		confirmBoardAndLoadBoard(board2, loadBoard);
	}

	@Test
	public void 공지사항_페이징_처리() throws Exception {
		boardService.deleteBoard(new Board());
		int count = boardService.selectNoticeCount(new BoardFilter());
		assertThat(count, is(0));

		for (int i = 0; i < 14; i++) {
			boardService.insertNoticeWrite(board1);
		}
		BoardFilter boardFilter = new BoardFilter();

		boardFilter.setPage(1);
		List<Board> boards = boardService.selectNoticeList(boardFilter);
		assertThat(boards.size(), is(10));

		boardFilter.setPage(2);
		boards = boardService.selectNoticeList(boardFilter);
		assertThat(boards.size(), is(4));

		boardFilter.setPage(3);
		boards = boardService.selectNoticeList(boardFilter);
		assertThat(boards.size(), is(0));
	}

	@Test
	public void 조회수_증가() {
		boardService.updateReadCount(1);

		Board board = boardService.selectNoticeContent(new Board(1, null, null,
				null, null, null));

		assertThat(board.getReadCount(), is(1));
	}

	private void confirmBoardAndLoadBoard(Board board, Board loadBoard) {
		assertThat(board.getTitle(), is(loadBoard.getTitle()));
		assertThat(board.getContent(), is(loadBoard.getContent()));
	}

}
