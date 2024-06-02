package validator;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Book;
import domain.Location;
import repository.BookRepository;
import repository.LocationRepository;

public class BookValidation implements Validator{
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private LocationRepository locationRepo;

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		Book newBook = (Book) target;
		
		// Validating ISBN13
		String isbn = newBook.getIsbn();
		validateISBN(isbn, errors);
		
		// Validate Purchase Price
		BigDecimal price = newBook.getPurchasePrice();
		if (price != null) {
			validatePurchasePrice(price, errors);
		}

		//Validate Location(s)
		//validateLocations(newBook, errors);
		
		// Validate URL format
	    String image = newBook.getCoverLink();
	    if (!image.isBlank()) {
	        if (!isValidURL(image)) {
	            errors.rejectValue("image", "invalidFormat.book.image", "Invalid URL format.");
	        }
	    }
	    
	    
	}

	private boolean isValidURL(String image) {
		try {
	        new URL(image);
	        return true;
	    } catch (MalformedURLException e) {
	        return false;
	    }
	}

	private void validateLocations(Book newBook, Errors errors) {
		List<Location> locations = newBook.getLocations();
		System.out.println(newBook);
		System.out.println(locations);

        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            System.out.println(location);
            
            if (location != null) {
                if (!location.getName().isBlank()) {
                    String code1 = location.getLocalCode1();
		            String code2 = location.getLocalCode2();
		            String name = location.getName();
		            
					Integer[] params = {50, 300, 50};
		
		            // Validation for codes
		            if (!isCorrectCode(code1)) {
		                errors.rejectValue("locations[" + i + "].localCode1", "invalidCode1.Location.localCode1", params, "Must be a number between {0} and {1}");
		            }
		            if (!isCorrectCode(code2)) {
		                errors.rejectValue("locations[" + i + "].localCode2", "invalidCode2.Location.localCode2", params, "Must be a number between {0} and {1}");
		            }
		
		            // Validate name of location
		            if (!isCorrectName(name)) {
		                errors.rejectValue("locations[" + i + "].name", "incorrectName.location.name", "Name of location must only contain letters.");
		            }
		            
		            // Validate if difference between 2 codes is at least 50
		            if (!isCorrectDifference(code1, code2)) {
		                errors.rejectValue("locations[" + i + "].localCode1", "invalidDifference.location.localCode1", params, "Difference must be at least {2}");
		            }
		
		            // Check if the location already exists for the book
		            if (locationRepo.locationAlreadyExists(code1, code2, name)) {
		                errors.reject("duplicateLocation.location", "This location has already been used by another book");
		            }
                }
            }
        }
	}

	private boolean isCorrectName(String name) {
		return name.matches("[a-zA-Z]+");
	}

	private boolean isCorrectDifference(String code1, String code2) {
		try {
            int intCode1 = Integer.parseInt(code1);
            int intCode2 = Integer.parseInt(code2);
            return Math.abs(intCode1 - intCode2) >= 50;
        } catch (NumberFormatException e) {
            return false;
        }
	}

	private boolean isCorrectCode(String code) {
		try {
			int intCode = Integer.parseInt(code);
			return intCode >= 50 && intCode <= 300;
	    } catch (NumberFormatException e) {
	    	return false;
	    }
	}

	private void validatePurchasePrice(BigDecimal price, Errors errors) {
		if (price.intValue() <=0 || price.intValue() >= 100) {
			int min = 0;
			int max = 100;
			Integer[] params = { min, max };
			errors.rejectValue("price", "incorrectRange.book.price",
					params,
					"Purchase price must be greater than {0} and be less than {1}.");
		}
	}

	private void validateISBN(String isbn, Errors errors) {
		if (isbn.length() != 13) {
	    	
	    	errors.rejectValue("isbn", isbn);
	        errors.rejectValue("isbn",
	        		"lenghtOfISBN.book.isbn",
	        		"ISBN must be 13 characters long");
	        
	    } else if (bookRepo.isAlreadyUsedISBN(isbn)) {
	        errors.rejectValue("isbn",
	        		"existingISBN.book.isbn",
	        		"ISBN number has already been used.");
	    } else {
	        // ISBN is valid check
	    	int sumEven = 0;
			int sumOdd = 0;
			
			for (int i = 1; i <= 11 ; i++) {
				sumEven += Integer.parseInt(String.valueOf(isbn.charAt(i)));
			}
			sumEven *= 3;
			
			for (int i = 0; i <= 10 ; i++) {
				sumOdd += Integer.parseInt(String.valueOf(isbn.charAt(i)));
			}
			int sum = sumEven + sumOdd;
			int nextTen = (int) (Math.round(sum/10.0) * 10);
			
			if (Integer.parseInt(String.valueOf(isbn.charAt(12))) != (nextTen-sum)) {
				errors.rejectValue("isbn",
						"invalidISBN13.book.isbn",
						"Please enter a valid ISBN13 code.");
			}
	    }
	}

}
