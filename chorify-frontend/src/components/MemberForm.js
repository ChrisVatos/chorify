import React from 'react'
import { Form, redirect, useActionData, json } from 'react-router-dom' 
import './MemberForm.css'

function MemberForm({method, event, title}) {

  const data = useActionData();

  return (
    <Form className="new__member__form" action="/members/newMember" method={method}>
        <div className="member__form">

            <h2 className="member__form__title">{title}</h2>

            <div className="user__inputs">
                <div className="name__input">
                    <input id="name" type="text" name="name" placeholder="Name"/>
                </div>

                <div className="email__input">
                    <input id="email" type="text" name="email" placeholder="Email"/>
                </div>

                <div className="phoneNumber__input">
                    <input id="phoneNumber" type="text" name="phoneNumber" placeholder="Phone Number"/>
                </div>

                <div className="houseID__input">
                    <input id="houseID" type="number" name="houseID" placeholder="HouseID"/>
                </div>

                {data && data.errorMessage && <p className="error__message">{data.errorMessage}</p>}
            </div>

            <button className="member__form__button" type="submit">Add Member</button>

        </div>
    </Form>
  );
}

export default MemberForm;

export async function action({request, params}) {
    
    const method = request.method;

    const data = await request.formData();

    let url = 'http://localhost:8080/members/newMember';

    const memberData = {
        name: data.get('name'),
        phoneNumber: data.get('phoneNumber'),
        emailAddress: data.get('email'),
        houseID: data.get('houseID')
    }

    const response = await fetch(url, {
          method: "POST",
          body: JSON.stringify(memberData),
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
