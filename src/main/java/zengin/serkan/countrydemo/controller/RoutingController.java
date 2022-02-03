package zengin.serkan.countrydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zengin.serkan.countrydemo.dto.RouteDTO;
import zengin.serkan.countrydemo.service.IRouteService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/routing")
public class RoutingController {

    @Autowired
    private IRouteService routeService;

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<RouteDTO> getRouting(@PathVariable("origin") @NotBlank @Size(max = 3, min = 3) String origin,
                                               @PathVariable("destination") @NotBlank @Size(max = 3, min = 3) String destination) {
        return routeService.getRoute(origin, destination);
    }
}
