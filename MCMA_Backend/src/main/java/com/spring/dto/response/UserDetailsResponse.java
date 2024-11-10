package com.spring.dto.response;

import com.spring.enums.Gender;
import com.spring.enums.Type;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
