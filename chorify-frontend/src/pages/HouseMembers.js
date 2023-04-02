import React from 'react'
import { NavLink, useLoaderData } from 'react-router-dom';

function HouseMembers() {

  const members = useLoaderData();

  return (
    <>
      <div>
        <NavLink to="newMember"><button>New Member</button></NavLink>
      </div>
      <h1>House Members</h1>
      <ul>
      {members.map(member => 
        <li>
          <h4>{member.name}</h4>
        </li>)}
      </ul>
    </>
  );
}

export default HouseMembers;

export async function loader() {
  
  const response = await fetch('http://localhost:8080/members/house/280');

  return response;

  console.log(response);
}
