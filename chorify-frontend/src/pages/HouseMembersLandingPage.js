import React from 'react'
import { NavLink } from 'react-router-dom'
import './HouseMembersLandingPage.css'

function HouseMembersLandingPage() {
  return (
    <>
     <div className="button__div">
        <NavLink to="newMember"><button className="new__member__creation__button">New Member</button></NavLink>
        <NavLink to="viewMembers"><button className="view__members__button">View Members</button></NavLink>

        <h1 className="houseMember__landingPage__description">Please select one of the above options to create a new house member or update an existing one.</h1>
      </div>
    </>
  )
}

export default HouseMembersLandingPage;
