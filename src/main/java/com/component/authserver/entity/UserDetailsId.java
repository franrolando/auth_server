package com.component.authserver.entity;

import com.component.authserver.constants.Provider;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsId implements Serializable {

    private String userId;
    @Enumerated(EnumType.ORDINAL)
    private Provider fkProviderId;

}
