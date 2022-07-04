package gestWeb.domain;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gestWeb.web.rest.TestUtil;

public class Gruposcie10Test {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gruposcie10.class);
        Gruposcie10 gruposcie101 = new Gruposcie10();
        gruposcie101.setId(1L);
        Gruposcie10 gruposcie102 = new Gruposcie10();
        gruposcie102.setId(gruposcie101.getId());
        assertThat(gruposcie101).isEqualTo(gruposcie102);
        gruposcie102.setId(2L);
        assertThat(gruposcie101).isNotEqualTo(gruposcie102);
        gruposcie101.setId(null);
        assertThat(gruposcie101).isNotEqualTo(gruposcie102);
    }
}
