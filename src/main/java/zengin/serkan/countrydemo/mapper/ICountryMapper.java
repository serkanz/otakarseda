package zengin.serkan.countrydemo.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import zengin.serkan.countrydemo.dto.CountryDTO;
import zengin.serkan.countrydemo.model.Country;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICountryMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target="name", source="cca3")
    })
    Set<Country> mapToCountrySet(Set<CountryDTO> countryDTOSet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target="name", source="cca3")
    })
    Country mapToCountry(CountryDTO countryDTO);
}
