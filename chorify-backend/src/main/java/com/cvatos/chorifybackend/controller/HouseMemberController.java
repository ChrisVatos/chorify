package com.cvatos.chorifybackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvatos.chorifybackend.model.HouseMember;
import com.cvatos.chorifybackend.service.HouseMemberService;
import com.dto.HouseMemberRequestDto;
import com.dto.HouseMemberResponseDto;

@Controller
public class HouseMemberController {

    @Autowired
    private HouseMemberService houseMemberService;

    @PostMapping("/members/newMember")
    public ResponseEntity<HouseMemberResponseDto> createNewMember(@RequestBody HouseMemberRequestDto houseMemberRequest) {

        // Create object using data from the HouseRequestDto
        HouseMember memberToCreate = new HouseMember(
            houseMemberRequest.getName(), 
            houseMemberRequest.getPhoneNumber(), 
            houseMemberRequest.getEmailAddress()
        );

        // Call the createHouseMember() method from the HouseService to create the member and get back the created object
        HouseMember createdMember = houseMemberService.createHouseMember(memberToCreate, houseMemberRequest.getHouseID());

        // Create HouseResponseDto using data from the created object
        HouseMemberResponseDto createdMemberResponse = new HouseMemberResponseDto(
            createdMember.getId(),
            createdMember.getName(),
            createdMember.getPhoneNumber(),
            createdMember.getEmailAddress(),
            createdMember.getChores()
        );

        return new ResponseEntity<HouseMemberResponseDto>(createdMemberResponse, HttpStatus.CREATED);
    }
    
}
