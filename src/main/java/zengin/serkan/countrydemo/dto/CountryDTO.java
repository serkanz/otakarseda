package zengin.serkan.countrydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryDTO {
    private String cca3;
    private Set<String> borders;

    private CountryDTO previousCountry = null;
    private Boolean visited = false;
    private Integer distance = Integer.MAX_VALUE;
}
