import React from 'react';
import { useSelector } from 'react-redux';
import { useRouter } from 'next/router';
import Link from 'next/link';

import { HeaderNav, HeaderMenu, HeaderMenuGroup, HomepageLink } from './Nav.styled';

const Nav = () => {
  const { isSignedIn } = useSelector(state => state.user.user);
  const router = useRouter();

  const getSecondMenu = () => {
    if (['/signin', '/join', '/settings'].includes(router.pathname)) {
      return;
    }

    if (isSignedIn) {
      return (
        <HeaderMenu _float='right'>
          <Link href='/settings?menu=profile' as='/settings/profile' prefetch={false}>
            <a>마이페이지</a>
          </Link>
        </HeaderMenu>
      );
    }

    return (
      <HeaderMenu _float='right'>
        <HeaderMenuGroup>
          <Link href='/signin' prefetch={false}>
            <a>로그인</a>
          </Link>
          <span>|</span>
          <Link href='/join' prefetch={false}>
            <a>회원가입</a>
          </Link>
        </HeaderMenuGroup>
      </HeaderMenu>
    );
  };

  return (
    <>
      <HeaderNav>
        <ul className='container'>
          <HeaderMenu>
            <Link href='/' prefetch={false}>
              <HomepageLink>
                <i className='material-icons'>share</i>
                <span>PublicShare</span>
              </HomepageLink>
            </Link>
          </HeaderMenu>
          {getSecondMenu()}
        </ul>
      </HeaderNav>
    </>
  );
};

export default Nav;
