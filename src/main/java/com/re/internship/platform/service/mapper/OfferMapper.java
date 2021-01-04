package com.re.internship.platform.service.mapper;


import com.re.internship.platform.domain.*;
import com.re.internship.platform.service.dto.OfferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offer} and its DTO {@link OfferDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {



    default Offer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Offer offer = new Offer();
        offer.setId(id);
        return offer;
    }
}
