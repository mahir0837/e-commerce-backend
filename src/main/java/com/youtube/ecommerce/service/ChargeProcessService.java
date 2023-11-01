package com.youtube.ecommerce.service;

import com.youtube.ecommerce.entity.OrderInput;

public interface ChargeProcessService {
    void doInitialCharge(OrderInput orderInput, boolean isSingleProductChekout);
}
