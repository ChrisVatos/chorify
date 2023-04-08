import React from 'react'
import MemberForm from '../components/MemberForm'

function NewMemberPage() {
  return (
    <MemberForm method="POST" title="Create New House Member" isForEdit={false} currentData={null}/>  
  )
}

export default NewMemberPage;
