package com.cvatos.chorifybackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cvatos.chorifybackend.model.HouseMember;
import com.cvatos.chorifybackend.service.HouseMemberService;
import com.dto.HouseMemberRequestDto;
import com.dto.HouseMemberResponseDto;

@CrossOrigin(origins = "http://localhost:3000/")
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
            createdMember.getChores(),
            createdMember.getHouse().getId()
        );

        return new ResponseEntity<HouseMemberResponseDto>(createdMemberResponse, HttpStatus.CREATED);
    }

    @GetMapping("/members/id/{id}")
    public ResponseEntity<HouseMemberResponseDto> getMemberById(@PathVariable int id) {
        
        HouseMember retrievedMember = houseMemberService.getHouseMemberById(id);

        HouseMemberResponseDto retrievedMemberResponse = new HouseMemberResponseDto(
            retrievedMember.getId(),
            retrievedMember.getName(),
            retrievedMember.getPhoneNumber(),
            retrievedMember.getEmailAddress(),
            retrievedMember.getChores(),
            retrievedMember.getHouse().getId()
        );

        return new ResponseEntity<HouseMemberResponseDto>(retrievedMemberResponse, HttpStatus.OK);
    }

    @GetMapping("/members/name/{name}")
    public ResponseEntity<HouseMemberResponseDto> getMemberByName(@PathVariable String name) {
        
        HouseMember retrievedMember = houseMemberService.getHouseMemberByName(name);

        HouseMemberResponseDto retrievedMemberResponse = new HouseMemberResponseDto(
            retrievedMember.getId(),
            retrievedMember.getName(),
            retrievedMember.getPhoneNumber(),
            retrievedMember.getEmailAddress(),
            retrievedMember.getChores(),
            retrievedMember.getHouse().getId()
        );

        return new ResponseEntity<HouseMemberResponseDto>(retrievedMemberResponse, HttpStatus.OK);
    }

    @GetMapping("/members/house/{id}")
    public ResponseEntity<List<HouseMemberResponseDto>> getMembersByHouse(@PathVariable int id) {

        List<HouseMember> houseMembersByHouse = houseMemberService.getHouseMembersByHouse(id);
        List<HouseMemberResponseDto> houseMembersResponseDto = new ArrayList<HouseMemberResponseDto>();

        for(HouseMember member: houseMembersByHouse) {
            HouseMemberResponseDto memberInHouseResponse = new HouseMemberResponseDto(
                member.getId(),
                member.getName(),
                member.getPhoneNumber(),
                member.getEmailAddress(),
                member.getChores(),
                member.getHouse().getId()
            );

            houseMembersResponseDto.add(memberInHouseResponse);
        }

        return new ResponseEntity<List<HouseMemberResponseDto>>(houseMembersResponseDto, HttpStatus.OK);
    }

    @PutMapping("/members/updateName/{id}")
    public ResponseEntity<HouseMemberResponseDto> updateName(@PathVariable int id, @RequestBody HouseMemberRequestDto houseMemberRequest) {
        
        HouseMember updatedMember = houseMemberService.updateHouseMemberName(id, houseMemberRequest.getName());

        HouseMemberResponseDto updatedMemberResponse = new HouseMemberResponseDto(
            updatedMember.getId(),
            updatedMember.getName(),
            updatedMember.getPhoneNumber(),
            updatedMember.getEmailAddress(),
            updatedMember.getChores(),
            updatedMember.getHouse().getId()
        );

        return new ResponseEntity<HouseMemberResponseDto>(updatedMemberResponse, HttpStatus.OK);
    }

    @PutMapping("/members/update/{id}")
    public ResponseEntity<HouseMemberResponseDto> updateMember(@PathVariable int id, @RequestBody HouseMemberRequestDto houseMemberRequest) {
        
        HouseMember updatedMember = houseMemberService.updateHouseMemberDetails(id, houseMemberRequest.getName(), houseMemberRequest.getEmailAddress(), houseMemberRequest.getPhoneNumber());

        HouseMemberResponseDto updatedMemberResponse = new HouseMemberResponseDto(
            updatedMember.getId(),
            updatedMember.getName(),
            updatedMember.getPhoneNumber(),
            updatedMember.getEmailAddress(),
            updatedMember.getChores(),
            updatedMember.getHouse().getId()
        );

        return new ResponseEntity<HouseMemberResponseDto>(updatedMemberResponse, HttpStatus.OK);
    }

    @PutMapping("/members/updateEmail/{id}")
    public ResponseEntity<HouseMemberResponseDto> updateEmail(@PathVariable int id, @RequestBody HouseMemberRequestDto houseMemberRequest) {
        
        HouseMember updatedMember = houseMemberService.updateHouseMemberEmail(id, houseMemberRequest.getEmailAddress());

        HouseMemberResponseDto updatedMemberResponse = new HouseMemberResponseDto(
            updatedMember.getId(),
            updatedMember.getName(),
            updatedMember.getPhoneNumber(),
            updatedMember.getEmailAddress(),
            updatedMember.getChores(),
            updatedMember.getHouse().getId()
        );

        return new ResponseEntity<HouseMemberResponseDto>(updatedMemberResponse, HttpStatus.OK);
    }

    @PutMapping("/members/updateNumber/{id}")
    public ResponseEntity<HouseMemberResponseDto> updatePhoneNumber(@PathVariable int id, @RequestBody HouseMemberRequestDto houseMemberRequest) {
        
        HouseMember updatedMember = houseMemberService.updateHouseMemberPhoneNumber(id, houseMemberRequest.getPhoneNumber());

        HouseMemberResponseDto updatedMemberResponse = new HouseMemberResponseDto(
            updatedMember.getId(),
            updatedMember.getName(),
            updatedMember.getPhoneNumber(),
            updatedMember.getEmailAddress(),
            updatedMember.getChores(),
            updatedMember.getHouse().getId()
        );

        return new ResponseEntity<HouseMemberResponseDto>(updatedMemberResponse, HttpStatus.OK);
    }

    @DeleteMapping("/members/delete/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable int id) {
        
        houseMemberService.deleteHouseMember(id);

        return new ResponseEntity<String>("House Member with id " + id + " has been deleted.", HttpStatus.OK);
    }
}
