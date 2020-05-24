package com.zelix.ahome.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.ahome.web.rest.TestUtil;

public class WorkReviewTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkReview.class);
        WorkReview workReview1 = new WorkReview();
        workReview1.setId(1L);
        WorkReview workReview2 = new WorkReview();
        workReview2.setId(workReview1.getId());
        assertThat(workReview1).isEqualTo(workReview2);
        workReview2.setId(2L);
        assertThat(workReview1).isNotEqualTo(workReview2);
        workReview1.setId(null);
        assertThat(workReview1).isNotEqualTo(workReview2);
    }
}
