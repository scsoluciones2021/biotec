package gestWeb.domain;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gestWeb.web.rest.TestUtil;

public class Categoriascie10Test {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categoriascie10.class);
        Categoriascie10 categoriascie101 = new Categoriascie10();
        categoriascie101.setId(1L);
        Categoriascie10 categoriascie102 = new Categoriascie10();
        categoriascie102.setId(categoriascie101.getId());
        assertThat(categoriascie101).isEqualTo(categoriascie102);
        categoriascie102.setId(2L);
        assertThat(categoriascie101).isNotEqualTo(categoriascie102);
        categoriascie101.setId(null);
        assertThat(categoriascie101).isNotEqualTo(categoriascie102);
    }
}
