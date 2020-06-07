package com.assignment.students.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Class {

    @JsonProperty("class_id")
    @Column(name = "class_id")
    private long classId;

    @JsonProperty("class_name")
    @Column(name = "class_name")
    // TODO GP @NotEmpty is better, @NotBlank is best
    @NotNull
    @Size(min=2,max=15)
    private String className;

    @JsonProperty("class_description")
    @Column(name = "class_description")
    @NotNull
    @Size(min=2,max=30)
    private String classDescription;

}
