package tk.parking.app.validation;

import tk.parking.app.http.request.LevelDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Comparator;
import java.util.List;

public class LevelSequenceValidator implements ConstraintValidator<LevelSequenceConstraint, List<LevelDTO>> {
    @Override
    public boolean isValid(final List<LevelDTO> levelDTOS, final ConstraintValidatorContext constraintValidatorContext) {
        levelDTOS.sort(Comparator.comparing(LevelDTO::getLevel));
        for (int i = 0; i < levelDTOS.size(); ) {
            if (levelDTOS.get(i++).getLevel() != i) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(LevelSequenceConstraint constraintAnnotation) {
    }
}
