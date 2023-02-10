package com.smModule.postService.Payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DatabindException;
import com.smModule.postService.Entity.Tag;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllPostResponse {
    private String caption;
    private List<Tag> tags;
    private String userId;
    private String categoryId;
    private Date dateCreated;
    private Boolean isEdited;
}
