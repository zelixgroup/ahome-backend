package com.zelix.ahome.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.ahome.web.rest.TestUtil;

public class IdDocumentTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdDocumentType.class);
        IdDocumentType idDocumentType1 = new IdDocumentType();
        idDocumentType1.setId(1L);
        IdDocumentType idDocumentType2 = new IdDocumentType();
        idDocumentType2.setId(idDocumentType1.getId());
        assertThat(idDocumentType1).isEqualTo(idDocumentType2);
        idDocumentType2.setId(2L);
        assertThat(idDocumentType1).isNotEqualTo(idDocumentType2);
        idDocumentType1.setId(null);
        assertThat(idDocumentType1).isNotEqualTo(idDocumentType2);
    }
}
