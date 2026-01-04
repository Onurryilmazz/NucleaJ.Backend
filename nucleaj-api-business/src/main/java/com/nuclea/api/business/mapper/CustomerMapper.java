package com.nuclea.api.business.mapper;

import com.nuclea.api.business.model.response.CustomerResponse;
import com.nuclea.data.entity.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * MapStruct mapper for Customer entity.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "isEmailVerified", source = "isEmailVerified")
    @Mapping(target = "isActive", source = "isActive")
    @Mapping(target = "oauthProvider", source = "oauthProvider")
    CustomerResponse toResponse(Customer customer);
}
