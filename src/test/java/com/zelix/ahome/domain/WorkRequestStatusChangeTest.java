package com.zelix.ahome.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.ahome.web.rest.TestUtil;

public class WorkRequestStatusChangeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkRequestStatusChange.class);
        WorkRequestStatusChange workRequestStatusChange1 = new WorkRequestStatusChange();
        workRequestStatusChange1.setId(1L);
        WorkRequestStatusChange workRequestStatusChange2 = new WorkRequestStatusChange();
        workRequestStatusChange2.setId(workRequestStatusChange1.getId());
        assertThat(workRequestStatusChange1).isEqualTo(workRequestStatusChange2);
        workRequestStatusChange2.setId(2L);
        assertThat(workRequestStatusChange1).isNotEqualTo(workRequestStatusChange2);
        workRequestStatusChange1.setId(null);
        assertThat(workRequestStatusChange1).isNotEqualTo(workRequestStatusChange2);
    }
}
