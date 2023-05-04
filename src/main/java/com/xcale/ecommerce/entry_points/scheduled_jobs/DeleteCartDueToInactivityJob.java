package com.xcale.ecommerce.entry_points.scheduled_jobs;

import com.xcale.ecommerce.use_cases.delete_cart_due_to_inactivity.DeleteCartDueToInactivityUseCase;
import com.xcale.ecommerce.use_cases.delete_cart_due_to_inactivity.DeleteCartDueToInactivityUseCaseParams;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteCartDueToInactivityJob {
    private final DeleteCartDueToInactivityUseCase deleteCartDueToInactivityUseCase;

    public DeleteCartDueToInactivityJob(DeleteCartDueToInactivityUseCase deleteCartDueToInactivityUseCase) {
        this.deleteCartDueToInactivityUseCase = deleteCartDueToInactivityUseCase;
    }

    @Scheduled(fixedRate = 10000)
    public void scheduleTaskWithFixedRate() {
        DeleteCartDueToInactivityUseCaseParams params = new DeleteCartDueToInactivityUseCaseParams();

        try {
            deleteCartDueToInactivityUseCase.execute(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
