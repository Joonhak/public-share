import React from 'react';
import Link from 'next/link';
import { useDispatch, useSelector } from 'react-redux';

import { BOOK_STATUS, LENDING_STATUS } from '@utils/consts';
import { borrowBookRequest, returnBookRequest } from '@redux/actions/lendingActions';

import { Button } from '@styles/common';

const LendingButton = ({ detail, lending }) => {
  const user = useSelector(state => state.user.user.data);
  const dispatch = useDispatch();

  // 로그인하지 않은 경우
  if (!user.id) {
    return (
      <Link href={{ pathname: '/signin' }} prefetch={false}>
        <Button>로그인하기</Button>
      </Link>
    );
  }

  // 도서의 주인이 현재 로그인한 사용자인 경우
  if (detail.owner === user.name) {
    /**
     * TODO:
     * 1. if detail.status === UNAVAILABLE
     *   show ACCEPT, REJECT or RETURN button
     * 2. if detail.status === AVAILABLE
     *   show HISTORY button
     */
    return null;
  }

  const handleRent = () => {
    dispatch(borrowBookRequest(lending.book.id));
  };

  const handleCancelBorrow = () => {
    console.log(lending.id);
    // dispatch(cancelBorrowRequest(lending.id));
  };

  const handleReturnBook = () => {
    console.log(lending.id);
    dispatch(returnBookRequest(lending.id));
  };

  // 현재 도서가 대여 가능한 도서인 경우
  if (detail.status === BOOK_STATUS.AVAILABLE) {
    return (
      <Button _color='primary' onClick={handleRent}>
        대여신청
      </Button>
    );
  }

  // 현재 대여 (또는 대여 요청) 중인 회원이 현재 로그인한 회원인 경우
  if (lending.borrowerId === user.id) {
    /**
     * 1. if lending status === REQUESTED
     *   CANCEL button
     * 2. if lending status === ACCEPTED
     *   RETURN button
     */
    if (lending.currentStatus === LENDING_STATUS.REQUESTED) {
      return (
          <Button _color='red' onClick={handleCancelBorrow}>신청취소</Button>
      )
    } else if (lending.currentStatus === LENDING_STATUS.ACCEPTED) {
      return (
          <Button _color='secondary' onClick={handleReturnBook}>반납하기</Button>
      )
    }
  }

  // 대여할 수 없는 경우
  return (
    <Button _color='gray' disabled>
      대여불가
    </Button>
  );
};

export default LendingButton;
