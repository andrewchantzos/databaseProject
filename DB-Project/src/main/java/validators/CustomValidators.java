package validators;


import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.TextField;

public class CustomValidators {
	
	public static IntegerRangeValidator ageValidator() {
		return new IntegerRangeValidator("Age must be between 0 and 140", 0, 140);
	}
	
	public static IntegerRangeValidator idValidator() {
		return new IntegerRangeValidator("Invalid id", 1, 1000000);
	}

	public static IntegerRangeValidator experienceValidator() {
		return new IntegerRangeValidator("Experience must be between 0 and 70 years", 0, 70);
	}

	public static IntegerRangeValidator streetNumber() {
		return new IntegerRangeValidator("Street number must be between 0 and 10000", 0, 10000);
	}
	
	public static StringLengthValidator nameValidator() {
		return new StringLengthValidator("Invalid input", 1, 40, true);
	}
	
	public static RegexpValidator phoneRegexValidator() {
		return new RegexpValidator("[^a-zA-Z]{3,15}","Invalid Phone Number");
	}
	
	@SuppressWarnings("deprecation")
	public static TextField stringValidator(TextField field) {
		field.setValidationVisible(false);

		field.addBlurListener(new BlurListener() {
			
			@Override
			public void blur(BlurEvent event) {
				field.setValidationVisible(true);
				
			}
		});
		field.addValidator(CustomValidators.nameValidator());
		return field;
	}
	
	@SuppressWarnings("deprecation")
	public static TextField phoneValidator(TextField field) {
		field.setValidationVisible(false);
		field.addValidator(phoneRegexValidator());
		field.addValidator(nameValidator());
		field.addBlurListener(new BlurListener() {
			
			@Override
			public void blur(BlurEvent event) {
				field.setValidationVisible(true);
				
			}
		});

		return field;
	}
}
