package zengin.serkan.countrydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RouteDTO {
    private List<String> route;

    public List<String> getRoute() {
        if (route == null) {
            route = new ArrayList<>();
        }
        return route;
    }
}
