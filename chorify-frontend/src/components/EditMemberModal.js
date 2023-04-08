import React from "react";
import MemberForm from "../components/MemberForm";
import  ReactDom  from "react-dom";
import "./EditMemberModal.css";

function EditMemberModal(props) {
  
  if (!props.open) {
    return null;
  }

  return ReactDom.createPortal(
    <>
        <div className="editMember__overlay__styles">
            <MemberForm method="PUT" 
                        title="Edit House Member"
                        isForEdit={true}
                        currentData={props.currentData}
                        onClose={props.onClose}
            />
        </div>
    </>, document.getElementById("editMember__modal")
  );
}

export default EditMemberModal;
