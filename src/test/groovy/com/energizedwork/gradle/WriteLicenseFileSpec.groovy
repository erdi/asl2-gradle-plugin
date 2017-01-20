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

import org.gradle.testkit.runner.TaskOutcome

import static com.energizedwork.gradle.Asl2Plugin.WRITE_LICENSE_FILE_TASK_NAME
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.gradle.testkit.runner.TaskOutcome.UP_TO_DATE

class WriteLicenseFileSpec extends PluginSpec {

    def "writeLicenseFile task writes ASL2 license file into the root of the project"() {
        when:
        runTask('writeLicenseFile')

        then:
        licenseFile.text == getClass().getResource('ASL2').text
    }

    def "writeLicenseFile task is incremental"() {
        when:
        runTask(WRITE_LICENSE_FILE_TASK_NAME)

        then:
        runTaskAndGetOutcome(WRITE_LICENSE_FILE_TASK_NAME) == UP_TO_DATE

        when:
        licenseFile.delete()

        then:
        runTaskAndGetOutcome(WRITE_LICENSE_FILE_TASK_NAME) == SUCCESS
    }

    private File getLicenseFile() {
        new File(testProjectDir.root, 'LICENSE')
    }

    private TaskOutcome runTaskAndGetOutcome(String taskName) {
        runTask(taskName).tasks.find { it.path == ":$taskName" }.outcome
    }

}
