package gestWeb.domain;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gestWeb.web.rest.TestUtil;

public class Diagnosticoscie10Test {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diagnosticoscie10.class);
        Diagnosticoscie10 diagnosticoscie101 = new Diagnosticoscie10();
        diagnosticoscie101.setId(1L);
        Diagnosticoscie10 diagnosticoscie102 = new Diagnosticoscie10();
        diagnosticoscie102.setId(diagnosticoscie101.getId());
        assertThat(diagnosticoscie101).isEqualTo(diagnosticoscie102);
        diagnosticoscie102.setId(2L);
        assertThat(diagnosticoscie101).isNotEqualTo(diagnosticoscie102);
        diagnosticoscie101.setId(null);
        assertThat(diagnosticoscie101).isNotEqualTo(diagnosticoscie102);
    }
}
