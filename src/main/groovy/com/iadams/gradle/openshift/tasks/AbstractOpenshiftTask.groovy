/*
 * Openshift Plugin
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Iain Adams
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.iadams.gradle.openshift.tasks

import com.openshift.restclient.ClientBuilder
import com.openshift.restclient.IClient
import com.openshift.restclient.authorization.TokenAuthorizationStrategy
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class AbstractOpenshiftTask extends DefaultTask {

  @Input
  String baseUrl

  @Input
  String token

  IClient client;

  AbstractOpenshiftTask(String description) {
    this.description = description
    group = 'OpenShift'
  }

  @TaskAction
  void start() {
    performLogin()
    executeAction()
  }

  abstract void executeAction()

  /**
   * Initializes the client and performs a login preferring token > user/password
   */
  void performLogin() {
    client = new ClientBuilder(getBaseUrl())
//      .usingToken(getToken())
      .build()

    client.setAuthorizationStrategy(new TokenAuthorizationStrategy(getToken()))
  }
}