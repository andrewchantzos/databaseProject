package validators;


import com.vaadin.data.validator.IntegerRangeValidator;
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
	
	public static IntegerRangeValidator quantityValidator() {
		return new IntegerRangeValidator("Quantity must be between 0 and 1000", 0, 1000);
	}
	
	public static IntegerRangeValidator priceValidator() {
		return new IntegerRangeValidator("Price must be between 0 and 100000", 0, 100000);
	}


	public static IntegerRangeValidator streetNumber() {
		return new IntegerRangeValidator("Street number must be between 0 and 10000", 0, 10000);
	}
	
	public static StringLengthValidator nameValidator() {
		return new StringLengthValidator("Invalid input", 1, 40, true);
	}
	
	public static StringLengthValidator textValidator() {
		return new StringLengthValidator("Invalid input", 1, 400, true);
	}
	
	public static RegexpValidator phoneRegexValidator() {
		return new RegexpValidator("[^a-zA-Z]{3,15}","Invalid Phone Number");
	}
	
	public static TextField stringValidator(TextField field) {
		field.setValidationVisible(false);

		field.addBlurListener(event -> field.setValidationVisible(true));
		field.addValidator(CustomValidators.nameValidator());
		return field;
	}
	

	public static TextField phoneValidator(TextField field) {
		field.setValidationVisible(false);
		field.addValidator(phoneRegexValidator());
		field.addValidator(nameValidator());
		field.addBlurListener(new BlurListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void blur(BlurEvent event) {
				field.setValidationVisible(true);
				
			}
		});

		return field;
	}
}
