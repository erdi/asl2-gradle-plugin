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

import com.energizedwork.gradle.idea.IdeaProjectComponentsPlugin
import com.energizedwork.gradle.idea.IdeaProjectComponentsPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.plugins.ide.idea.IdeaPlugin

class Asl2Plugin implements Plugin<Project> {

    public static final String WRITE_LICENSE_FILE_TASK_NAME = 'writeLicenseFile'
    public static final String LICENSE_RESOURCE_NAME = 'ASL2'
    public static final String LICENSE_FILE_NAME = 'LICENSE'
    public static final String IDEA_COPYRIGHT_HEADERS_CONFIG_RESOURCE_NAME = 'asl2-copyright-header-idea-component.xml'

    void apply(Project project) {
        addWriteLicenseFileTask(project)
        addIdeaCopyrightHeaderConfiguration(project)
    }

    private void addIdeaCopyrightHeaderConfiguration(Project project) {
        def plugins = project.plugins
        plugins.withType(IdeaPlugin) {
            plugins.apply(IdeaProjectComponentsPlugin)
            project.extensions.configure(IdeaProjectComponentsPluginExtension) { IdeaProjectComponentsPluginExtension extension ->
                extension.stream(getClass().getResourceAsStream(IDEA_COPYRIGHT_HEADERS_CONFIG_RESOURCE_NAME))
            }
        }
    }

    private Task addWriteLicenseFileTask(Project project) {
        project.task(WRITE_LICENSE_FILE_TASK_NAME) { Task task ->
            def licenceFile = new File(project.projectDir, LICENSE_FILE_NAME)
            task.outputs.file(licenceFile)
            task.doLast {
                new File(project.projectDir, LICENSE_FILE_NAME).withDataOutputStream {
                    it << getClass().getResourceAsStream(LICENSE_RESOURCE_NAME)
                }
            }
        }
    }

}
