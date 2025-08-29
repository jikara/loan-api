package com.softmint.component;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.schema.idl.RuntimeWiring;

import static graphql.scalars.ExtendedScalars.*;


@DgsComponent
public class ScalarRegistration {
    @DgsRuntimeWiring
    public RuntimeWiring.Builder addLongScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(GraphQLLong);
    }

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addDateTimeScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(DateTime);
    }

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addDateScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(Date);
    }

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addBigDecimalScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(GraphQLBigDecimal);
    }

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addUrlScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(Url);
    }

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addUuidScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(UUID);
    }


}
