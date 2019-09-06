import React from 'react';
import Link from 'next/link';

import { Card, CardBody } from './index.styled';

function BookCard({ data }) {
  return (
    <Link
      href={{ pathname: '/book', query: { id: data.id } }}
      as={`/book/${data.id}`}
      prefetch={false}
    >
      <a>
        <Card>
          <img src='https://placeimg.com/350/400/animals' />
          <CardBody>
            <h2 className='card-title'>{data.title}</h2>
            <p className='description'>{`${data.author} | ${data.publisher}`}</p>
            <span>{data.createdAt}</span>
          </CardBody>
        </Card>
      </a>
    </Link>
  );
}

export default BookCard;