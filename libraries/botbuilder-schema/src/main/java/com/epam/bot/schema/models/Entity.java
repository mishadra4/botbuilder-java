/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.epam.bot.schema.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object of schema.org types.
 */
public class Entity {
    /**
     * Entity Type (typically from schema.org types).
     */
    @JsonProperty(value = "type")
    private String type;

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the Entity object itself.
     */
    public Entity withType(String type) {
        this.type = type;
        return this;
    }

}
