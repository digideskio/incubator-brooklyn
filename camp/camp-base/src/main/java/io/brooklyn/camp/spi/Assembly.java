/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/
package io.brooklyn.camp.spi;

import io.brooklyn.camp.spi.collection.BasicResourceLookup;
import io.brooklyn.camp.spi.collection.ResourceLookup;
import io.brooklyn.camp.spi.collection.ResourceLookup.EmptyResourceLookup;


/** Holds the metadata (name, description, etc) for an AssemblyTemplate
 * as well as fields pointing to behaviour (eg list of ACT's).
 * <p>
 * See {@link AbstractResource} for more general information.
 */
public class Assembly extends AbstractResource {

    public static final String CAMP_TYPE = "Assembly";
    static { assert CAMP_TYPE.equals(Assembly.class.getSimpleName()); }
    
    /** Use {@link #builder()} to create */
    protected Assembly() {}

    AssemblyTemplate assemblyTemplate;
    ResourceLookup<ApplicationComponent> applicationComponents;
    ResourceLookup<PlatformComponent> platformComponents;
    
    // TODO
//    "parameterDefinitionUri": URI,
//    "pdpUri" : URI ?
                    
    public AssemblyTemplate getAssemblyTemplate() {
        return assemblyTemplate;
    }
    public ResourceLookup<ApplicationComponent> getApplicationComponents() {
        return applicationComponents != null ? applicationComponents : new EmptyResourceLookup<ApplicationComponent>();
    }
    public ResourceLookup<PlatformComponent> getPlatformComponents() {
        return platformComponents != null ? platformComponents : new EmptyResourceLookup<PlatformComponent>();
    }
    
    private void setAssemblyTemplate(AssemblyTemplate assemblyTemplate) {
        this.assemblyTemplate = assemblyTemplate;
    }
    private void setApplicationComponents(ResourceLookup<ApplicationComponent> applicationComponents) {
        this.applicationComponents = applicationComponents;
    }
    private void setPlatformComponents(ResourceLookup<PlatformComponent> platformComponents) {
        this.platformComponents = platformComponents;
    }
    
    // builder
    
    public static Builder<? extends Assembly> builder() {
        return new Builder<Assembly>(CAMP_TYPE);
    }
    
    public static class Builder<T extends Assembly> extends AbstractResource.Builder<T,Builder<T>> {
        
        protected Builder(String type) { super(type); }
        
        @SuppressWarnings("unchecked")
        protected T createResource() { return (T) new Assembly(); }
        
        public Builder<T> assemblyTemplate(AssemblyTemplate x) { instance().setAssemblyTemplate(x); return thisBuilder(); }
        public Builder<T> applicationComponentTemplates(ResourceLookup<ApplicationComponent> x) { instance().setApplicationComponents(x); return thisBuilder(); }
        public Builder<T> platformComponentTemplates(ResourceLookup<PlatformComponent> x) { instance().setPlatformComponents(x); return thisBuilder(); }
        
        public synchronized Builder<T> add(ApplicationComponent x) {
            if (instance().applicationComponents==null) {
                instance().applicationComponents = new BasicResourceLookup<ApplicationComponent>();
            }
            if (!(instance().applicationComponents instanceof BasicResourceLookup)) {
                throw new IllegalStateException("Cannot add to resource lookup "+instance().applicationComponents);
            }
            ((BasicResourceLookup<ApplicationComponent>)instance().applicationComponents).add(x);
            return thisBuilder();
        }
        
        public synchronized Builder<T> add(PlatformComponent x) {
            if (instance().platformComponents==null) {
                instance().platformComponents = new BasicResourceLookup<PlatformComponent>();
            }
            if (!(instance().platformComponents instanceof BasicResourceLookup)) {
                throw new IllegalStateException("Cannot add to resource lookup "+instance().platformComponents);
            }
            ((BasicResourceLookup<PlatformComponent>)instance().platformComponents).add(x);
            return thisBuilder();
        }
        
        @Override
        public synchronized T build() {
            return super.build();
        }
    }

}
