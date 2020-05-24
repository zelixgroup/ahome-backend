package com.zelix.ahome.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.ahome.web.rest.TestUtil;

public class WorkCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkCategory.class);
        WorkCategory workCategory1 = new WorkCategory();
        workCategory1.setId(1L);
        WorkCategory workCategory2 = new WorkCategory();
        workCategory2.setId(workCategory1.getId());
        assertThat(workCategory1).isEqualTo(workCategory2);
        workCategory2.setId(2L);
        assertThat(workCategory1).isNotEqualTo(workCategory2);
        workCategory1.setId(null);
        assertThat(workCategory1).isNotEqualTo(workCategory2);
    }
}
