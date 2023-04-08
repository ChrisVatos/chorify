import React from 'react'
import { Form, redirect, useActionData, json } from 'react-router-dom' 
import './MemberForm.css'

function MemberForm({method, event, title, isForEdit, onClose, currentData}) {

  const data = useActionData();

  return (
    <Form className="new__member__form" action="/members/newMember" method={method}>
        <div className="member__form">

            <h2 className="member__form__title">{title}</h2>

            <div className="user__inputs">

                {isForEdit &&
                <div className="id__input">
                    {isForEdit && <label><strong>Id</strong></label>}
                    <input id="id" type="number" name="id" placeholder="Id" defaultValue={currentData !== null ? currentData.currentID: ""} readonly="readonly"/>
                </div>
                }

                <div className="name__input">
                    {isForEdit && <label><strong>Name</strong></label>}
                    <input id="name" type="text" name="name" placeholder="Name" defaultValue={currentData !== null ? currentData.currentName: ""}/>
                </div>

                <div className="email__input">
                    {isForEdit && <label><strong>Email</strong></label>}   
                    <input id="email" type="text" name="email" placeholder="Email" defaultValue={currentData !== null ? currentData.currentEmail: ""}/>
                </div>

                <div className="phoneNumber__input">
                    {isForEdit && <label><strong>Phone Number</strong></label>}
                    <input id="phoneNumber" type="text" name="phoneNumber" placeholder="Phone Number" defaultValue={currentData !== null ? currentData.currentNumber: ""}/>
                </div>

                {currentData === null &&
                    <div className="houseID__input">
                        <input id="houseID" type="number" name="houseID" placeholder="HouseID"/>
                    </div>
                }       

                {data && data.errorMessage && <p className="error__message">{data.errorMessage}</p>}
            </div>

            <div className="memberForm__buttons">
                {isForEdit && <button className="cancel__member__edit" onClick={onClose}>Cancel</button>}
                <button className="member__form__button" type="submit">{isForEdit ? "Save": "Add Member"}</button>
            </div>

        </div>
    </Form>
  );
}

export default MemberForm;

export async function action({request, params}) {
    
    const method = request.method;

    const data = await request.formData();

    let url = 'http://localhost:8080/members/';

    let fetchRequestData = {};

    let editMemberData = {
        id: data.get('id'),
        name: data.get('name'),
        phoneNumber: data.get('phoneNumber'),
        emailAddress: data.get('email'),
    }

    let newMemberData = {
        id: data.get('id'),
        name: data.get('name'),
        phoneNumber: data.get('phoneNumber'),
        emailAddress: data.get('email'),
        houseID: data.get('houseID')
    }

    if(method === "POST") {
        url = url + "newMember";
        fetchRequestData = newMemberData;
    } else {
        url = url + "update/" + editMemberData.id;
        fetchRequestData = editMemberData;
    }

    const response = await fetch(url, {
          method: method,
          body: JSON.stringify(fetchRequestData),
          headers: {
              'Content-Type': 'application/json'
          }
      });

      if (!response.ok) {
        const error = await response.json();
        return ({errorMessage: error.message, status: error.status});
      } else {
        return redirect('/members')
      }
}
