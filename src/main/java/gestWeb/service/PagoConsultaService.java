package gestWeb.service;

import gestWeb.service.dto.PagoConsultaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link gestWeb.domain.PagoConsulta}.
 */
public interface PagoConsultaService {

    /**
     * Save a pagoConsulta.
     *
     * @param pagoConsultaDTO the entity to save.
     * @return the persisted entity.
     */
    PagoConsultaDTO save(PagoConsultaDTO pagoConsultaDTO);

    /**
     * Get all the pagoConsultas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PagoConsultaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pagoConsulta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PagoConsultaDTO> findOne(Long id);

    /**
     * Delete the "id" pagoConsulta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<PagoConsultaDTO> busqueda_filtros_impresion(String[] query, List<String> espSel, List<String> profSel);
}
