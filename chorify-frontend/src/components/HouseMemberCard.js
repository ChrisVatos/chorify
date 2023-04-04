import React from 'react'
import './HouseMemberCard.css'

function HouseMemberCard(props) {
  return (
    <div>     
        <div className="main__houseMember__card">
            <div className="houseMember__ID__section">
                <h3>ID</h3>
                <h4>{props.id}</h4>
            </div>

            <div className="houseMember__name__section">
                <h3>Name</h3>
                <h4>{props.name}</h4>
            </div>

            <div className="houseMember__email__section">
                <h3>Email</h3>
                <h4>{props.email}</h4>
            </div>

            <div className="houseMember__phoneNumber__section">
                <h3>Phone Number</h3>
                <h4>{props.phoneNumber}</h4>
            </div>
        </div>
    </div>
  )
}

export default HouseMemberCard;
