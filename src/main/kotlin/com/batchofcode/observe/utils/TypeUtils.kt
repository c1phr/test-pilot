package com.batchofcode.observe.utils

import org.springframework.core.ParameterizedTypeReference

/**
 * Created by ryanbatchelder on 2/21/17.
 */

inline fun <reified T: Any> typeRef(): ParameterizedTypeReference<T> = object: ParameterizedTypeReference<T>(){}