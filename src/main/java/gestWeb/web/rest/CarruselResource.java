package gestWeb.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestWeb.service.CarruselService;

/**
 * REST controller for managing TituloShort.
 */
@RestController
@RequestMapping("/api")
public class CarruselResource {

    private final Logger log = LoggerFactory.getLogger(TituloShortResource.class);

    private final CarruselService carruselService;

    public CarruselResource(CarruselService carruselService) {
        this.carruselService = carruselService;
    }



    /**
     * GET  /carrusel : get all the carrusel.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imagenes in body
     */
    @GetMapping("/carrusel")
    @Timed
    public String[] getAllCarrusel() {
        log.debug("REST request to get all carruselService");
        return carruselService.listaCarrusel();
    }


}
