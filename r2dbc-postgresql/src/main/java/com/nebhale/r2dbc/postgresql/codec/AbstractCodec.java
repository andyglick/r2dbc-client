/*
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nebhale.r2dbc.postgresql.codec;

import com.nebhale.r2dbc.postgresql.client.Parameter;
import com.nebhale.r2dbc.postgresql.message.Format;
import com.nebhale.r2dbc.postgresql.type.PostgresqlObjectId;
import io.netty.buffer.ByteBuf;

import static java.util.Objects.requireNonNull;

abstract class AbstractCodec<T> implements Codec {

    private final Class<T> type;

    @SuppressWarnings({"unchecked", "rawtypes"})
    AbstractCodec(Class type) {
        this.type = requireNonNull(type, "type must not be null");
    }

    @Override
    public final boolean canEncode(Object value) {
        return this.type.isInstance(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Parameter encode(Object value) {
        requireNonNull(value, "value must not be null");

        return doEncode((T) value);
    }

    Parameter create(Format format, PostgresqlObjectId type, ByteBuf value) {
        requireNonNull(format, "format must not be null");
        requireNonNull(type, "type must not be null");

        return new Parameter(format, type.getObjectId(), value);
    }

    abstract Parameter doEncode(T value);

}
