/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package uk.ac.ed.portlets.almaportlet.data;

/**
 * Typesafe wrapper around a library barcode.
 */
public class Barcode {
    private final String barcode;
    
    private      Barcode(final String setBarcode) {
        this.barcode = setBarcode;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Barcode)) {
            return false;
        }
        
        final Barcode other = (Barcode)o;
        
        return this.barcode.equals(other.barcode);
    }
    
    @Override
    public int hashCode() {
        return this.barcode.hashCode();
    }
    
    public static Barcode get(final String value) {
        return null == value
            ? null
            : new Barcode(value);
    }

    /**
     * @return the id
     */
    public String getBarcode() {
        return barcode;
    }
    
    @Override
    public String toString() {
        return this.barcode;
    }
}
