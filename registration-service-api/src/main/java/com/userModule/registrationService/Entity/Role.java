package com.userModule.registrationService.Entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Author : Rohit Parihar
 * Date : 12/18/2022
 * Time : 4:30 PM
 * Class : Role
 * Project : Bloggios-Backend
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    private String roleId;
    private String roleName;
    private String userId;
    private Date createdAt;
    private Boolean isActive;
}
