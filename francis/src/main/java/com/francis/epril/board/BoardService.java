package com.francis.epril.board;

import java.util.List;

public interface BoardService {

	void insertNoticeWrite(Board board);

	void updateNotice(Board board);

	void deleteBoard(Board board);

	Board selectNoticeContent(Board board);

	List<Board> selectNoticeList(BoardFilter boardFilter);

	Integer selectNoticeCount(BoardFilter boardFilter);

	void updateReadCount(Integer pn);
}
