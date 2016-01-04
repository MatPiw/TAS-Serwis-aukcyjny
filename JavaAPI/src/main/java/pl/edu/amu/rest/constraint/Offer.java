package pl.edu.amu.rest.constraint;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;

/**
 * Created by Altenfrost on 2016-01-03.
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Offer.Validator.class)
public @interface Offer {
    String message() default "{pl.edu.amu.rest.constraint.Offer.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    public class Validator implements ConstraintValidator<Offer, pl.edu.amu.rest.model.Offer> {


        @Override
        public void initialize(Offer offer) {

        }

        @Override
        public boolean isValid(pl.edu.amu.rest.model.Offer offer, ConstraintValidatorContext constraintValidatorContext) {

            Long owner_id=Long.valueOf(offer.getOwner_id());
            Long buyer_id=(offer.getBuyer_id()==null)?null:Long.valueOf(offer.getBuyer_id());


            BigDecimal minimal_price=offer.getPrices().getMinimal_price();
            BigDecimal buy_now_price=offer.getPrices().getBuy_now_price();

            if (minimal_price!=null && buy_now_price!=null){
                if (buy_now_price.compareTo(minimal_price)==-1){

                    return false;
                }
            }
            if (buyer_id!=null){
                if (owner_id.compareTo(buyer_id)==0){

                    return false;
                }
            }

            return true;
        }
    }
}
