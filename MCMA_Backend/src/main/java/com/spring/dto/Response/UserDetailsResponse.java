package com.spring.dto.Response;

import com.spring.enums.Gender;
import com.spring.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
public class UserDetailsResponse implements Serializable {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Date dateOfBirth;

    private Gender gender;

    private Type type;

    private String address;

    private Date dateCreated;

    private Date dateUpdated;
}
