import React from 'react'
import { Form, redirect, useActionData } from 'react-router-dom' 
import './MemberForm.css'

function MemberForm({method, event}) {

    const data = useActionData();
  return (
    <Form className="test" action="/members/newMember" method={method}>
        <div className="member__form">

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

            <button className="member__form__button" type="submit">Add Member</button>
        </div>

        {data && data.error && <p>{data.error}</p>}
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

    fetch(url, {
        method: "POST",
        body: JSON.stringify(memberData),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
          // handle successful response
          return response.json();
        } else {
          // handle error response
          return response.json().then(error => {
            throw new Error(error.message);
          });
        }
      })
      .then(data => {
        // handle successful data
      })
      .catch(error => {
        // handle error
        console.error('Error:', error.message);
        // display error message to user
      });    
}
