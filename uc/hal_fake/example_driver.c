/*
 * Copyright (C) 2020 Erik Fleckstein <erik@tinkerforge.com>
 *
 * Redistribution and use in source and binary forms of this file,
 * with or without modification, are permitted. See the Creative
 * Commons Zero (CC0 1.0) License for more details.
 */

#include <stdio.h>

#include "src/hal_fake/hal_fake.h"

static TF_Port ports[1] = {
    TF_PORT(1, 'A')
};

void example_setup(TF_HAL *hal);
void example_loop(TF_HAL *hal);

void check(int e_code, const char *c);

void check(int e_code, const char *c) {
    if (e_code < 0) {
        tf_hal_printf("Failed to %s: %s (error code %d)\n", c, tf_hal_strerror(e_code), e_code);
    }
}

static TF_HAL hal;

int main(void) {
    check(tf_hal_create(&hal, ports, sizeof(ports) / sizeof(ports[0])), "hal create");
    example_setup(&hal);

    while (true) {
        example_loop(&hal);
    }
}
