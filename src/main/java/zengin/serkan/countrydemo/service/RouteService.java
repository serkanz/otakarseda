package zengin.serkan.countrydemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zengin.serkan.countrydemo.dto.CountryDTO;
import zengin.serkan.countrydemo.dto.RouteDTO;
import zengin.serkan.countrydemo.mapper.ICountryMapper;
import zengin.serkan.countrydemo.model.Country;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

@Service
public class RouteService implements IRouteService {
    @Autowired
    private Set<Country> countries;

    public ResponseEntity<RouteDTO> getRoute(String origin, String destination) {

        //If origin and destination are same return bad request
        if (origin.equals(destination)) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

        Optional<Country> originCountryOpt = countries.stream().filter(country -> country.getName().equals(origin.toUpperCase(Locale.ENGLISH))).findFirst();
        Optional<Country> destinationCountryOpt = countries.stream().filter(country -> country.getName().equals(destination.toUpperCase(Locale.ENGLISH))).findFirst();

        //If origin or destination country is not found return bad request
        if (!originCountryOpt.isPresent() || !destinationCountryOpt.isPresent())
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        //If origin or destination country does not have any neighbor return bad request
        if (originCountryOpt.get().getBorders().size() == 0 || destinationCountryOpt.get().getBorders().size() == 0)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        Set<String> commonBorders = new HashSet<>(originCountryOpt.get().getBorders());
        commonBorders.retainAll(destinationCountryOpt.get().getBorders());

        RouteDTO route = new RouteDTO();
        //If origin and destination country have any common border return first country in common border
        if (commonBorders.size() > 0) {
            route.getRoute().add(originCountryOpt.get().getName());
            route.getRoute().add(commonBorders.stream().findFirst().get());
            route.getRoute().add(destinationCountryOpt.get().getName());
            return new ResponseEntity(route, HttpStatus.OK);
        }

        //set the fields used for path finding to initial values
        countries.stream().forEach(country -> {
            country.setVisited(false);
            country.setPreviousCountry(null);
            country.setDistance(0);
        });

        RouteDTO routeDTO = calculateShortestRoute(originCountryOpt.get(), destinationCountryOpt.get());

        //if no common border found return bad request
        if (routeDTO == null)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        //return shortest path from origin to destination
        return new ResponseEntity<RouteDTO>(routeDTO, HttpStatus.OK);
    }

    //Dijkstra’s algorithm is used to find the shortest path between two countries
    private RouteDTO calculateShortestRoute(Country origin, Country destination) {
        PriorityQueue<Country> queue = new PriorityQueue<>();
        //number of hops from origin is stored in i
        Integer i = 0;

        origin.setDistance(0);
        queue.add(origin);

        //every possible path from origin is checked
        while (!queue.isEmpty()) {
            i++;
            Country currentCountry = queue.poll();

            //every neighbor of current country is checked
            for (String neighbor : currentCountry.getBorders()) {
                Country country = countries.stream().filter(c -> c.getName().equals(neighbor)).findFirst().get();
                //every possible route from current country to origin is checked
                if (!country.getVisited()) {
                    //if previously not visited add to the queue
                    if (!queue.contains(country)) {
                        country.setPreviousCountry(currentCountry);
                        country.setDistance(i);
                        queue.add(country);
                    } else {
                        //if previously visited check if the hops from origin is less than the current hops
                        if (country.getDistance() > i) {
                            queue.remove(country);

                            country.setPreviousCountry(currentCountry);
                            country.setDistance(i);
                            queue.add(country);
                        }
                    }
                }
            }
            currentCountry.setVisited(true);
        }
        //after every possible hops from origin is checked, if destination is not found return null
        if (destination.getDistance() == 0) {
            return null;
        } else {

            //else create a new route object and fill it with countries from origin to destination and reverse it
            RouteDTO route = new RouteDTO();
            Country currentCountry = destination;
            while (currentCountry.getPreviousCountry() != null) {
                route.getRoute().add(currentCountry.getName());
                currentCountry = currentCountry.getPreviousCountry();
            }
            route.getRoute().add(currentCountry.getName());
            Collections.reverse(route.getRoute());
            return route;
        }
    }
}
