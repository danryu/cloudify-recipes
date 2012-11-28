/*******************************************************************************
* Copyright (c) 2012 GigaSpaces Technologies Ltd. All rights reserved
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/
import org.cloudifysource.dsl.context.ServiceContextFactory

context = ServiceContextFactory.getServiceContext()
config = new ConfigSlurper().parse(new File("master-service.properties").toURL())

def argsLine = "dfsadmin " + args.join(' ')

println "About to execute " + context.serviceDirectory + "/hadoop.sh "

new AntBuilder().sequential {	
    chmod(file:"${context.serviceDirectory}/hadoop.sh", perm:"ugo+rx")
	exec(executable:context.serviceDirectory + "/hadoop.sh", osfamily:"unix", failonerror:"false", spawn:"false") {
		arg("value":argsLine)
		env("key":"BIGINSIGHTS_HOME", "value":config.BI_DIRECTORY_PREFIX + config.BigInsightInstall)
	}
}
