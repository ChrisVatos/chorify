import { React, useState } from 'react'
import { Form, redirect, useActionData } from 'react-router-dom' 
import './MemberForm.css'

function MemberForm({method, event, title, isForEdit, onClose, currentData}) {

    const data = useActionData();

    let [nameInput, setNameInput] = useState("");
    let [nameInputTouched, setNameInputTouched] = useState(false);
    const isNameInputValid = (name) => name.length > 6;
    const isNameInvalid = !isNameInputValid(nameInput) && nameInputTouched;

    let [emailInput, setEmailInput] = useState("");
    let [emailInputTouched, setEmailInputTouched] = useState(false);
    const emailRegex = /[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+(?:\.[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?/i
    const isEmailInputValid = (email) => emailRegex.test(email);
    const isEmailInvalid = !isEmailInputValid(emailInput) && emailInputTouched;

    let [numberInput, setNumberInput] = useState("");
    let [numberInputTouched, setNumberInputTouched] = useState(false);
    const numberRegex = /^\d{3}-\d{3}-\d{4}$/
    const isNumberInputValid = (number) => numberRegex.test(number);
    const isNumberInvalid = !isNumberInputValid(numberInput) && numberInputTouched;

    let [houseIDInput, setHouseIDInput] = useState("");
    let [houseIdInputTouched, setHouseIdInputTouched] = useState(false);
    const isHouseIdInputValid = (id) => parseInt(id) > 0;
    const isHouseIdInvalid = !isHouseIdInputValid(houseIDInput) && houseIdInputTouched;

    let formIsValid = false;
  

    if(isNameInputValid(nameInput) && isEmailInputValid(emailInput) & isNumberInputValid(numberInput) && isHouseIdInputValid(houseIDInput)) {

        formIsValid = true;
    }

    function nameOnChangeHandler(event) {
        setNameInput(event.target.value);
    }

    function nameOnBlurHandler() {
        setNameInputTouched(true);
    }

    function emailOnChangeHandler(event) {
        setEmailInput(event.target.value);
    }

    function emailOnBlurHandler() {
        setEmailInputTouched(true);
    }

    function numberOnChangeHandler(event) {
        setNumberInput(event.target.value);
    }

    function numberOnBlurHandler() {
        setNumberInputTouched(true);
    }

    function houseIdOnChangeHandler(event) {
        setHouseIDInput(event.target.value);
    }

    function houseIdOnBlurHandler() {
        setHouseIdInputTouched(true);
    }

  return (
    <Form className="new__member__form" action="/members/newMember" method={method}>
        <div className="member__form">

            <h2 className="member__form__title">{title}</h2>

            <div className="user__inputs">

                {isForEdit &&
                <div className="id__input">
                    {isForEdit && <label><strong>Id</strong></label>}
                    <input 
                        id="id" 
                        type="number" 
                        name="id" 
                        placeholder="Id" 
                        defaultValue={currentData !== null ? currentData.currentID: ""} 
                        readonly="readonly"
                    />
                </div>
                }

                <div className="name__input">
                    {isForEdit && <label><strong>Name</strong></label>}
                    <input 
                        id="name" 
                        type="text" 
                        name="name" 
                        placeholder="Name" 
                        defaultValue={currentData !== null ? currentData.currentName: ""}
                        onChange={nameOnChangeHandler}
                        onBlur={nameOnBlurHandler}
                        className={isNameInvalid ? "invalid__input" : ""}
                    />
                    {isNameInvalid && <p className="input__correction">Name must be longer than 6 characters.</p>}
                </div>

                <div className="email__input">
                    {isForEdit && <label><strong>Email</strong></label>}   
                    <input 
                        id="email" 
                        type="text" 
                        name="email" 
                        placeholder="Email" 
                        defaultValue={currentData !== null ? currentData.currentEmail: ""}
                        onChange={emailOnChangeHandler}
                        onBlur={emailOnBlurHandler}
                        className={isEmailInvalid ? "invalid__input" : ""}
                    />
                    {isEmailInvalid && <p className="input__correction">Email must be of the form: [a-zA-Z]@[a-zA-Z].com</p>}

                </div>

                <div className="phoneNumber__input">
                    {isForEdit && <label><strong>Phone Number</strong></label>}
                    <input 
                        id="phoneNumber" 
                        type="text" 
                        name="phoneNumber" 
                        placeholder="Phone Number" 
                        defaultValue={currentData !== null ? currentData.currentNumber: ""}
                        onChange={numberOnChangeHandler}
                        onBlur={numberOnBlurHandler}
                        className={isNumberInvalid ? "invalid__input" : ""}
                    />
                    {isNumberInvalid && <p className="input__correction">Number must be in the form: ###-###-####</p>}

                </div>

                {currentData === null &&
                    <div className="houseID__input">
                        <input 
                            id="houseID" 
                            type="number" 
                            name="houseID" 
                            placeholder="HouseID"
                            onChange={houseIdOnChangeHandler}
                            onBlur={houseIdOnBlurHandler}
                            className={isHouseIdInvalid ? "invalid__input" : ""}
                        />
                    {isHouseIdInvalid && <p className="input__correction">House ID must be a number larger than 0</p>}

                    </div>
                }       

                {data && data.errorMessage && <p className="error__message">{data.errorMessage}</p>}
            </div>

            <div className="memberForm__buttons">
                {isForEdit && <button className="cancel__member__edit" onClick={onClose}>Cancel</button>}
                <button 
                    className="member__form__button" type="submit"
                    disabled={!formIsValid}
                >
                        {isForEdit ? "Save": "Add Member"}
                </button>
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
