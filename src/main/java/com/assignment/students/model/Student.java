package com.assignment.students.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    @JsonProperty("student_id")
    @Column(name="student_id")
    private long studentId;

    @JsonProperty("first_name")
    @Column(name = "first_name")
    @NotBlank
    @Size(min=1,max = 20)
    private String firstName;

    @JsonProperty("last_name")
    @Column(name = "last_name")
    @NotBlank
    @Size(min=1,max = 20)
    private String lastName;

    @JsonProperty("email_addresses")
    private List<EmailAddresses> emailAddresses;

    @JsonProperty("courses")
    private List<Course> courses;

}
