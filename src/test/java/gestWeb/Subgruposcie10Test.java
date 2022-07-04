package gestWeb.domain;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gestWeb.web.rest.TestUtil;

public class Subgruposcie10Test {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subgruposcie10.class);
        Subgruposcie10 subgruposcie101 = new Subgruposcie10();
        subgruposcie101.setId(1L);
        Subgruposcie10 subgruposcie102 = new Subgruposcie10();
        subgruposcie102.setId(subgruposcie101.getId());
        assertThat(subgruposcie101).isEqualTo(subgruposcie102);
        subgruposcie102.setId(2L);
        assertThat(subgruposcie101).isNotEqualTo(subgruposcie102);
        subgruposcie101.setId(null);
        assertThat(subgruposcie101).isNotEqualTo(subgruposcie102);
    }
}
