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


		/* 
			This file retrieves metrics via the cbstats command line tool.
		*/

println "couchbase_cbstats.groovy: Starting ..."

context = ServiceContextFactory.getServiceContext()

def instanceID = context.instanceId
if ( instanceID != 1 ) {
	return ["NA":0]
}

def scriptsFolder = context.attributes.thisInstance["scriptsFolder"]

def addServerScript="${scriptsFolder}/cbstats.sh"
def firstInstancePort = context.attributes.thisInstance["currentPort"]
def clusterBucketName = "CloudifyCouchbase${instanceID}"


builder = new AntBuilder()
builder.sequential {	
	echo(message:"couchbase_cbstats.groovy: Running ${addServerScript} ...")
	exec(executable:"${addServerScript}", outputproperty:"currentOutPut", failonerror: "true") {
		arg(value:"localhost")
		arg(value:firstInstancePort)	
		arg(value:"${clusterBucketName}")		
	}
}	

def outputPropertyStr = builder.project.properties."currentOutPut"

println outputPropertyStr
return outputPropertyStr

println "couchbase_cbstats.groovy: End of cbstats script"