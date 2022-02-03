package zengin.serkan.countrydemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zengin.serkan.countrydemo.dto.CountryDTO;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Country implements Comparable<Country> {
    private String name;
    private Set<String> borders;

    private Country previousCountry = null;
    private Boolean visited = false;
    private Integer distance = Integer.MAX_VALUE;

    @Override
    public int compareTo(Country o) {
        return Double.compare(this.distance, o.getDistance());
    }
}
