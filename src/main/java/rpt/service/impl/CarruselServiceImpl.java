package rpt.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rpt.repository.CarruselRepository;
import rpt.service.CarruselService;
/**
 * Service Implementation for managing Carrusel.
 */
@Service
@Transactional
public class CarruselServiceImpl implements CarruselService {

    private final Logger log = LoggerFactory.getLogger(CarruselServiceImpl.class);

    private final Path rootLocation = Paths.get("carrusel");

    public CarruselServiceImpl() { }

    /**
     * Get all the Carrusels.
     *
     * @return the list of entities
     */
    @Override

    public String[] listaCarrusel() {
        log.debug("Request to get all Carrusels");
        return CarruselRepository.listaCarrusel(rootLocation.toString());
    }

}
