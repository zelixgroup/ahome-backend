package com.zelix.ahome.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.ahome.web.rest.TestUtil;

public class IdDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdDocument.class);
        IdDocument idDocument1 = new IdDocument();
        idDocument1.setId(1L);
        IdDocument idDocument2 = new IdDocument();
        idDocument2.setId(idDocument1.getId());
        assertThat(idDocument1).isEqualTo(idDocument2);
        idDocument2.setId(2L);
        assertThat(idDocument1).isNotEqualTo(idDocument2);
        idDocument1.setId(null);
        assertThat(idDocument1).isNotEqualTo(idDocument2);
    }
}
