import React from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { signOutRequest } from '@redux/actions/userActions';

import { Title } from './index.styled';
import { Button } from '@styles/common';

const Profile = () => {
  const user = useSelector(state => state.user.user.data);
  const dispatch = useDispatch();

  const handleSignOut = () => {
    dispatch(signOutRequest());
  };

  return (
    <>
      <Title>내 프로필</Title>
      <Button _color='gray' onClick={handleSignOut}>
        로그아웃
      </Button>
    </>
  );
};

export default Profile;
