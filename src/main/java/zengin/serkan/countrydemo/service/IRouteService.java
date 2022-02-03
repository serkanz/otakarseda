package zengin.serkan.countrydemo.service;

import org.springframework.http.ResponseEntity;
import zengin.serkan.countrydemo.dto.RouteDTO;

public interface IRouteService {
    ResponseEntity<RouteDTO> getRoute(String origin, String destination);
}
