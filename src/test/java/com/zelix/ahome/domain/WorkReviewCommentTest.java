package com.zelix.ahome.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.ahome.web.rest.TestUtil;

public class WorkReviewCommentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkReviewComment.class);
        WorkReviewComment workReviewComment1 = new WorkReviewComment();
        workReviewComment1.setId(1L);
        WorkReviewComment workReviewComment2 = new WorkReviewComment();
        workReviewComment2.setId(workReviewComment1.getId());
        assertThat(workReviewComment1).isEqualTo(workReviewComment2);
        workReviewComment2.setId(2L);
        assertThat(workReviewComment1).isNotEqualTo(workReviewComment2);
        workReviewComment1.setId(null);
        assertThat(workReviewComment1).isNotEqualTo(workReviewComment2);
    }
}
