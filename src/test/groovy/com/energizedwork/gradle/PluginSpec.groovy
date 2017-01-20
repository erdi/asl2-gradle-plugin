/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.energizedwork.gradle

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class PluginSpec extends Specification {

    @Rule
    protected TemporaryFolder testProjectDir
    protected File buildScript

    void setup() {
        buildScript = testProjectDir.newFile('build.gradle')
        applyPlugin()
    }

    protected BuildResult runTask(String taskName) {
        GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments(taskName)
            .withPluginClasspath()
            .build()
    }

    private void applyPlugin() {
        buildScript << """
            plugins {
                id 'com.energizedwork.asl2'
            }
         """
    }

}
