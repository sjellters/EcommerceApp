package com.xcale.ecommerce.use_cases;

public abstract class UseCase<P extends UseCaseParams, R extends UseCaseResult> {

    public abstract R execute(P params) throws Exception;
}
