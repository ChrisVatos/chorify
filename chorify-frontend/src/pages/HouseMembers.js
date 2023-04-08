import { React, useState, useEffect } from 'react'
import { NavLink, useLoaderData} from 'react-router-dom';
import HouseMemberCard from '../components/HouseMemberCard';
import './HouseMembers.css'

function HouseMembers() {

  const [members, setMembers] = useState([]);
  const memberData = useLoaderData();

  useEffect(() => {
    setMembers(memberData);
  }, []);

  const handleDeletion = async (idOfMemberToDelete) => {
    
    const response = await fetch('http://localhost:8080/members/delete/' + idOfMemberToDelete, {
        method: 'DELETE', // Set the HTTP method to DELETE
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if(!response.ok) {
        const error = await response.json();
        return ({errorMessage: error.message, status: error.status});
    } else {
      // Handle successful deletion, e.g., fetch updated member data
      // and update state in parent component
      const updatedMembers = members.filter(member => member.id != idOfMemberToDelete);
      setMembers(updatedMembers); // Update members state with updated data
    }
}

  return (
    <>
      <h1 className="view__houseMembers__heading">House Members</h1>
      {members && members.errorMessage && <h3 className="error__message__on__houseMembers__page">{members.errorMessage}</h3> }
      <ul>
      {!members.errorMessage && members.map(member => 
        <li className="house__member__card" style={{listStyleType: "none"}} key={member.id}>
          <HouseMemberCard 
            id={member.id}
            name={member.name} 
            email={member.emailAddress} 
            phoneNumber={member.phoneNumber}
            onDelete={handleDeletion}>
          </HouseMemberCard>
        </li>)}
      </ul>
    </>
  );
}

export default HouseMembers;

export async function loader() {
  
  const response = await fetch('http://localhost:8080/members/house/280');

  if (!response.ok) {
    const error = await response.json();
    return ({errorMessage: error.message, status: error.status});
  } 

  return response;
}
