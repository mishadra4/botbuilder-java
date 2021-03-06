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

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Provides details that modify the PaymentDetails based on payment method
 * identifier.
 */
public class PaymentDetailsModifier {
    /**
     * Contains a sequence of payment method identifiers.
     */
    @JsonProperty(value = "supportedMethods")
    private List<String> supportedMethods;

    /**
     * This value overrides the total field in the PaymentDetails dictionary
     * for the payment method identifiers in the supportedMethods field.
     */
    @JsonProperty(value = "total")
    private PaymentItem total;

    /**
     * Provides additional display items that are appended to the displayItems
     * field in the PaymentDetails dictionary for the payment method
     * identifiers in the supportedMethods field.
     */
    @JsonProperty(value = "additionalDisplayItems")
    private List<PaymentItem> additionalDisplayItems;

    /**
     * A JSON-serializable object that provides optional information that might
     * be needed by the supported payment methods.
     */
    @JsonProperty(value = "data")
    private Object data;

    /**
     * Get the supportedMethods value.
     *
     * @return the supportedMethods value
     */
    public List<String> supportedMethods() {
        return this.supportedMethods;
    }

    /**
     * Set the supportedMethods value.
     *
     * @param supportedMethods the supportedMethods value to set
     * @return the PaymentDetailsModifier object itself.
     */
    public PaymentDetailsModifier withSupportedMethods(List<String> supportedMethods) {
        this.supportedMethods = supportedMethods;
        return this;
    }

    /**
     * Get the total value.
     *
     * @return the total value
     */
    public PaymentItem total() {
        return this.total;
    }

    /**
     * Set the total value.
     *
     * @param total the total value to set
     * @return the PaymentDetailsModifier object itself.
     */
    public PaymentDetailsModifier withTotal(PaymentItem total) {
        this.total = total;
        return this;
    }

    /**
     * Get the additionalDisplayItems value.
     *
     * @return the additionalDisplayItems value
     */
    public List<PaymentItem> additionalDisplayItems() {
        return this.additionalDisplayItems;
    }

    /**
     * Set the additionalDisplayItems value.
     *
     * @param additionalDisplayItems the additionalDisplayItems value to set
     * @return the PaymentDetailsModifier object itself.
     */
    public PaymentDetailsModifier withAdditionalDisplayItems(List<PaymentItem> additionalDisplayItems) {
        this.additionalDisplayItems = additionalDisplayItems;
        return this;
    }

    /**
     * Get the data value.
     *
     * @return the data value
     */
    public Object data() {
        return this.data;
    }

    /**
     * Set the data value.
     *
     * @param data the data value to set
     * @return the PaymentDetailsModifier object itself.
     */
    public PaymentDetailsModifier withData(Object data) {
        this.data = data;
        return this;
    }

}
