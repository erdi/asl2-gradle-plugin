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

import groovy.xml.XmlUtil
import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input

import static com.energizedwork.gradle.Asl2Plugin.IDEA_COPYRIGHT_HEADERS_CONFIG_RESOURCE_NAME
import static org.xmlunit.builder.Input.fromStream

class IdeaCopyrightHeadersConfigSpec extends PluginSpec {

    def "copyright headers are configured in IntelliJ if idea plugin is applied"() {
        given:
        buildScript << '''
            apply plugin: 'idea'
        '''

        when:
        def iprXml = generateAndParseIdeaProjectConf()

        then:
        !DiffBuilder.compare(copyrightHeadersConfigResourceInput)
                .withTest(nodeInput(iprXml.component.find { it.@name == 'CopyrightManager' }))
                .ignoreWhitespace()
                .build()
                .hasDifferences()
    }

    def "plugin does not apply idea plugin"() {
        given:
        buildScript << """
            task $testTaskName {
                doLast {
                    println "Has idea plugin: \${project.plugins.hasPlugin(IdeaPlugin)}"
                }
            }
        """

        expect:
        runTask(testTaskName).output.contains('Has idea plugin: false')

        where:
        testTaskName = 'hasIdeaPlugin'
    }

    def "idea-project-components plugin can be applied after applying asl2 plugin"() {
        given:
        buildScript << '''
            apply plugin: 'com.energizedwork.idea-project-components'
        '''

        when:
        runIdeaProjectTask()

        then:
        noExceptionThrown()
    }

    private Input.Builder nodeInput(Node node) {
        Input.fromString(XmlUtil.serialize(node))
    }

    private Input.Builder getCopyrightHeadersConfigResourceInput() {
        fromStream(getClass().getResourceAsStream(IDEA_COPYRIGHT_HEADERS_CONFIG_RESOURCE_NAME))
    }

    private void runIdeaProjectTask() {
        runTask('ideaProject')
    }

    private String getProjectName() {
        testProjectDir.root.name
    }

    private Node generateAndParseIdeaProjectConf() {
        runIdeaProjectTask()
        new XmlParser().parse(new File(testProjectDir.root, "${projectName}.ipr"))
    }
}
