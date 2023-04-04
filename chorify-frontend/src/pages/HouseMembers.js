import React from 'react'
import { NavLink, useLoaderData } from 'react-router-dom';
import HouseMemberCard from '../components/HouseMemberCard';
import './HouseMembers.css'

function HouseMembers() {

  const members = useLoaderData();

  return (
    <>
      <div className="button__div">
        <NavLink to="newMember"><button className="new__member__creation__button">New Member</button></NavLink>
        <NavLink><button className="view__members__button">View Members</button></NavLink>
      </div>
      <ul>
      {members.map(member => 
        <li className="house__member__card" style={{listStyleType: "none"}} key={member.id}>
          <HouseMemberCard 
            id={member.id}
            name={member.name} 
            email={member.emailAddress} 
            phoneNumber={member.phoneNumber}>
          </HouseMemberCard>
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
