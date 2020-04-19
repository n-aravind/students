package com.assignment.students.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Class {

    @JsonProperty("class_id")
    @Column(name = "class_id")
    private long classId;

    @JsonProperty("class_name")
    @Column(name = "class_name")
    private String className;

    @JsonProperty("class_description")
    @Column(name = "class_description")
    private String classDescription;
}
