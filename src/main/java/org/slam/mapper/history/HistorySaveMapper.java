package org.slam.mapper.history;

import org.apache.ibatis.annotations.Insert;
import org.slam.dto.book.BookHistory;

public interface HistorySaveMapper {

    @Insert("INSERT INTO BOOK_HISTORY(BOOK_ID, REQUESTED_STATUS, REQUESTED_USER) VALUES(#{bookId}, #{requestedStatus}, #{modifiedBy})")
    int insertHistory(BookHistory history);

}
