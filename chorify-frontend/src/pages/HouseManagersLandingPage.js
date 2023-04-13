import React from 'react'
import { NavLink, useLoaderData } from 'react-router-dom'
import './HouseManagersLandingPage.css'
import ManagerForm from '../components/ManagerForm'
import HouseManagerCard from '../components/HouseManagerCard';

function HouseManagersLandingPage() {

    const managers = useLoaderData();

  return (
    <div className="house__manager__landing__page">
        <ManagerForm method="POST" title="Create New House Manager" isForEdit={false} currentData={null}/>

        <div className="house__manager__list">
            {!managers.errorMessage && managers.map(manager => 
                <li className="house__manager__card" style={{listStyleType: "none"}} key={manager.id}>
                    <HouseManagerCard 
                    id={manager.id}
                    name={manager.name} 
                    email={manager.emailAddress} 
                    phoneNumber={manager.phoneNumber}
                    type={manager.houseManagerType}>
                    </HouseManagerCard>
                </li>)}
       </div>
    </div>
  )
}

export default HouseManagersLandingPage;

export async function loader() {
  
    const response = await fetch('http://localhost:8080/managers/house/280');
    console.log(response);
  
    if (!response.ok) {
      const error = await response.json();
      return ({errorMessage: error.message, status: error.status});
    } 
  
    return response;
}