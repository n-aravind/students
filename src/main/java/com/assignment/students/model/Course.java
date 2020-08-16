package com.assignment.students.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {

    @JsonProperty("course_id")
    @Column(name = "course_id")
    private long courseId;

    @JsonProperty("course_name")
    @Column(name = "course_name")
    @NotBlank
    @Size(min=2,max=15)
    private String courseName;

    @JsonProperty("course_description")
    @Column(name = "course_description")
    @NotBlank
    @Size(min=2,max=30)
    private String courseDescription;

}
