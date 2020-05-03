package com.assignment.students.model;

import com.assignment.students.utils.FilterColumn;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailAddresses {

    @Column(name = "id")
    @JsonInclude(value = JsonInclude.Include.CUSTOM,valueFilter = FilterColumn.class)
    private long id;

    @JsonProperty("student_id")
    @Column(name = "student_id")
    @JsonInclude(value = JsonInclude.Include.CUSTOM,valueFilter = FilterColumn.class)
    private long studentId;

    @JsonProperty("email_address")
    @Column(name = "email_address")
    private String emailAddress;

    @JsonProperty("email_type")
    @Column(name = "email_type")
    private EmailType emailType;
}
