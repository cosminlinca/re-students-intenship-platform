package com.re.internship.platform.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.re.internship.platform.domain.Offer;
import com.re.internship.platform.domain.*; // for static metamodels
import com.re.internship.platform.repository.OfferRepository;
import com.re.internship.platform.service.dto.OfferCriteria;
import com.re.internship.platform.service.dto.OfferDTO;
import com.re.internship.platform.service.mapper.OfferMapper;

/**
 * Service for executing complex queries for {@link Offer} entities in the database.
 * The main input is a {@link OfferCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OfferDTO} or a {@link Page} of {@link OfferDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfferQueryService extends QueryService<Offer> {

    private final Logger log = LoggerFactory.getLogger(OfferQueryService.class);

    private final OfferRepository offerRepository;

    private final OfferMapper offerMapper;

    public OfferQueryService(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    /**
     * Return a {@link List} of {@link OfferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OfferDTO> findByCriteria(OfferCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Offer> specification = createSpecification(criteria);
        return offerMapper.toDto(offerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OfferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OfferDTO> findByCriteria(OfferCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Offer> specification = createSpecification(criteria);
        return offerRepository.findAll(specification, page)
            .map(offerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OfferCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Offer> specification = createSpecification(criteria);
        return offerRepository.count(specification);
    }

    /**
     * Function to convert {@link OfferCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Offer> createSpecification(OfferCriteria criteria) {
        Specification<Offer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Offer_.id));
            }
            if (criteria.getPositionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPositionName(), Offer_.positionName));
            }
            if (criteria.getProgramDurationInWeeks() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProgramDurationInWeeks(), Offer_.programDurationInWeeks));
            }
            if (criteria.getRequiredSkills() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequiredSkills(), Offer_.requiredSkills));
            }
            if (criteria.getTechnologies() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTechnologies(), Offer_.technologies));
            }
            if (criteria.getDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetails(), Offer_.details));
            }
            if (criteria.getPaid() != null) {
                specification = specification.and(buildSpecification(criteria.getPaid(), Offer_.paid));
            }
            if (criteria.getObservations() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservations(), Offer_.observations));
            }
            if (criteria.getDomain() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomain(), Offer_.domain));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Offer_.companyId));
            }
            if (criteria.getCoverImagePath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCoverImagePath(), Offer_.coverImagePath));
            }
        }
        return specification;
    }
}
