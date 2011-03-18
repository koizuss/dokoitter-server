package com.appspot.dokoitter.server.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.model.Follow;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class FollowTest extends AppEngineTestCase {

    private Follow model = new Follow();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
