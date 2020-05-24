package com.zelix.ahome.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.ahome.web.rest.TestUtil;

public class WorkRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkRequest.class);
        WorkRequest workRequest1 = new WorkRequest();
        workRequest1.setId(1L);
        WorkRequest workRequest2 = new WorkRequest();
        workRequest2.setId(workRequest1.getId());
        assertThat(workRequest1).isEqualTo(workRequest2);
        workRequest2.setId(2L);
        assertThat(workRequest1).isNotEqualTo(workRequest2);
        workRequest1.setId(null);
        assertThat(workRequest1).isNotEqualTo(workRequest2);
    }
}
