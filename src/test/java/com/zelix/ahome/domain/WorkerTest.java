package com.zelix.ahome.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zelix.ahome.web.rest.TestUtil;

public class WorkerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Worker.class);
        Worker worker1 = new Worker();
        worker1.setId(1L);
        Worker worker2 = new Worker();
        worker2.setId(worker1.getId());
        assertThat(worker1).isEqualTo(worker2);
        worker2.setId(2L);
        assertThat(worker1).isNotEqualTo(worker2);
        worker1.setId(null);
        assertThat(worker1).isNotEqualTo(worker2);
    }
}
