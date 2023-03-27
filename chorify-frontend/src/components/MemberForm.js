import React from 'react'
import { Form, redirect, useActionData } from 'react-router-dom' 
import './MemberForm.css'

function MemberForm({method, event}) {

    const data = useActionData();
  return (
    <Form action="/members/newMember" method={method}>

        <label>Name</label>
        <input id="name" type="text" name="name" />

        <label>Email</label>
        <input id="email" type="text" name="email" />

        <label>Phone Number</label>
        <input id="phoneNumber" type="text" name="phoneNumber" />

        <label>House ID</label>
        <input id="houseID" type="number" name="houseID" />

        <button type="submit">Submit</button>

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
      });;




    // if(response.status === 201) {
    //     return redirect("/members")
    // }

    // if(response.status !== 201) {
    //     console.log(response.body);
    //     return {error: response.body.message}
    // }
    
    
}
