package com.appspot.dokoitter.server.controller.status;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class StopControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/status/stop");
        StopController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
