/*
 *  *
 *  Copyright (C) 2009-2015 Dell, Inc.
 *  See annotations for authorship information
 *
 *  ====================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  ====================================================================
 *
 */

package org.dasein.cloud.azurepack.tests;

import junit.framework.Assert;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.dasein.cloud.util.requester.streamprocessors.XmlStreamToObjectProcessor;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Created by vmunthiu on 9/9/2015.
 */
public class HttpMethodAsserts {
  public static void assertGet(HttpUriRequest actualHttpRequest, String expectedUrl) {
    assertHttpMethod(actualHttpRequest, "GET", expectedUrl);
  }

  public static void assertGet(HttpUriRequest actualHttpRequest, String expectedUrl, Header[] expectedHeaders) {
    assertHttpMethod(actualHttpRequest, "GET", expectedUrl, expectedHeaders);
  }

  public static void assertDelete(HttpUriRequest actualHttpRequest, String expectedUrl) {
    assertHttpMethod(actualHttpRequest, "DELETE", expectedUrl);
  }

  public static void assertDelete(HttpUriRequest actualHttpRequest, String expectedUrl, Header[] expectedHeaders) {
    assertHttpMethod(actualHttpRequest, "DELETE", expectedUrl, expectedHeaders);
  }

  public static void assertPut(HttpUriRequest actualHttpRequest, String expectedUrl) {
    assertHttpMethod(actualHttpRequest, "PUT", expectedUrl);
  }

  public static void assertPut(HttpUriRequest actualHttpRequest, String expectedUrl, Header[] expectedHeaders) {
    assertHttpMethod(actualHttpRequest, "PUT", expectedUrl, expectedHeaders);
  }

  public static void assertPut(HttpUriRequest actualHttpRequest, String expectedUrl, Header[] expectedHeaders, Object expectedEntity) {
    assertHttpMethod(actualHttpRequest, "PUT", expectedUrl, expectedHeaders, expectedEntity);
  }

  public static void assertPost(HttpUriRequest actualHttpRequest, String expectedUrl) {
    assertHttpMethod(actualHttpRequest, "POST", expectedUrl);
  }

  public static void assertPost(HttpUriRequest actualHttpRequest, String expectedUrl, Header[] expectedHeaders) {
    assertHttpMethod(actualHttpRequest, "POST", expectedUrl, expectedHeaders);
  }

  public static void assertPost(HttpUriRequest actualHttpRequest, String expectedUrl, Header[] expectedHeaders, Object expectedEntity) {
    assertHttpMethod(actualHttpRequest, "POST", expectedUrl, expectedHeaders, expectedEntity);
  }

  private static void assertHttpMethod(HttpUriRequest actualHttpRequest, String expectedHttpMethod, String expectedUrl) {
    assertEquals(String.format("Method does not performs a HTTP %s", expectedHttpMethod), expectedHttpMethod, actualHttpRequest.getMethod());
    assertEquals(String.format("Method does not performs a HTTP %s at the correct URL", expectedHttpMethod), expectedUrl, actualHttpRequest.getURI().toString());
  }

  private static void assertHttpMethod(final HttpUriRequest actualHttpRequest, String expectedHttpMethod, String expectedUrl, Header[] expectedHeaders)
  {
    assertHttpMethod(actualHttpRequest, expectedHttpMethod, expectedUrl);
    assertNotNull(actualHttpRequest.getAllHeaders());
    CollectionUtils.forAllDo(Arrays.asList(expectedHeaders), new Closure() {
      @Override
      public void execute(Object input) {
        final Header expectedHeader = (Header) input;
        boolean headerExists = CollectionUtils.exists(Arrays.asList(actualHttpRequest.getAllHeaders()), new Predicate() {
          @Override
          public boolean evaluate(Object object) {
            Header actualHeader = (Header) object;
            return expectedHeader.getName().equals(actualHeader.getName()) && expectedHeader.getValue().equals(actualHeader.getValue());
          }
        });
        assertTrue(String.format("Expected %s header not found in the request or found with the wrong value in the request", expectedHeader.getName()), headerExists);
      }
    });
  }

  private static void assertHttpMethod(HttpUriRequest actualHttpRequest, String expectedHttpMethod, String expectedUrl, Header[] expectedHeaders, Object expectedEntity) {
    assertHttpMethod(actualHttpRequest, expectedHttpMethod, expectedUrl, expectedHeaders);
    assertTrue("Incorrect httpRequest found in the call", actualHttpRequest instanceof HttpEntityEnclosingRequestBase);

    try {
      Object actualEntity = new XmlStreamToObjectProcessor().read(((HttpEntityEnclosingRequestBase)actualHttpRequest).getEntity().getContent(), expectedEntity.getClass());
      assertReflectionEquals("Incorrect value(s) found in the XML body of the request", expectedEntity, actualEntity);
    } catch (Exception e) {
      Assert.fail("Incorrect XML body found in the request");
    }
  }
}