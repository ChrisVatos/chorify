import React from "react";
import { useRef, useState } from 'react'
import "./HouseMemberCard.css";
import EditMemberModal from './EditMemberModal'

function HouseMemberCard(props) {

    const [isEditMemberModalVisible, setIsEditMemberModalVisible] = useState(false);

    const memberIDToDelete = useRef(null);

    async function deleteMember() {
        const idToDelete = memberIDToDelete.current.innerText;
        props.onDelete(idToDelete);
    }

  return (
    <div>
      <div className="main__houseMember__card">

        <EditMemberModal  
                onClose={() => setIsEditMemberModalVisible(false)}
                open={isEditMemberModalVisible}
                currentData={{
                    currentName: props.name, 
                    currentEmail: props.email,
                    currentNumber: props.phoneNumber, 
                    currentID: props.id
                }}>
        </EditMemberModal>

        <div className="houseMember__ID__section">
          <h3>ID</h3>
          <h4 ref={memberIDToDelete}>{props.id}</h4>
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

        <button className="deleteMember__button" onClick={deleteMember}>&#x2716;</button>
        <button className="editMember__button" onClick={() => setIsEditMemberModalVisible(true)}> &#9998;</button>
      </div>
    </div>
  );
}

export default HouseMemberCard;
